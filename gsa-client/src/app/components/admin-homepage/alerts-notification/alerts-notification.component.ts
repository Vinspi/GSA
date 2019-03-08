import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-alerts-notification',
  templateUrl: './alerts-notification.component.html',
  styleUrls: ['./alerts-notification.component.css']
})
export class AlertsNotificationComponent implements OnInit {

  notificationType: String = 'warning';
  alerts: number

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getAlertNotification().subscribe(res => {
      this.alerts = res.data;
      if (this.alerts == 0){
        this.notificationType = 'success';
      }
      else {
        this.notificationType = 'warning'
      }
    });
  }

}
