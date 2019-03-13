import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-outdated-management',
  templateUrl: './outdated-management.component.html',
  styleUrls: ['./outdated-management.component.css']
})
export class OutdatedManagementComponent implements OnInit {

  products: Array<any>;
  aliquotSelected: any;
  aliquotIndex: number;
  productIndex: number;

  toastTrigger: Subject<void> = new Subject();
  toastBody: String;
  toastHeader: String;
  toastType: String;

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.products = [];
    this.aliquotSelected = null;
    this.adminService.getAllOutdatedAliquot().subscribe(res => {
      if (res.status == 'SUCCESS') {
        this.products = <Array<any>> res.data;
      }
    });
  }

  deleteAliquot(aliquot: any, aliquotIndex: number, productIndex: number) {
    console.log("aliquot "+aliquot);
    this.aliquotSelected = aliquot;
    this.aliquotIndex = aliquotIndex;
    this.productIndex = productIndex;
  }

  sendForm() {
    this.adminService.deleteOutdatedAliquot(this.aliquotSelected).subscribe(res => {
      if (res.status == 'SUCCESS') {
        this.toastBody = "The aliquot has been deleted";
        this.toastHeader = "Success";
        this.toastType = 'success';
        this.toastTrigger.next();
        this.products[this.productIndex].aliquots.splice(this.aliquotIndex, 1);

        if (this.products[this.productIndex].aliquots.length == 0){
          this.products.splice(this.productIndex, 1);
        }
      }
      else {
        this.toastBody = "This aliquot can't be deleted";
        this.toastHeader = "Error";
        this.toastType = 'danger';
        this.toastTrigger.next();
      }
    });
  }

}
