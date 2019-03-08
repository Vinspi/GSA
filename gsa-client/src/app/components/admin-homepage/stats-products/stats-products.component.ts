import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-stats-products',
  templateUrl: './stats-products.component.html',
  styleUrls: ['./stats-products.component.css']
})
export class StatsProductsComponent implements OnInit {

  public chartLabels:String[] = [];
  public chartData:number[] = [];

  public chartType:string = 'bar';
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
  public chartOptions:any = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {display: false},
    scales:{
      xAxes: [{
          display: false
      }]
    }
  }

  constructor() { }

  ngOnInit() {
    for(var i=0;i<75;i++){
      this.chartData.push(Math.random()*5);
      this.chartLabels.push('SOURCE_ANTI_TARGET');
    }
  }

}
