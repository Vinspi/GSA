import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  months: String[] = ["january","february","march","april", "june", "july", "august","september","october","november","december"];
  teams: String[] = [];
  products: String[] = [];
  years: number[] = [];

  beginMonth: String;
  endMonth: String;
  beginYear: number;
  endYear: number;
  team: String;
  product: String;


  public pieChartLabels:string[] = [];
  public pieChartData:number[] = [];

  public pieChartType:string = 'line';
  public pieChartOptions:any = {responsive: true, maintainAspectRatio: false, legend: {display: false}}
  public chartColors: Array<any> = [
    { // first color
      backgroundColor: 'rgba(77,92,138,0.8)',
      borderColor: 'rgba(77,92,138,1)',
      pointBackgroundColor: 'rgba(225,10,24,0.2)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(225,10,24,0.2)'
    },
  ];

  constructor(private userService: UserService) { }

  ngOnInit() {

    this.years = [2019,2018,2017];

    this.beginMonth = this.months[0];
    this.endMonth = this.months[0];
    this.beginYear = this.years[0];
    this.endYear = this.years[0];


    this.userService.getAllProductName().subscribe(response => {
      this.products = response.data;
    });
    this.userService.getAllTeamName().subscribe(response => {
      this.teams = response.data;
    });
  }

  onValueChange() {
    
  }

}
