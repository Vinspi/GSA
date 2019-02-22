import { Component, OnInit } from '@angular/core';
import {AdminService} from 'src/app/services/admin.service';
import { aliquoteOverview } from '../../aliquoteOverview';
import { Subject } from 'rxjs';
import {debounceTime} from 'rxjs/operators';


@Component({
  selector: 'app-add-aliquote',
  templateUrl: './add-aliquote.component.html',
  styleUrls: ['./add-aliquote.component.css']
})
export class AddAliquoteComponent implements OnInit {

  public isViewable: boolean;
  private SelectedPrice : string;
  private SelectedReserveQty : string;
  private SelectedVisibleQty : string;
  private SelectedProvider : string;
  private selectedSource : string;
  private selectedTarget : string;
  private defaultOption: String = 'Choose';

  data: Array<aliquoteOverview>;
  dtTrigger: Subject<any> = new Subject();

  messageAlert: string;
  typeAlert: string;
  private _success = new Subject<string>();

  constructor(private adminService: AdminService) {
    this.isViewable = false; 
   }
   
  ngOnInit() {
    this.adminService.getAllSpeciesName().subscribe(response => {
      this.data = <Array<aliquoteOverview>> response.data;
      console.log("data : "+JSON.stringify(this.data[0]));
      this.dtTrigger.next();
    });
  }
  public toggle(): void { 
    this.isViewable = !this.isViewable; 
    console.log('---->' +this.isViewable);
  }

  addAliquote(){
    this.SelectedVisibleQty =(<HTMLInputElement>document.getElementById("visibleQty")).value;
    this.SelectedPrice = (<HTMLInputElement>document.getElementById("price")).value;
    this.SelectedProvider = (<HTMLInputElement>document.getElementById("provider")).value;
    this.selectedSource = $('#source option:selected').text();
    this.selectedTarget = $('#target option:selected').text();

    if(this.isViewable){

      this.SelectedReserveQty = (<HTMLInputElement>document.getElementById("reserveStock")).value;
    } else this.SelectedReserveQty ="";
  
    if (this.SelectedPrice === null ||this.SelectedProvider === null || this.selectedSource === this.defaultOption || this.selectedTarget === this.defaultOption){
      this.typeAlert = "danger";
      this.messageAlert = "Fields with * are required ";
      this.adminService.configureMessageAlert(this.typeAlert,this.messageAlert, 4000);
      return;
    }

    this.adminService.addAliquote({
      aliquotQuantityVisibleStock : this.SelectedVisibleQty,
      aliquotQuantityHiddenStock :this.SelectedReserveQty,
      aliquotPrice  : this.SelectedPrice,
      provider : this.SelectedProvider,
      source : this.selectedSource,
      target : this.selectedTarget,
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.typeAlert = "success";
        this.messageAlert = "The product was successfully added.";

      } else if (res.status === 'FAIL') {
        this.typeAlert = "danger";
        this.messageAlert = "An error occurred, this product could not be added.";
      }
      this.adminService.configureMessageAlert(this.typeAlert,this.messageAlert, 4000);
    });
  }
}


