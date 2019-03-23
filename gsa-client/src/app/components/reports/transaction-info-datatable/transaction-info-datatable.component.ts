import { Component, OnInit, Input, AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { TeamTransaction } from 'src/app/teamTransaction';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-transaction-info-datatable',
  templateUrl: './transaction-info-datatable.component.html',
  styleUrls: ['./transaction-info-datatable.component.css']
})
export class TransactionInfoDatatableComponent implements AfterViewInit, OnDestroy, OnInit {
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Quantity', 'Unit price'];
  items: Array<any>;

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.dtOptions = {
      responsive: true,
      lengthMenu: [5, 10, 25, 50],
      paging: true,
      searching: true,
      info: false
    };
  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy() {
    this.dtTrigger.unsubscribe();
  }

  public async reRenderData() {
    return this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }

  private fetchTransactions(team: string, quarter: string, year: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.adminService
        .getQuarterlyWithdrawnTransactionsByTeamNameAndYear(
          team,
          quarter,
          year
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

  public updateTransactionData(team: string, quarter: string, year: string) {
    this.fetchTransactions(team, quarter, year)
      .then(data => {
        this.items = this.transactionValuesToArray(<Array<TeamTransaction>>data);
        this.reRenderData();
      })
      .catch(() => {
        this.items = [];
        this.reRenderData();
      });
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
