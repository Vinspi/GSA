import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { keyframes } from '@angular/animations';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  data: any[] = [];
  map: Map<number, number> = new Map();
  toastTrigger: Subject<void> = new Subject();

  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit() {
    this.adminService.getAllProductsWithAliquots().subscribe(res => {
      if (res.status == 'SUCCESS'){
        this.data = res.data;
        
        /* we fill the map with actual values */
        this.data.forEach(product => {
          product.aliquots.forEach(aliquot => {
            this.map.set(aliquot.aliquotNLot, aliquot.aliquotQuantityVisibleStock);
          });
        });
      }
    });
  }

  setMapValue(nLot: number, event){
    this.map.set(nLot, +event.target.value);
  }

  sendForm(){
    var form = [];

    this.map.forEach((value, key) => {
      form.push({
        aliquotNLot: key,
        quantity: value
      });    
    });
    
    console.log(form);
    

    this.adminService.postInventoryForm(form).subscribe(res => {
      if(res.status == 'FAIL'){
        this.toastTrigger.next();
      }
      else {
        /* i know this is ugly but stackOverflow's users said it was ok so */
        (<any>$('#modalInventory')).modal('toggle');
      }
    });
  }


}
