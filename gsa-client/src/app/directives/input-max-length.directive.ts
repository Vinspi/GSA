import { Directive, HostListener, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appInputMaxLength]'
})
export class InputMaxLengthDirective {

  @Input('appInputMaxLength') inputMaxLenght: number;

  constructor(private el: ElementRef) {
    
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(e: KeyboardEvent) {

  // Allow: Delete, Backspace, Tab, Escape, Enter
  if ([46, 8, 9, 27, 13].indexOf(e.keyCode) !== -1) {
    return;
  }

  if (this.el.nativeElement.value.length >= this.inputMaxLenght) {
    console.log("prevent default !");
    
    e.preventDefault();
  }

    console.log("valuekjhvfkhgdkftygh : "+this.el.nativeElement.value.length+" and "+this.inputMaxLenght);
    
  }


}
