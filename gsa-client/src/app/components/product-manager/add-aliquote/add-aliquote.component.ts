import { Component, OnInit, ViewChild, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-add-aliquote',
  templateUrl: './add-aliquote.component.html',
  styleUrls: ['./add-aliquote.component.css']
})
export class AddAliquoteComponent implements OnInit, OnChanges {

  @Output() toast: EventEmitter<any> = new EventEmitter();

  public isViewable: boolean;
 
  data: String[];
  model: any = {};
  modelTransfert: any = {};

  constructor(private adminService: AdminService) {
    this.isViewable = false; 
  }

  loadData() {
    this.adminService.getAllProductsName().subscribe(response => {
      this.data = <String[]> response.data;
      /* set default value */
      this.model.product = this.data[0];
    });
  }
   
  ngOnInit() {
    this.data = [];
    this.modelTransfert.from = 'RESERVE';
    this.modelTransfert.to = 'STOCK';

    this.loadData();
  }

  ngOnChanges(simpleChanges: SimpleChanges) {
    this.loadData();
  }

  public toggle(): void { 
    this.isViewable = !this.isViewable;
  }

  transfertAliquot() {
    this.adminService.transfertAliquot(this.modelTransfert).subscribe(res => {

      if(res.status == 'SUCCESS') {

        this.toast.emit({
          toastHeader: "Success",
          toastBody: "The aliquot was successfully transferred.",
          toastType: 'success'
        });

    
      } else {

        this.toast.emit({
          toastHeader: "Error",
          toastBody: "this aliquot could not be added.",
          toastType: 'danger'
        });
      }
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

        this.toast.emit({
          toastHeader: "Success",
          toastBody: "The aliquot was successfully added.",
          toastType: 'success'
        });

      } else {

        this.toast.emit({
          toastHeader: "Error",
          toastBody: "this aliquot could not be added.",
          toastType: 'danger'
        });   
      }
    });
  }
}


