import {Component, OnInit, Output} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {AdminService} from 'src/app/services/admin.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  @Output() toast: EventEmitter<any> = new EventEmitter();
  @Output() reload: EventEmitter<void> = new EventEmitter();

  private speciesNames: string[] = [];
  private selectedTarget: string;
  private selectedSource: string;

  /* toast (cuz i like toast) */
  toastBody: String;
  toastHeader: String;
  toastType: String;


  constructor(private adminService: AdminService) { }

  ngOnInit() {

    this.adminService.getAllSpeciesName().subscribe(res => {
      this.speciesNames = res.data;
      this.selectedSource = this.speciesNames[0];
      this.selectedTarget = this.speciesNames[0];
    });
  }

  addProduct() {

    this.adminService.addProduct({
      targetName : this.selectedTarget,
      sourceName : this.selectedSource
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.toast.emit({
          toastBody: 'The product was successfully added.',
          toastHeader: 'Success',
          toastType: 'success'
        });
        this.reload.emit();
      } else if (res.status === 'FAIL') {
        this.toast.emit({
          toastBody: 'this product could not be added.',
          toastHeader: 'Error',
          toastType: 'danger'
        });
      }
    });
  }
}
