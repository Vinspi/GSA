import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  speciesNames : String[] = [];

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.getAllSpeciesName().subscribe(res => {
      this.speciesNames = res.data;
    });
  }
}
