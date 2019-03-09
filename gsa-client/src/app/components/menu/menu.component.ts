import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import * as $ from 'jquery';
import { User } from 'src/app/user';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit, OnChanges {

  user: User;

  constructor(private localStorage: LocalStorage) { }

  ngOnInit() {
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    });
  }

  ngOnChanges(simpleChanges: SimpleChanges) {
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    });
  }

  globalOne: any;

  onMouseEnterPanel(){
    this.globalOne = setTimeout(function() {
      $('#panel span').show();
      $('#panel span').css('opacity','1');
      
    }, 300);
    $('.panel-icon-container i').css('font-size', '14pt');
  }

  onMouseLeavePanel(){
    clearTimeout(this.globalOne);
    $('#panel span').hide();
    $('#panel span').css('opacity', '0');
    $('.panel-icon-container i').css('font-size', '20pt');
  }


  

}
