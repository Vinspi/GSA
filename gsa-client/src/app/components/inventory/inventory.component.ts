import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { keyframes } from '@angular/animations';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  data: any[] = [];
  map: Map<number, number> = new Map();

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getAllProductsWithAliquots().subscribe(res => {
      if (res.status == 'SUCCESS'){
        this.data = res.data;
        
        /* we fill the map with actual values */
        for(var i=0;i<this.data.length;i++){
          for(var k=0;k<this.data[i].aliquots.length;k++){
            
            this.map.set(this.data[i].aliquots[k].aliquotNLot, this.data[i].aliquots[k].aliquotQuantityVisibleStock);
    
          }
        }
        
        
      }
    });
  }

  setMapValue(nLot: number, event){
    this.map.set(nLot, +event.target.value);
  }

  sendForm(){
    var form = [];

    this.map.forEach((nlot, value) => {
      form.push({
        aliquotNLot: nlot,
        quantity: value
      });    
    });
    
    this.adminService.postInventoryForm(form).subscribe(res => {

    });
  }


}
