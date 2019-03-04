import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AliquotService } from '../../services/aliquot.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/user';
import { Aliquot } from './aliquot';
import * as moment from 'moment';
import {formatDate} from '@angular/common';

@Component({
  selector: 'app-aliquot-management',
  templateUrl: './aliquot-management.component.html',
  styleUrls: ['./aliquot-management.component.css']
})

export class AliquotManagementComponent implements OnInit {

  cart: Array<any> = new Array();
  barcodeNlot: number
  user: User;
  teamChoosed: String;
  loaded: boolean = false;
  aliquoList = new Array<Aliquot>();


  constructor(private userService: UserService, private localStorage: LocalStorage, private aliquotService: AliquotService) {}

  ngOnInit() {
    formatDate(new Date(), 'yyyy-MM-dd', 'en');
    console.log(this.aliquoList);
    // this.aliquotService.getAliquots();
    console.log(new Date());
    this.aliquotService.getAliquots().subscribe(res => {
      // this.aliquoList = res.data;
      res.data.forEach(el => {
        const aliquot = new Aliquot();
        aliquot.id = el.aliquotNLot;
        aliquot.date = el.aliquotExpirationDate;
        aliquot.quantityVisible = el.aliquotQuantityVisibleStock;
        aliquot.quatityHidden = el.aliquotQuantityHiddenStock;
        const expirationDate  = el.aliquotExpirationDate;
       console.log(expirationDate);
        if(expirationDate < moment.locale()){ 
          //if(aliquot.date < moment()){ 
          aliquot.expire = true;
        } else {
          aliquot.expire = false;
        }
        this.aliquoList.push(aliquot)
       });
       //console.log(aliquot.expire);
      console.log(this.aliquoList);
      });
  }

  deleteAliquot(id: number){
    // appel au service suppression
    this.aliquotService.removeAliquots(id).subscribe(res => {
      alert("Aliquot deleted");
    });

  }

  





  
}
