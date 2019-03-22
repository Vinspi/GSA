import { Component, OnInit, Input, AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';

@Component({
  selector: 'app-reloadable-datatable',
  templateUrl: './reloadable-datatable.component.html',
  styleUrls: ['./reloadable-datatable.component.css']
})
export class ReloadableDatatableComponent implements AfterViewInit, OnDestroy, OnInit {
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();

  @Input()
  headers: Array<String>;

  items: Array<any>;

  constructor() { }

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

}
