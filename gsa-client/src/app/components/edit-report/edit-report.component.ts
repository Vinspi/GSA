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
  totalLosses: number;
  remainingLosses: number;


  headers: Array<string> = ['Date', 'User name', 'Product name', 'Withdrawn quantity', 'Unit price'];

  productLosses: Map<string, number>;
  teamLosses: Map<string, number>;

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
  private noTeam: string;

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {
    this.noTeam = "No teams";

    this.years = ["2019"]; // Need to request the year of the earliest quarterly report.
    this.selectedQuarter = this.quarters[0];
    this.selectedYear = this.years[0];
    this.selectedTeam = this.noTeam;

    this.adminService.getQuarterlyTransactionLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(res => {
      this.totalLosses = res.data.totalLosses;
      this.remainingLosses = res.data.totalLosses;
      console.log(this.remainingLosses);
      this.productLosses = new Map<string, number>();
      res.data.productLosses.forEach(productLoss => {
        this.productLosses.set(productLoss.name, productLoss.loss);
      });
    });

    this.teamLosses = new Map<string, number>();
    this.teamLosses.set('Team A', 0);
    this.teamLosses.set('Team B', 6.65);
    this.teamLosses.set('Team C', 5.65);
    this.teamLosses.set('Team D', 5.65);
    this.teamLosses.set('Team E', 8.65);
    this.teamLosses.set('Team F', 5.65);
    this.teamLosses.set('Team G', 5.65);
    this.teamLosses.set('Team H', 0);
    this.teamLosses.set('Team J', 0);
    this.teamLosses.set('Team K', 0);
    this.teamLosses.set('Team L', 0);
    this.teamLosses.set('Team M', 5.65);
    


    /*this.adminService.getReportLosses(this.selectedQuarterToParamValue(), this.selectedYear).subscribe(res => {
        this.teamLosses = new Map<string, number>();
        res.data.forEach(teamLoss => {
          this.teamLosses.set(teamLoss.name, teamLoss.loss);
        });
    });*/

  }

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.userService.getAllTeamName().subscribe(res => {
      if (res && res.data.length > 0) {
        this.teamNames = res.data;
        this.selectedTeam = this.teamNames[8];
        this.updateTransactionData();
      } else {
        this.teamNames = [this.noTeam];
        this.selectedTeam = this.noTeam;
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
    if (this.selectedTeam !== this.noTeam) {
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
