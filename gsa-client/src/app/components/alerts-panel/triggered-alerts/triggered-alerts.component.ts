import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-triggered-alerts',
  templateUrl: './triggered-alerts.component.html',
  styleUrls: ['./triggered-alerts.component.css']
})
export class TriggeredAlertsComponent implements OnInit, OnChanges{

  data: any[] = [];

  constructor(private adminService : AdminService) { }

  ngOnInit() {
    this.adminService.getTriggeredAlerts().subscribe(response => {
      this.data = <any[]> response.data;
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    this.adminService.getTriggeredAlerts().subscribe(response => {
      this.data = <any[]> response.data;
    });
  }

  toggle(event) {
    console.log(event.srcElement.id);

  }

}
