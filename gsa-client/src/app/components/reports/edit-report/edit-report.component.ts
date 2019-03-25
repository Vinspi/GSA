import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild,
} from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { UserService } from 'src/app/services/user.service';
import { TransactionInfoDatatableComponent } from '../../reports/transaction-info-datatable/transaction-info-datatable.component';
import { EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';
import { Big } from 'big.js';
import { TeamTransaction } from 'src/app/teamTransaction';

@Component({
  selector: 'app-edit-report',
  templateUrl: './edit-report.component.html',
  styleUrls: ['./edit-report.component.css']
})
export class EditReportComponent implements AfterViewInit, OnInit {
  @ViewChild(TransactionInfoDatatableComponent)
  dtElement: TransactionInfoDatatableComponent;

  activeTab: string;

  toast: EventEmitter<any> = new EventEmitter();
  toastTrigger: Subject<void> = new Subject();
  toastBody: String;
  toastHeader: String;
  toastType: String;

  quarterBill: Big;
  teamBill: Big;
  remainingLosses: Big;
  productLosses: Map<string, Big>;
  teamLosses: Map<string, Big>;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Quantity', 'Unit price'];
  quartersText: Array<string>;
  quarters: Map<string, any>;

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {
    this.teamLosses = new Map<string, Big>();
    this.activeTab = 'quarterOverview';
  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.initializeQuarters()
      .then(quarterNumber => {
        if (quarterNumber > 0) {
          this.userService.getAllTeamName().subscribe(res => {
            if (res && res.data.length > 0) {
              this.teamNames = res.data;
              this.teamNames.sort((left, right) => (left > right ? 1 : -1));
              this.selectedTeam = this.teamNames[0];
              this.updateAllData();
            }
          });
        }
      }).catch(() => this.displayCouldNotRetrieveDataToast());
  }

  public changeTab(activeTab) {
    this.activeTab = activeTab;
  }

  private displayCouldNotSaveToast() {
    this.toastBody = 'Saving process failed.';
    this.toastHeader = 'Error';
    this.toastType = 'danger';
    this.toastTrigger.next();
  }

  private displayCouldNotRetrieveDataToast() {
    this.toastBody = 'Some data could not be retrieved.';
    this.toastHeader = 'Error';
    this.toastType = 'danger';
    this.toastTrigger.next();
  }

  private initializeQuarters(): Promise<Number> {
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
          }
          resolve(this.quarters.size);
        } else {
          reject();
        }
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

  private saveTeamReportData(teamLosses: Map<string, Big>, finalFlag: boolean): Promise<any> {
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
    this.updateTransactions();
    this.updateWithdrawnTransactionsTotalCost();
    // report needs to be initialised and saved first before being able of getting the remaining losses
    this.updateTeamLosses()
      .then(() => this.updateProductLosses())
      .catch(() => this.displayCouldNotRetrieveDataToast());
  }

  public validateReport() {
    if (this.remainingLosses.eq(new Big(0))) {
      this.saveTeamReportData(this.teamLosses, true)
        .then(() => {
          this.initializeQuarters()
            .then(quarterNumber => {
              if (quarterNumber > 0) {
                this.updateAllData();
              }
            }).catch(() => this.displayCouldNotRetrieveDataToast());
          this.toastBody = 'The report was successfully validated.';
          this.toastHeader = 'Success';
          this.toastType = 'success';
          this.toastTrigger.next();
        }).catch(() => this.displayCouldNotSaveToast());
    } else {
      this.toastBody = 'The report could not be validated';
      this.toastHeader = 'Error';
      this.toastType = 'danger';
      this.toastTrigger.next();
    }
  }

  public onTeamLossInputChange(teamName: string, event) {
    const oldLossValue: Big = this.teamLosses.get(teamName);
    const teamLoss = new Map<string, Big>();
    const updateToPreviousValue = () => $('#input-team-' + this.teamNames.indexOf(teamName)).val(oldLossValue.toString());

    if (event.target.value === '') {
      return;
    }

    if (event.target.value === '.' || !event.target.value.match((new RegExp(/^\d{1,6}\.?\d{0,2}$/g)))) {
      updateToPreviousValue();
      return;
    }

    const newLossValue: Big = new Big(event.target.value);

    if (this.remainingLosses.plus(oldLossValue).minus(newLossValue).cmp(new Big(0)) >= 0) {
      this.teamLosses.set(teamName, newLossValue);
      teamLoss.set(teamName, newLossValue);
      this.saveTeamReportData(teamLoss, false)
        .then(() => this.updateRemainingLosses())
        .catch(() => this.displayCouldNotSaveToast());
    } else {
      updateToPreviousValue();
      this.toastBody = 'Remaining losses cannot be negative';
      this.toastHeader = 'Error';
      this.toastType = 'danger';
      this.toastTrigger.next();
    }
  }

  private updateWithdrawnTransactionsTotalCost() {
    this.adminService.getWithdrawnProductsTotalCost(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.quarterBill = new Big(response.data);
      } else {
        this.quarterBill = new Big(0);
      }
    });
  }

  public updateTeamLosses() {
    return new Promise((resolve, reject) =>
      this.adminService.getReportLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
        if (response.status === 'SUCCESS') {
          if (response.data.length === 0) {
            for (let index = 0; index < this.teamNames.length; index++) {
              this.teamLosses.set(this.teamNames[index], new Big(0));
            }
            this.saveTeamReportData(this.teamLosses, false)
              .then(() => resolve())
              .catch(() => this.displayCouldNotSaveToast());
          } else {
            for (const teamLoss of response.data) {
              this.teamLosses.set(teamLoss.teamName, teamLoss.loss);
            }
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
        this.productLosses = new Map<string, Big>();
        res.data.productLosses.forEach(productLoss => {
          this.productLosses.set(productLoss.name, new Big(productLoss.loss));
        });

        if (this.productLosses.size !== 0) {
          this.updateRemainingLosses();
        } else {
          this.remainingLosses = new Big(0);
        }
      }
    });
  }

  private updateRemainingLosses() {
    this.adminService.getRemainingReportLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.remainingLosses = new Big(response.data);
      } else {
        this.remainingLosses = new Big(0);
      }
    });
  }

  public fetchTransactions(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.adminService
        .getQuarterlyWithdrawnTransactionsByTeamNameAndYear(
          this.selectedTeam,
          this.getSelectedQuarter(),
          this.getSelectedYear()
        )
        .subscribe(transactionResponse => {
          if (transactionResponse.status === 'SUCCESS') {
            this.teamBill = new Big((<Array<TeamTransaction>> transactionResponse.data).reduce((acc, currentValue) => {
              return acc+=(currentValue.aliquotPrice*currentValue.transactionQuantity);
            }, 0));
            resolve(transactionResponse.data);
          } else {
            reject();
          }
        });
    });
  }

  private updateTransactions() {
    this.fetchTransactions()
    .then(withdrawnTransactions => this.dtElement.updateTransactionDatatable(<Array<TeamTransaction>> withdrawnTransactions));
  }
}
