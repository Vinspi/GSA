import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from '../../../services/admin.service';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-stats-providers',
  templateUrl: './stats-providers.component.html',
  styleUrls: ['./stats-providers.component.css']
})
export class StatsProvidersComponent implements OnInit {

  @ViewChild(BaseChartDirective)
  chart: BaseChartDirective;

  public chartLabels:String[];
  public chartData:number[];

  public chartType:string = 'doughnut';
  public chartOptions:any = {responsive: true, maintainAspectRatio: false, legend: {display: true, position: 'top'}}

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.chartData = [];
    this.chartLabels = [];
    this.adminService.getProvidersStats().subscribe(res => {
      res.data.forEach(elt => {
        this.chartData.push(elt.providerStat);
        this.chartLabels.push(elt.providerName);
      });
      this.chart.ngOnChanges({});
    });
  }

}
