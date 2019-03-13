import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AdminService } from '../../services/admin.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/user';
import { Aliquot } from './aliquot';
import * as moment from 'moment';

@Component({
  selector: 'app-aliquot-management',
  templateUrl: './aliquot-management.component.html',
  styleUrls: ['./aliquot-management.component.css']
})
export class AliquotManagementComponent implements OnInit {

  cart: Array<any> = new Array();
  user: User;
  teamChoosed: String;
  loaded: boolean = false;
  aliquoList = new Array<Aliquot>();


  constructor(private userService: UserService, private localStorage: LocalStorage, private adminService: AdminService) { }

  ngOnInit() {
    console.log(this.adminService.getAliquots());
    this.adminService.getAliquots().subscribe(res => {
      this.aliquoList = res.data;
      res.data.forEach(el => {
        const aliquot = new Aliquot();
        aliquot.id = el.aliquotNLot;
        aliquot.date = el.aliquotExpirationDate;
        aliquot.quantityVisible = el.aliquotQuantityVisibleStock;
        aliquot.quatityHidden = el.aliquotQuantityHiddenStock;
        if(this.adminService.isExpire(aliquot.id)){
          (this.adminService.isExpire(aliquot.id))
          aliquot.expire = true;
          console.log('haaaaaaaaaaahowaaaaaaaaaaaaaaaaaaaaaaaaaa \n'+aliquot.expire );
          console.log("aaaaaaaa"+aliquot);
        } else {
          aliquot.expire = false;
        }
        this.aliquoList.push(aliquot)
       });
      });
  }


  updateAliquot(id: number){
    this.adminService.updateAliquot(id).subscribe(res => {
      alert("Aliquot expire !!!");
      this.ngOnInit();
    });
    
    
  }

  





  
}
