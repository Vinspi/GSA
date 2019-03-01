import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AliquotService } from '../../services/aliquot.service';
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
  barcodeNlot: number
  user: User;
  teamChoosed: String;
  loaded: boolean = false;
  aliquoList = new Array<Aliquot>();

  constructor(private userService: UserService, private localStorage: LocalStorage, private aliquotService: AliquotService) { }

  ngOnInit() {
    console.log('aliqot');
    // this.aliquotService.getAliquots();
    this.aliquotService.getAliquots().subscribe(res => {
      // this.aliquoList = res.data;
      res.data.forEach(el => {
        const aliquot = new Aliquot();
        aliquot.id = el.aliquotNLot;
        aliquot.date = el.aliquotExpirationDate;
        aliquot.quantityVisible = el.aliquotQuantityVisibleStock;
        aliquot.quatityHidden = el.aliquotQuantityHiddenStock;
        const expirationDate  = el.aliquotExpirationDate;
       // console.log(expirationDate);
        if(expirationDate > moment()){
          aliquot.expire = false;
        } else {
          aliquot.expire = true;
        }
        this.aliquoList.push(aliquot)
       });
     //  console.log(this.aliquoList);
      });
  }

  deleteAliquot(id: number){
    // appel au service suppression
    this.aliquotService.removeAliquots(id).subscribe(res => {
      alert("Aliquot deleted");
    });

    
  }

  





  
}
