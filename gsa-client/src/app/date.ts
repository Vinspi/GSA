import { formatDate } from '@angular/common';

export class Date {
  constructor(public year: number, public month: number, public day: number) {
  }

  get date() {
    if (this.year !== null) {
      return formatDate(this.year.toString() + '-' + this.month.toString() + '-' + this.day.toString(), 'yyyy-MM-dd', 'en-US');
    } else {
      return '';
    }
  }
}
