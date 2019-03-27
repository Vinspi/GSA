import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import * as $ from 'jquery';
import { User } from 'src/app/user';
import { Foo } from '../../foo';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit, OnChanges {

  user: User;
  selectedValue: String;

  constructor(private localStorage: LocalStorage) { }

  ngOnInit() {
    Foo.subj.subscribe(user => {

      this.user = user;
      
    });
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    });
  }

  ngOnChanges(simpleChanges: SimpleChanges) {
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    });
  }

  setSelectedValue(value: String) {
    this.selectedValue = value;
    console.log(this.selectedValue);
    
  }

  isSelected(value: String): boolean {
    return value == this.selectedValue;
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
    $('.panel-icon-container i').css('font-size', '18pt');
  }

  

}
