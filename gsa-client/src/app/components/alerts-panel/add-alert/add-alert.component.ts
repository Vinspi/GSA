import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AdminService } from '../../../services/admin.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-add-alert',
  templateUrl: './add-alert.component.html',
  styleUrls: ['./add-alert.component.css']
})
export class AddAlertComponent implements OnInit {

  @Output() newAlert: EventEmitter<void> = new EventEmitter();

  productNames: String[];
  toastEvent: Subject<void>;
  toastHeader: String;
  toastBody: String;
  toastType: String;
  toastTimeout: number;

  addAlertModel: any = {};

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.toastTimeout = 2000;
    this.toastEvent = new Subject();
    this.productNames = [];
    this.addAlertModel.storageType = 'STOCK';
    this.adminService.getAllProductsName().subscribe(res => {
      this.productNames = <String[]> res.data;
      this.addAlertModel.productName = this.productNames[0];
    });
  }

  triggerToast() {
    this.toastEvent.next();
  }

  sendAddAlertForm() {
    this.adminService.addAlert(this.addAlertModel).subscribe(res => {
      if (res.status == 'SUCCESS'){
        this.setUpSuccessToast();
        this.triggerToast();
        this.newAlert.emit();
        this.addAlertModel.quantity = null;
      }
      else {
        this.setUpDangerToast();
        this.triggerToast();
      }
    });
  }

  setUpDangerToast() {
    this.toastHeader = "Error";
    this.toastBody = "This alert can't be added";
    this.toastType = "danger";
  }

  setUpSuccessToast() {
    this.toastHeader = "Success";
    this.toastBody = "The alert has been added !";
    this.toastType = "success";
  }

}
