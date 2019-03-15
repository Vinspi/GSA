import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild,
  Output
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

  quarters: Array<any>;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;
  selectedYear: string;

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Withdrawn quantity', 'Unit price'];
  quartersText: Array<string> = [
    'First quarter',
    'Second quarter',
    'Third quarter',
    'Fourth quarter'
  ];

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {

    /*this.adminService.getQuarterAndYearValuesOfEditableReports().subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.quarters = res.data;
      }

    });*/

    this.years = ['2018', '2019']; // Need to request the year of the earliest quarterly report.
    this.selectedQuarter = this.quartersText[0];
    this.selectedYear = this.years[0];

  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.userService.getAllTeamName().subscribe(res => {
      if (res && res.data.length > 0) {
        this.teamNames = res.data;
        this.selectedTeam = this.teamNames[0];

        this.updateTransactionData();
        this.updateTeamLosses();
        this.updateProductLosses();
      }
    });
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
      data.year = this.selectedYear;
      data.quarter = this.selectedQuarterToParamValue();

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
    this.updateProductLosses();
    this.updateTeamLosses();
  }

  public validateReport() {
    if (this.remainingLosses === 0) {
      this.saveTeamReportData(this.teamLosses, true)
        .then(() => {
          this.toastBody = 'The report was successfully validated.';
          this.toastHeader = 'Success';
          this.toastType = 'success';
          this.toastTrigger.next();
        })
        .catch(() => console.log('Could not save'));
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
      })
      .catch(() => {
        console.log('Could not save input');
      });

  }

  public updateTeamLosses() {
    this.adminService.getReportLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.teamLosses = new Map<string, number>();
        for (const teamLoss of response.data) {
          this.teamLosses.set(teamLoss.teamName, teamLoss.loss);
        }

        if (this.teamLosses.size === 0) {
          for (let index = 0; index < this.teamNames.length; index++) {
            this.teamLosses.set(this.teamNames[index], 0);
          }
          this.saveTeamReportData(this.teamLosses, false)
            .catch(() => console.log('Could not save'));
        }
      } else {
        // failed request
      }

    });
  }

  public updateProductLosses() {
    this.adminService.getQuarterlyTransactionLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.productLosses = new Map<string, number>();
        res.data.productLosses.forEach(productLoss => {
          this.productLosses.set(productLoss.name, productLoss.loss);
        });

        this.totalLosses = res.data.totalLosses;
        this.adminService.getSumOfQuarterLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(response => {
          if (response.status === 'SUCCESS') {
            this.remainingLosses = this.totalLosses - response.data;
            this.remainingLosses = Number(this.remainingLosses.toFixed(2));
          } else {
            this.remainingLosses = 0;
          }
        });
      } else {
        //Failed request
      }
    });
  }

  /*Transactions and datatable*/
  private fetchTransactions(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.adminService
        .getQuarterlyWithdrawnTransactionsByTeamNameAndYear(
          this.selectedTeam,
          this.selectedQuarterToParamValue(),
          this.selectedYear
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
    if (this.teamNames.length > 0) {
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

  private selectedQuarterToParamValue() {
    return 'QUARTER_' + (this.quartersText.indexOf(this.selectedQuarter) + 1);
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
