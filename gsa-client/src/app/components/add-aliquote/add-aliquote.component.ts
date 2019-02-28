import { Component, OnInit, ViewChild } from '@angular/core';
import {AdminService} from 'src/app/services/admin.service';
import { Subject } from 'rxjs';
import { Aliquote } from 'src/app/aliquote';
import { FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
import { AbstractControl, ValidatorFn ,ReactiveFormsModule} from '@angular/forms';



@Component({
  selector: 'app-add-aliquote',
  templateUrl: './add-aliquote.component.html',
  styleUrls: ['./add-aliquote.component.css']
})
export class AddAliquoteComponent implements OnInit {

  public isViewable: boolean;
  private SelectedNumLot : number 
  private SelectedPrice : number;
  private SelectedReserveQty : number;
  private SelectedVisibleQty : number;
  private SelectedProvider : string;
  private SelectedProduct : string;
  private _success = new Subject<string>();
  data: Array<Aliquote>;
  dtTrigger: Subject<any> = new Subject();
  messageAlert: string;
  typeAlert: string;
  aliquoteForm :FormGroup
  model: any = {};

  constructor(private adminService: AdminService) {
    this.isViewable = false; 
  }
   
  ngOnInit() {
    this.adminService.getAllProductsName().subscribe(response => {
      this.data = <Array<Aliquote>> response.data;
      console.log("data : "+JSON.stringify(this.data[0]));
      this.dtTrigger.next();
    });
  }

  public toggle(): void { 
    this.isViewable = !this.isViewable; 
   }

  addAliquote(){
    this.SelectedNumLot =this.model.Nlot;
    this.SelectedVisibleQty =this.model.visibleQty;
    this.SelectedPrice = this.model.price;
    this.SelectedProvider = this.model.provider;
    this.SelectedProduct = this.model.product;

    if(this.isViewable){
      this.SelectedReserveQty = this.model.reserveQty;
    } else this.SelectedReserveQty =0;
  
    this.adminService.addAliquote({
      aliquotNLot : this.SelectedNumLot,
      aliquotQuantityVisibleStock : this.SelectedVisibleQty,
      aliquotQuantityHiddenStock :this.SelectedReserveQty,
      aliquotPrice  : this.SelectedPrice,
      aliquoteProvider : this.SelectedProvider,
      aliquoteproduct : this.SelectedProduct,
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.typeAlert = "success";
        this.messageAlert = "The aliquote was successfully added.";

      } else if (res.status === 'FAIL') {
        this.typeAlert = "danger";
        this.messageAlert = "An error occurred, this aliquote could not be added.";
      }
      this.adminService.configureNotificationAlert(this.typeAlert,this.messageAlert, 4000);
    });
  }
}


