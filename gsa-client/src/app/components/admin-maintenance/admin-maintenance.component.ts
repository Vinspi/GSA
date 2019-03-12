import { Component, OnInit, OnChanges } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AdminService } from '../../services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-maintenance',
  templateUrl: './admin-maintenance.component.html',
  styleUrls: ['./admin-maintenance.component.css']
})
export class AdminMaintenanceComponent implements OnInit {

  loaded: boolean;
  isMaintenanceMode: boolean;
  password: String;

  constructor(private adminService: AdminService, private userService: UserService, private router: Router) { }

  loadData() {
    this.loaded = false;
    this.userService.isMaintenance().subscribe(res => {
      this.isMaintenanceMode = res.data;
      this.loaded = true;
    });
  }

  ngOnInit() {
    this.loadData();
  }

  sendForm(){
    
    this.adminService.setMaintenanceMode({
      password: this.password,
      mode: !this.isMaintenanceMode
    }).subscribe(res => {
      if(res.status == 'SUCCESS'){
        if (!this.isMaintenanceMode) this.router.navigate(['/maintenance']);
        if (this.isMaintenanceMode) this.router.navigate(['/']);
        this.isMaintenanceMode = !this.isMaintenanceMode;
        this.password = "";
      }
    });
  }
}
