import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild,
} from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { UserService } from 'src/app/services/user.service';
import { ReloadableDatatableComponent } from '../reloadable-datatable/reloadable-datatable.component';
import { Transaction } from 'src/app/transaction';
import { EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-edit-report',
  templateUrl: './edit-report.component.html',
  styleUrls: ['./edit-report.component.css']
})
export class EditReportComponent implements AfterViewInit, OnInit {
  @ViewChild(ReloadableDatatableComponent)
  dtElement: ReloadableDatatableComponent;

  toast: EventEmitter<any> = new EventEmitter();
  toastTrigger: Subject<void> = new Subject();
  toastBody: String;
  toastHeader: String;
  toastType: String;

  totalLosses: number;
  remainingLosses: number;
  productLosses: Map<string, number>;
  teamLosses: Map<string, number>;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Withdrawn quantity', 'Unit price'];
  quartersText: Array<string>;
  quarters: Map<string, any>;

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.initializeQuarters().then(() =>
      this.userService.getAllTeamName().subscribe(res => {
        if (res && res.data.length > 0) {
          this.teamNames = res.data;
          this.selectedTeam = this.teamNames[0];
          this.updateAllData();
        }
      })
    );
  }

  private initializeQuarters(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.adminService.getQuarterAndYearValuesOfAllEditableReports().subscribe(res => {
        if (res.status === 'SUCCESS') {
          this.quartersText = new Array();
          this.quarters = new Map();
          for (const obj of res.data) {
            const key = this.quarterToText(obj.quarter) + obj.year;
            this.quartersText.push(key);
            this.quarters.set(key, obj);
          }

          if (this.quarters.size > 0) {
            this.selectedQuarter = this.quartersText[0];
            resolve();
          } else {
            reject();
          }
        }
        reject();
      });
    });
  }

  private getSelectedQuarter(): string {
    return this.quarters.get(this.selectedQuarter).quarter;
  }

  private getSelectedYear(): string {
    return this.quarters.get(this.selectedQuarter).year;
  }
  private quarterToText(quarter: string): string {
    if (quarter === 'QUARTER_1') {
      return 'First quarter of ';
    }
    if (quarter === 'QUARTER_2') {
      return 'Second quarter of ';
    }
    if (quarter === 'QUARTER_3') {
      return 'Third quarter of ';
    }
    if (quarter === 'QUARTER_4') {
      return 'Fourth quarter of ';
    } else {
      return null;
    }
  }

  private saveTeamReportData(teamLosses: Map<string, number>, finalFlag: boolean): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const teamLossesAsArray = new Array<any>();
      for (const [key, value] of Array.from(teamLosses)) {
        teamLossesAsArray.push({
          teamName: key,
          loss: value
        });
      }

      const data: any = {};
      data.teamReportLosses = teamLossesAsArray;
      data.finalFlag = finalFlag;
      data.year = this.getSelectedYear();
      data.quarter = this.getSelectedQuarter();

      this.adminService.saveTeamReport(data).subscribe(res => {
        if (res.status === 'SUCCESS') {
          resolve();
        } else {
          reject();
        }
      });
    });
  }

  public updateAllData() {
    this.updateTransactionData();
    // report needs to be initialised and saved first before being able of getting the sum of losses of this quarter
    this.updateTeamLosses()
    .then(() => this.updateProductLosses());
  }

  public validateReport() {
    if (this.remainingLosses === 0) {
      this.saveTeamReportData(this.teamLosses, true)
        .then(() => {
          this.initializeQuarters().then(() => this.updateAllData());
          this.toastBody = 'The report was successfully validated.';
          this.toastHeader = 'Success';
          this.toastType = 'success';
          this.toastTrigger.next();
        });
    } else {
      this.toastBody = 'The report could not be validated';
      this.toastHeader = 'Error';
      this.toastType = 'danger';
      this.toastTrigger.next();
    }
  }

  public onTeamLossInputChange(teamName: string, event) {
    const newLossValue: number = event.target.value;
    const oldLossValue: number = this.teamLosses.get(teamName);
    const teamLoss = new Map<string, number>();

    this.teamLosses.set(teamName, newLossValue);
    teamLoss.set(teamName, newLossValue);
    this.saveTeamReportData(teamLoss, false)
      .then(() => {
        this.remainingLosses -= newLossValue - oldLossValue;
        this.remainingLosses = Number(this.remainingLosses.toFixed(2));
      });
  }

  public updateTeamLosses() {
    return new Promise((resolve, reject) =>
      this.adminService.getReportLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
        if (response.status === 'SUCCESS') {
          this.teamLosses = new Map<string, number>();
          for (const teamLoss of response.data) {
            this.teamLosses.set(teamLoss.teamName, teamLoss.loss);
          }

          if (this.teamLosses.size === 0) {
            for (let index = 0; index < this.teamNames.length; index++) {
              this.teamLosses.set(this.teamNames[index], 0);
            }
            this.saveTeamReportData(this.teamLosses, false).then(() => resolve());
          } else {
            resolve();
          }
        } else {
          reject();
        }
      }));
  }

  public updateProductLosses() {
    this.adminService.getQuarterlyTransactionLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.productLosses = new Map<string, number>();
        res.data.productLosses.forEach(productLoss => {
          this.productLosses.set(productLoss.name, productLoss.loss);
        });

        this.totalLosses = res.data.totalLosses;
        this.adminService.getSumOfQuarterLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
          if (response.status === 'SUCCESS') {
            this.remainingLosses = this.totalLosses - response.data;
            this.remainingLosses = Number(this.remainingLosses.toFixed(2));
          } else {
            this.remainingLosses = -1;
          }
        });
      }
    });
  }

  /*Transactions and datatable*/
  private fetchTransactions(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.adminService
        .getQuarterlyWithdrawnTransactionsByTeamNameAndYear(
          this.selectedTeam,
          this.getSelectedQuarter(),
          this.getSelectedYear()
        )
        .subscribe(transactionResponse => {
          if (transactionResponse.status === 'SUCCESS') {
            resolve(transactionResponse.data);
          } else {
            reject();
          }
        });
    });
  }

  public updateTransactionData() {
    if (this.quarters.size > 0) {
      this.fetchTransactions()
        .then(data => {
          this.dtElement.items = this.transactionValuesToArray(<Array<Transaction>>data.transactions);
          this.dtElement.reRenderData();
        })
        .catch(e => {
          this.dtElement.items = [];
          this.dtElement.reRenderData();
        });
    }
  }

  private transactionValuesToArray(transactions: Array<any>): Array<any> {
    const transactionValues = [];
    for (const transaction of transactions) {
      const values = [];
      values.push(transaction.transactionDate);
      values.push(transaction.userName);
      values.push(transaction.productName);
      values.push(transaction.transactionQuantity);
      values.push(transaction.aliquotPrice);
      transactionValues.push(values);
    }
    return transactionValues;
  }
}
