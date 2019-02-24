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
        aliquot.id = el[0];
        aliquot.date = el[1];
        const expirationDate  = moment (el[1]);
        if(expirationDate > moment()){
          aliquot.expire = false;
        } else {
          aliquot.expire = true;
        }
        this.aliquoList.push(aliquot)
       });
      });
  }

  deleteAliquot(id: number){
    // appel au service suppression
  }



  
}
