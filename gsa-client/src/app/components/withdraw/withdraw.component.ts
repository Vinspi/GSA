import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { element } from '@angular/core/src/render3';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {

  cart: Array<any> = new Array();
  barcodeNlot: number

  constructor(private userService: UserService) { }

  ngOnInit() { }

  onTimesClicked(n: number){
    this.cart.splice(n,1);
  }

  onMinusClicked(nlot: number) {
    console.log("minus"+nlot);
    
    this.cart.forEach((element, index, o) => {
      if(element.nlot == nlot){
        if(element.quantity == 1){
          this.cart.splice(index,1);
        }
        else element.quantity--
      }
    });
  }

  onKeyEnter() {
    console.log("keyup ! ");

    var call_api = true;

    this.cart.forEach(element => {
      if(element.nlot == this.barcodeNlot){
        call_api = false;
        element.quantity++;
        this.barcodeNlot = null;
        return;
      }
    });

    if(call_api){
      this.userService.getProductName(this.barcodeNlot).subscribe(response => {
        if(response.status == 'SUCCESS'){
          this.cart.push({
            nlot: this.barcodeNlot,
            name: response.data,
            quantity: 1
          });
        }
        this.barcodeNlot = null;
      });
    }

    
  }

  onWithdrawClicked() {

    var aliquotList = new Array<any>();

    this.cart.forEach(element => {
      aliquotList.push({
        nlot: element.nlot,
        quantity: element.quantity
      });
    });

    this.userService.withdrawCart(aliquotList).subscribe(response => {
      this.cart = new Array();
    });
  }
}
