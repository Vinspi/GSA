export class Date {
  year: number;
  month: number;
  day: number;

  constructor() {
  }

  getDate(): Array<number> {
    return [this.year, this.month, this.day];
  }

  setDate(data: any) {
    this.year = data.year;
    this.month = data.month;
    this.day = data.day;
  }
}
