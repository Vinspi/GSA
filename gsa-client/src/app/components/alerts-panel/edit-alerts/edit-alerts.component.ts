import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-edit-alerts',
  templateUrl: './edit-alerts.component.html',
  styleUrls: ['./edit-alerts.component.css']
})

export class EditAlertsComponent implements OnInit {

  @Output() updateAlertList = new EventEmitter<any>();

  data: any[] = [];
  elementChoosed: number;
  seuil: number;


  constructor(private adminService : AdminService) { }

  ngOnInit() {
    this.adminService.getAllAlerts().subscribe(response => {
      this.data = response.data;
      this.elementChoosed = 0;
    });
  }

  chooseElement(index: number) {
    this.elementChoosed = index;
  }

  removeAlert(){
    this.adminService.removeAlert(this.data[this.elementChoosed].alertId).subscribe(response => {
      if (response.status == 'SUCCESS'){
        this.data.splice(this.elementChoosed, 1);   
        this.updateAlertList.emit(); 
      }
    });
  }

  updateAlert(){
    var alert = this.data[this.elementChoosed];
    console.log(alert);
    
    this.adminService.updateAlert(alert.alertId, this.seuil).subscribe(response => {
      if (response.status == 'SUCCESS'){
        this.data[this.elementChoosed].seuil = this.seuil;
        this.updateAlertList.emit(); 
      }
    });
  }

}
