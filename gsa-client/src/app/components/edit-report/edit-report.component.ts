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

  // Cost variables
  cost: any = {
    totalLosses: 0,
    teamCost: 0,
    remainingLosses: 0
  };

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Withdrawn quantity', 'Unit price'];
  private _noTeam: string;
  isOnTransactionMode: boolean;

  // Form variables
  quarters: Array<string> = [
    "First quarter",
    "Second quarter",
    "Third quarter",
    "Fourth quarter"
  ];
  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;
  selectedYear: string;

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {
    this._noTeam = "No teams";
    this.isOnTransactionMode = true;

    this.years = ["2019"]; // Need to request the year of the earliest quarterly report.
    this.selectedQuarter = this.quarters[0];
    this.selectedYear = this.years[0];

    this.adminService.getQuarterlyTransactionLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(res => {
      this.cost.totalLosses = res.data.totalCost;
      this.cost.remainingLosses = res.data.totalCost;
    });

  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.userService.getAllTeamName().subscribe(res => {
      if (res && res.data.length > 0) {
        this.teamNames = res.data;
        this.selectedTeam = this.teamNames[8];
        this.updateTransactionData();
      } else {
        this.teamNames = [this._noTeam];
        this.selectedTeam = this._noTeam;
      }
    });
  }

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
            reject(new Error("Failed request"));
          }
        });
    });
  }

  private selectedQuarterToParamValue() {
    return "QUARTER_" + (this.quarters.indexOf(this.selectedQuarter) + 1);
  }

  public updateTransactionData() {
    if (this.selectedTeam !== this._noTeam) {
      this.fetchTransactions()
      .then(data => {
        if(this.isOnTransactionMode) {
          this.dtElement.items = this.transactionValuesToArray(<Array<Transaction>>data.transactions);
          this.dtElement.reRenderData();
        }
        this.cost.currentBill = data.totalPrice;
      })
      .catch(e => {
        this.dtElement.items = [];
        this.cost.currentBill = 0;
        if (this.isOnTransactionMode) {
          this.dtElement.reRenderData();
        }
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

  public toggle(): void {
    this.isOnTransactionMode = !this.isOnTransactionMode;
    if(this.isOnTransactionMode) {
      this.updateTransactionData();
    } else {

    }
  }
}
