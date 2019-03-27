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

  headers: Array<string> = ['Date', 'User name', 'Product name', 'Quantity', 'Unit price (TF)'];
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

  public reRenderData() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }

  public updateTransactionDatatable(transactions: Array<TeamTransaction>) {
    this.items = transactions.map(transaction => [
      transaction.transactionDate,
      transaction.userName,
      transaction.productName,
      transaction.transactionQuantity,
      transaction.aliquotPrice
    ]);
    this.reRenderData();
  }
}
