import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from '../../../services/admin.service';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-stats-products',
  templateUrl: './stats-products.component.html',
  styleUrls: ['./stats-products.component.css']
})
export class StatsProductsComponent implements OnInit {

  @ViewChild(BaseChartDirective)
  chart: BaseChartDirective;

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

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    // for(var i=0;i<75;i++){
    //   this.chartData.push(Math.random()*5);
    //   this.chartLabels.push('SOURCE_ANTI_TARGET');
    // }
    this.loadData();
  }

  loadData() {
    this.adminService.getProductsStats().subscribe(res => {
      res.data.forEach(elt => {
        this.chartData.push(elt.productPrice);
        this.chartLabels.push(elt.productName);
      });
      this.chart.ngOnChanges({});
    });
  }

}
