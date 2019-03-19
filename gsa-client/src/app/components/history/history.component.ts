import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Transaction } from 'src/app/transaction';
import { Subject, from } from 'rxjs';
import { Date } from 'src/app/date';
import { DataTableDirective } from 'angular-datatables';
import { AngularCsv } from 'angular7-csv/dist/Angular-csv';
import { NgbDatepickerConfig, NgbCalendar, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/user';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements AfterViewInit, OnDestroy, OnInit {

  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  history: Array<Transaction>;
  begin: Date;
  end: Date;
  stringBegin: string;
  stringEnd: string;
  selectedDate: any;

  user: User;

  constructor(private adminService: AdminService,
              private userService: UserService,
              private config: NgbDatepickerConfig,
              private calendar: NgbCalendar,
              private localStorage: LocalStorage) {

    
  }

  ngOnInit() {

    this.localStorage.getItem('user').subscribe(user => {
      this.user = <User> user;
      this.sendData();
    });

    $('#beginDate').attr('readonly', 'true');
    $('#endDate').attr('readonly', 'true');

    this.dtOptions = {
      retrieve: true,
      paging: true,
      lengthChange: false,
      pageLength: 10,
      pagingType: 'full_numbers',
      responsive: true,
      deferRender: true,
      processing: true
    };
    this.config.markDisabled = (date: NgbDate) => this.calendar.getWeekday(date) >= 6;

  }

  sendData() {
    this.setupDate();

    if (this.user.admin) {
      this.adminService.getWithdrawalsHistory({
        begin: this.stringBegin,
        end: this.stringEnd
      }).subscribe(res => {
          
        this.history = <Array<Transaction>> res.data;
        this.rerender();
      });
    } else {
      this.userService.getWithdrawalsHistory({
        userName: this.user.userName,
        begin: this.stringBegin,
        end: this.stringEnd
      }).subscribe(res => {
        this.history = <Array<Transaction>> res.data;
        this.rerender();
      });
    }
  }

  setupDate() {
    if (this.begin) {
      this.begin = new Date(this.begin.year, this.begin.month, this.begin.day);
      this.stringBegin = this.begin.date;
    } else {
      this.stringBegin = '';
    }

    if (this.end) {
      this.end = new Date(this.end.year, this.end.month, this.end.day);
      this.stringEnd = this.end.date;
    } else {
      return '';
    }
  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe(); // Do not forget to unsubscribe the event
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy(); // Destroy the table first
      this.dtTrigger.next(); // Call the dtTrigger to rerender again
    });
  }

  exportToCsv() {
    const csvOptions = {
      fieldSeparator: ';',
      quoteStrings: '"',
      decimalseparator: '.',
      showLabels: true,
      showTitle: true,
      useBom: true,
      headers: [
        'date',
        'userName',
        'aliquotName',
        'teamName',
        'quantity',
        'price'
      ]
    };

    if (this.begin && this.end) {
      return new AngularCsv(<Object> this.history, 'Transactions-since-' + this.stringBegin + '-until-' + this.stringEnd, csvOptions);
    } else if (this.begin && !this.end) {
      return new AngularCsv(<Object> this.history, 'Transactions-since-' + this.stringBegin, csvOptions);
    } else if (!this.begin && this.end) {
      return new AngularCsv(<Object> this.history, 'Transactions-until-' + this.stringEnd, csvOptions);
    } else {
      return new AngularCsv(<Object> this.history, 'All transactions', csvOptions);
    }
  }

  resetInputs() {
    $('#beginDate').attr('readonly', 'true');
    $('#endDate').attr('readonly', 'true');
  }

  setMinDate() {
    this.config.minDate = this.begin;
  }

  setMaxDate() {
    this.config.maxDate = this.end;
  }

  unsetMinDate() {
    this.config.minDate = null;
  }

  unsetMaxDate() {
    this.config.maxDate = null;
  }
}
