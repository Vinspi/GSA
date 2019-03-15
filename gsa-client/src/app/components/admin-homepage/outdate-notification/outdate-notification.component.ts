import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-outdate-notification',
  templateUrl: './outdate-notification.component.html',
  styleUrls: ['./outdate-notification.component.css']
})
export class OutdateNotificationComponent implements OnInit {

  notificationType: String = 'success';

  outdated: number;

  constructor(private adminService: AdminService) { }


  ngOnInit() {
    this.adminService.getAllOutdatedAliquot().subscribe(res => {
      if (res.status == 'SUCCESS') {
        this.outdated = (<Array<any>> res.data).reduce((acc, current) => {
          return acc += current.aliquots.length;
        }, 0);        
        if (this.outdated > 0){
          this.notificationType = 'warning'
        }
      }
    });
  }

}
