import { Directive, HostListener, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appNodrop]'
})
export class NodropDirective {

  @HostListener('drop', ['$event'])
  noDrop(): boolean {
    return false;
  }

  constructor(private el: ElementRef) {

  }

}