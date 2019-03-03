import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Transaction } from 'src/app/transaction';
import { NgbCalendar, NgbDate } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  history: Array<Transaction>;
  beginDate = '';
  endDate = '';

  constructor(private adminService: AdminService) {
  }

  ngOnInit() {
    this.adminService.getWithdrawalsHistoryBetween(this.beginDate, this.endDate).subscribe(res => {
      this.history = <Array<Transaction>> res.data;
      console.log('data : ' + JSON.stringify(this.history));
    });
  }

  sendData() {
    this.adminService.getWithdrawalsHistoryBetween(this.beginDate, this.endDate);
  }

  print(data: any) {
    console.log('print : ' + data);
  }

}
