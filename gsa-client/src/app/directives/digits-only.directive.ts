import { Directive, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[digitsOnly]'
})
export class DigitsOnlyDirective {

  constructor() { }


  @HostListener('keydown', ['$event'])
  onKeyDown(e: KeyboardEvent) {

    var regexp = new RegExp("[0-9]");

    // Allow: Delete, Backspace, Tab, Escape, Enter
    if ([46, 8, 9, 27, 13].indexOf(e.keyCode) !== -1) {
      return;
    }

    if (regexp.test(e.key)){
      return;
    }
    else {
      e.preventDefault();
    }
  }

  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent) {
    event.preventDefault();
    const pastedInput: string = event.clipboardData
      .getData('text/plain')
      .replace(/\D/g, ''); // get a digit-only string
    document.execCommand('insertText', false, pastedInput);
  }


}
