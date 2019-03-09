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

  history: Array<Transaction>;
  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();
  begin: any;
  end: any;

  constructor(private adminService: AdminService) {
  }

  ngOnInit() {
    this.dtOptions = {
      /*columns: [{
        title: 'Date',
        data: 'date'
      }, {
        title: 'User',
        data: 'userName'
      }, {
        title: 'Aliquot',
        data: 'aliquotName'
      },
      {
        title: 'Team',
        data: 'teamName'
      },
      {
        title: 'Quantity',
        data: 'quantity'
      },
      {
        title: 'Price',
        data: 'price'
      }],
      ajax: {
        url: 'http://localhost:8080/admin/history',
        method: 'POST',
        data: {
          begin: this.begin,
          end: this.end
        }
      },
      // Declare the use of the extension in the dom parameter
      dom: 'Bfrtip',
      // Configure the buttons
      buttons: [
        'columnsToggle',
        'colvis',
        'copy',
        'print',
        'excel',
        {
          text: 'Some button',
          key: '1',
          action: function (e, dt, node, config) {
            alert('Button activated');
          }
        }
      ],*/
      retrieve: true
    };
    /*$('datatable').DataTable({
      retrieve: true
    });*/

    this.begin = null;
    this.end = null;
    this.sendData();
  }

  sendData() {
    this.setupDate();

    this.adminService.getWithdrawalsHistory({
      begin: this.begin,
      end: this.end
    }).subscribe(res => {
      this.history = <Array<Transaction>> res.data;
      this.dtTrigger.next();
    });
  }

  setupDate() {
    if (this.begin === null || this.begin === [null, null, null]) {
      this.begin = [];
    } else {
      this.begin = [this.begin.year, this.begin.month, this.begin.day];
    }

    if (this.end === null || this.end === [null, null, null]) {
      this.end = [];
    } else {
      this.end = [this.end.year, this.end.month, this.end.day];
    }
  }
  /*private extractData(res: JsonResponse) {
    const body = res;
    return body.data || {};
  }*/

  // tslint:disable-next-line:use-life-cycle-interface
  /*ngOnDestroy() {
    this.dtTrigger.unsubscribe();
  }*/

}
