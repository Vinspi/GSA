import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-stats-providers',
  templateUrl: './stats-providers.component.html',
  styleUrls: ['./stats-providers.component.css']
})
export class StatsProvidersComponent implements OnInit {

  public chartLabels:String[] = ['red','blue','yellow','google','yahoo','amazon'];
  public chartData:number[] = [10,20,30,50,47,120];

  public chartType:string = 'doughnut';
  public chartOptions:any = {responsive: true, maintainAspectRatio: false, legend: {display: true, position: 'top'}}

  constructor() { }

  ngOnInit() {
  }

}
