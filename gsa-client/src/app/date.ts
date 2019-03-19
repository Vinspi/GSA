import { formatDate } from '@angular/common';

export class Date {
  constructor(public year: number, public month: number, public day: number) {
  }

  get date() {
    if (this.year !== null) {
      return formatDate(this.year + '-' + this.month + '-' + this.day, 'yyyy-MM-dd', 'en-US');
    } else {
      return '';
    }
  }
}
