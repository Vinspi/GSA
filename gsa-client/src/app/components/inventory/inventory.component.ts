import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  data: any[] = [];

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getAllProductsWithAliquots().subscribe(res => {
      if (res.status == 'SUCCESS'){
        this.data = res.data;        
      }
    });
  }

}
