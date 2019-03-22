import { Directive, HostListener, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appNodrop]'
})
export class NodropDirective {
// Your welcome Kappa
  constructor(private el: ElementRef) {

  }

}