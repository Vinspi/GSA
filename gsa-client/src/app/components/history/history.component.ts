import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Transaction } from 'src/app/transaction';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  /*id: number;
  date: Date;
  motif: string;
  quantity: number;
  type: string;
  aliquotId: number;
  memberId: number;*/

  transactionList: Array<Transaction>;
  dtTrigger: Subject<any> = new Subject();

  constructor(private adminService: AdminService) {
  }

  ngOnInit() {
    this.adminService.getWithdrawalsHistory().subscribe(res => {
      this.transactionList = <Array<Transaction>> res.data;
      console.log('data : ' + JSON.stringify(this.transactionList[0]));
      this.dtTrigger.next();
    });
  }
}
