import { Directive, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[appDigitOnlyDec]'
})
export class DigitOnlyDecDirective {

  constructor(private el: ElementRef) { }

  @HostListener('keydown', ['$event'])
  onKeyDown(e: KeyboardEvent) {

    var regexp = new RegExp("^[0-9]$");

    // Allow: Delete, Backspace, Tab, Escape, Enter
    if ([46, 8, 9, 27, 13].indexOf(e.keyCode) !== -1) {
      return;
    }

    console.log(e.key);
    console.log(regexp.test(e.key));
    
    if (e.key == '.' && this.el.nativeElement.value.indexOf('.') !== -1) {
      e.preventDefault();
    }
    else if (e.key == '.') {
      return;
    }
    

    if (regexp.test(e.key)) {
      
      return;
    }
    else {
      e.preventDefault();
    }
  }


}
