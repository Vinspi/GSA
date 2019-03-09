import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-report-notification',
  templateUrl: './report-notification.component.html',
  styleUrls: ['./report-notification.component.css']
})
export class ReportNotificationComponent implements OnInit {

  notificationType: String = 'warning';
  days: number;

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    // this.notificationType = 'success';
    this.adminService.getReportNotification().subscribe(res => {
      if (res.data.available == true){
        this.notificationType = 'success';
      }
      else {
        this.notificationType = 'warning';
      }
      this.days = res.data.nextReportIn;
    });
  }

}
