import { Component, OnInit } from '@angular/core';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';
import * as $ from 'jquery';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

globalOne: any;

onMouseEnterPanel(){
  this.globalOne = setTimeout(function() {
    $('#panel span').show();
    $('#panel span').css('opacity','1');
  }, 300);

}

onMouseLeavePanel(){
  clearTimeout(this.globalOne);
  $('#panel span').hide();
  $('#panel span').css('opacity', '0');
}


  constructor() { }

  ngOnInit() {
  }

}
