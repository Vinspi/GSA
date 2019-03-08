import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report-notification',
  templateUrl: './report-notification.component.html',
  styleUrls: ['./report-notification.component.css']
})
export class ReportNotificationComponent implements OnInit {

  notificationType: String = 'warning';

  constructor() { }

  ngOnInit() {
    // this.notificationType = 'success';
  }

}
