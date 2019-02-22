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

  private configureMessageAlert(type: string, message: string, time: number) {
    this.typeAlert = type;
    this._success.subscribe((m) => this.messageAlert = m);
    this._success.pipe(
      debounceTime(time)
    ).subscribe(() => this.messageAlert = null);
    this._success.next(message);
  }

  addAliquote(){
    this.SelectedVisibleQty =(<HTMLInputElement>document.getElementById("visibleQty")).value;
    this.SelectedReserveQty = (<HTMLInputElement>document.getElementById("reserveStock")).value;
    this.SelectedPrice = (<HTMLInputElement>document.getElementById("price")).value;
    this.SelectedProvider = (<HTMLInputElement>document.getElementById("provider")).value;
    this.selectedSource = $('#source option:selected').text();
    this.selectedTarget = $('#target option:selected').text();
   

    this.adminService.addAliquote({
      aliquotQuantityVisibleStock : this.SelectedVisibleQty,
      aliquotQuantityHiddenStock :this.SelectedReserveQty,
      aliquotPrice  : this.SelectedPrice,
      provider : this.SelectedProvider,
      source : this.selectedSource,
      target : this.selectedTarget,
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.configureMessageAlert('success', 'The product was successfully added.', 4000);
      } else if (res.status === 'FAIL') {
        this.configureMessageAlert('danger', 'An error occurred, this product could not be added.', 4000);
      }
    });
  }
}


