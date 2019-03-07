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
  modelTransfert: any = {};

  constructor(private adminService: AdminService) {
    this.isViewable = false; 
  }
   
  ngOnInit() {
    this.data = [];
    this.modelTransfert.from = 'RESERVE';
    this.modelTransfert.to = 'STOCK';

    this.adminService.getAllProductsName().subscribe(response => {
      this.data = <String[]> response.data;
      /* set default value */
      this.model.product = this.data[0];
    });
  }

  public toggle(): void { 
    this.isViewable = !this.isViewable;
  }

  transfertAliquot() {
    this.adminService.transfertAliquot(this.modelTransfert).subscribe(res => {

      if(res.status == 'SUCCESS') {
        this.typeAlert = 'success';
        this.messageAlert = this.modelTransfert.qte+' entity of aliquot n°'+this.modelTransfert.Nlot+
        ' have been transferred from '+this.modelTransfert.from+' to '+this.modelTransfert.to
      } else {
        this.typeAlert = 'danger';
        this.messageAlert = 'An error occured, this aliquot can\'t be transferred' 
      }
      setTimeout(() => {        
        this.messageAlert = null;
      }, 4000);
    });
  }

  addAliquote(){
  
    this.adminService.addAliquote({
      aliquotNLot : this.model.Nlot,
      aliquotQuantityVisibleStock : this.model.visibleQty,
      aliquotQuantityHiddenStock : this.model.reserveQty,
      aliquotPrice  : this.model.price,
      aliquotProvider : this.model.provider,
      aliquotProduct : this.model.product,
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.typeAlert = "success";
        this.messageAlert = "The aliquot was successfully added.";

      } else {
        this.typeAlert = "danger";
        this.messageAlert = "An error occurred, this aliquot could not be added.";
      }
      setTimeout(() => {        
        this.messageAlert = null;
      }, 4000);
    });
  }
}


