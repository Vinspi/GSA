import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';


@Component({
  selector: 'app-add-aliquote',
  templateUrl: './add-aliquote.component.html',
  styleUrls: ['./add-aliquote.component.css']
})
export class AddAliquoteComponent implements OnInit {

  public isViewable: boolean;
 
  data: String[];
  messageAlert: string;
  typeAlert: string;
  model: any = {};

  constructor(private adminService: AdminService) {
    this.isViewable = false; 
  }
   
  ngOnInit() {
    this.data = [];

    this.adminService.getAllProductsName().subscribe(response => {
      this.data = <String[]> response.data;
      /* set default value */
      this.model.product = this.data[0];
    });
  }

  public toggle(): void { 
    this.isViewable = !this.isViewable;
  }

  addAliquote(){
  
    this.adminService.addAliquote({
      aliquotNLot : this.model.Nlot,
      aliquotQuantityVisibleStock : this.model.visibleQty,
      aliquotQuantityHiddenStock : this.isViewable?this.model.reserveQty:0,
      aliquotPrice  : this.model.price,
      aliquotProvider : this.model.provider,
      aliquotProduct : this.model.product,
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.typeAlert = "success";
        this.messageAlert = "The aliquot was successfully added.";

      } else if (res.status === 'FAIL') {
        this.typeAlert = "danger";
        this.messageAlert = "An error occurred, this aliquot could not be added.";
      }
      setTimeout(() => {        
        this.messageAlert = null;
      }, 4000);
    });
  }
}


