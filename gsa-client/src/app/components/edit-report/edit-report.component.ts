import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild
} from "@angular/core";
import { AdminService } from "src/app/services/admin.service";
import { UserService } from "src/app/services/user.service";
import { ReloadableDatatableComponent } from '../reloadable-datatable/reloadable-datatable.component';
import { Transaction } from 'src/app/transaction';

@Component({
  selector: "app-edit-report",
  templateUrl: "./edit-report.component.html",
  styleUrls: ["./edit-report.component.css"]
})
export class EditReportComponent implements AfterViewInit, OnInit {
  @ViewChild(ReloadableDatatableComponent)
  dtElement: ReloadableDatatableComponent;

  totalLosses: number;
  remainingLosses: number;
  productLosses: Map<string, number>;
  teamLosses: Map<string, number>;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;
  selectedYear: string;

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Withdrawn quantity', 'Unit price'];
  quarters: Array<string> = [
    "First quarter",
    "Second quarter",
    "Third quarter",
    "Fourth quarter"
  ];

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {

    this.years = ["2018", "2019"]; // Need to request the year of the earliest quarterly report.
    this.selectedQuarter = this.quarters[0];
    this.selectedYear = this.years[0];

    this.updateProductLosses();
  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.userService.getAllTeamName().subscribe(res => {
      if (res && res.data.length > 0) {
        this.teamNames = res.data;
        this.selectedTeam = this.teamNames[6];

        this.updateTransactionData();
        this.updateTeamLosses();

      } else {
        // Handle no team situation
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
        .catch(() => console.log("Could not save"));
    } else {

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
      })
      .catch(() => {
        console.log("Could not save input");
      });

  }

  public updateTeamLosses() {
    this.adminService.getReportLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(response => {
      this.teamLosses = new Map<string, number>();
      for (const teamLoss of response.data) {
        this.teamLosses.set(teamLoss.teamName, teamLoss.loss);
      }

      if (this.teamLosses.size === 0) {
        this.totalLosses = 0;
        this.remainingLosses = 0;
        for (let index = 0; index < this.teamNames.length; index++) {
          this.teamLosses.set(this.teamNames[index], 0);
        }
        this.saveTeamReportData(this.teamLosses, false)
          .catch(() => console.log('Could not save'));
      } else {

      }
    });
  }

  public updateProductLosses() {
    this.adminService.getQuarterlyTransactionLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(res => {
      this.productLosses = new Map<string, number>();
      res.data.productLosses.forEach(productLoss => {
        this.productLosses.set(productLoss.name, productLoss.loss);
      });
      if (res.status === 'SUCCESS') {
        this.totalLosses = res.data.totalLosses;
        this.remainingLosses = res.data.totalLosses;
      } else {
        this.totalLosses = res.data.totalLosses;
        this.remainingLosses = res.data.totalLosses;
      }
      console.log('totalLosses : ' + this.totalLosses + " this.remainingLosses : " + this.remainingLosses);
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
          if (transactionResponse.status === "SUCCESS") {
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
    return "QUARTER_" + (this.quarters.indexOf(this.selectedQuarter) + 1);
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
