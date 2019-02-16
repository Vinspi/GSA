import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AdminService } from '../../services/admin.service';
import { BaseChartDirective } from 'ng2-charts/ng2-charts';
import { EventEmitter } from 'events';
import { load } from '@angular/core/src/render3';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  months: String[] = ["january","february","march","april", "may","june", "july", "august","september","october","november","december"];
  teams: String[] = [];
  products: String[] = [];
  years: number[] = [];

  beginMonth: String;
  endMonth: String;
  beginYear: number;
  endYear: number;
  team: String;
  product: String;

  private eventEmitter:  EventEmitter = new EventEmitter();


  public chartLabels:String[] = [];
  public chartData:number[] = [];

  public chartType:string = 'line';
  public chartOptions:any = {responsive: true, maintainAspectRatio: false, legend: {display: false}}
  public chartColors: Array<any> = [
    { 
      backgroundColor: 'rgba(77,92,138,0.8)',
      borderColor: 'rgba(77,92,138,1)',
      pointBackgroundColor: 'rgba(225,10,24,0.2)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(225,10,24,0.2)'
    },
  ];

  constructor(private userService: UserService, private adminService: AdminService) { }

  ngOnInit() {

    var loaded = 0;

    this.years = [];

    for(var i=0;i<3;i++){
      this.years.push((new Date()).getFullYear()-i);
    }

    this.beginMonth = this.months[0];
    this.endMonth = this.months[1];
    this.beginYear = this.years[0];
    this.endYear = this.years[0];

    /* load product names */
    this.userService.getAllProductName().subscribe(response => {
      this.products = response.data;
      this.product = this.products[0];
      this.eventEmitter.emit('loaded');
    });

    /* load team names */
    this.userService.getAllTeamName().subscribe(response => {
      this.teams = response.data;
      this.team = this.teams[0];
      this.eventEmitter.emit('loaded');

    });

    /* call the API to get some data */
    this.eventEmitter.on('loaded', event => {

      if(++loaded == 2) {
        this.loadData();
      }
    })

    
  }

  /* on change of inputs, recompute data to draw the new chart */
  onValueChange() {
    this.loadData();
  }

  private loadData() {
    this.adminService.getWithdrawStats({
      teamName: this.team,
      productName: this.product,
      monthLowerBound: this.beginMonth,
      monthUpperBound: this.endMonth,
      yearLowerBound: this.beginYear,
      yearUpperBound: this.endYear
    }).subscribe(response => {

      this.chartLabels.splice(0, this.chartLabels.length);
      this.chartData.splice(0, this.chartData.length);      

      response.data.forEach(element => {
        this.chartLabels.push(this.months[element.month-1]+' '+element.year);
        this.chartData.push(element.withdraw);
      });

      this.chart.ngOnChanges({});

    });
  }

}
