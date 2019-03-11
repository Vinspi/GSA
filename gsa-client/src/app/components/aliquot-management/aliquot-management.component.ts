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
        //const expirationDate  = moment (el.date);
        if(this.adminService.updateAliquot(aliquot.id)){
          aliquot.expire = true;
          console.log('haaaaaaaaaaahowaaaaaaaaaaaaaaaaaaaaaaaaaa \n'+aliquot.expire );
          console.log(aliquot);
        } else {
          aliquot.expire = false;
        }
        this.aliquoList.push(aliquot)
       });
      });
  }

  /*ngOnInit() {
    console.log('aliqot');
    //this.adminService.getAliquots();
    this.adminService.getAliquots().subscribe(res => {
      this.aliquoList = <Array<Aliquot>>res.data;
      console.log("aliquoList : "+JSON.stringify(this.aliquoList));
      res.data.forEach(el => {
        const aliquot = new Aliquot();
        aliquot.id = el.aliquotNLot;
        aliquot.date = el.aliquotExpirationDate;
        aliquot.quantityVisible = el.aliquotQuantityVisibleStock;
        aliquot.quatityHidden = el.aliquotQuantityHiddenStock;
        aliquot.expire=true;
        console.log(aliquot);
       });
      });
  }*/


  updateAliquot(id: number){
    // test pour afficher msg ( aliquot expire ou not expire) , sinn test pour afficher le bouton ou pas 
    this.adminService.updateAliquot(id).subscribe(res => {
      alert("Aliquot not expire !!!");
    });
    this.ngOnInit();
    
  }

  





  
}
