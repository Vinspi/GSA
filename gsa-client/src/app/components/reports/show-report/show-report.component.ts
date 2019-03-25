import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild,
  Output,
  EventEmitter,
} from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { UserService } from 'src/app/services/user.service';
import { TransactionInfoDatatableComponent } from '../../reports/transaction-info-datatable/transaction-info-datatable.component';
import { Big } from 'big.js';
import { AngularCsv } from 'angular7-csv/dist/Angular-csv';
import { User } from 'src/app/user';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { TeamReport } from 'src/app/teamReport';
import { JsonResponse } from 'src/app/services/request-interfaces/json-response';

@Component({
  selector: 'app-show-report',
  templateUrl: './show-report.component.html',
  styleUrls: ['./show-report.component.css']
})
export class ShowReportComponent implements AfterViewInit, OnInit {
  @ViewChild('transactions')
  dtElement: TransactionInfoDatatableComponent;

  teamReports: Array<TeamReport>;

  quarterBill: Big;
  teamLoss: Big;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;

  quartersText: Array<string>;
  quarters: Map<string, any>;

  user: User;

  constructor(
    private userService: UserService,
    private adminService: AdminService,
    private localStorage: LocalStorage
  ) { }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    this.localStorage.getItem('user').subscribe(user => {
      this.user = <User>user;
      if (this.user.admin) {
        this.adminService.getAllTeamReports().subscribe(res => {
          this.initData(res);
        });
      } else {
        this.userService.getTeamReports().subscribe(res => {
          this.initData(res);
        });
      }
    });
  }

  private initData(response: JsonResponse) {
    if (response.status === 'SUCCESS') {
      this.teamReports = <Array<TeamReport>>response.data;
    } else {
      this.teamReports = [];
    }
    this.initializeTeams();
    if (this.teamNames.length > 0) {
      this.onTeamChange();
    }
    console.log(JSON.stringify(this.teamReports));
  }

  private getSelectedQuarter(): string {
    return this.quarters.get(this.selectedQuarter).quarter;
  }

  private getSelectedYear(): string {
    return this.quarters.get(this.selectedQuarter).year;
  }
  private quarterToText(quarter: string): string {
    if (quarter === 'QUARTER_1') {
      return 'First quarter of ';
    }
    if (quarter === 'QUARTER_2') {
      return 'Second quarter of ';
    }
    if (quarter === 'QUARTER_3') {
      return 'Third quarter of ';
    }
    if (quarter === 'QUARTER_4') {
      return 'Fourth quarter of ';
    } else {
      return null;
    }
  }

  initializeTeams(): void {
    this.teamNames = [];
    for (const teamReport of this.teamReports) {
      this.teamNames.push(teamReport.teamName);
    }
    if (this.teamNames.length > 0) {
      this.selectedTeam = this.teamNames[0];
    }
  }

  updateQuarters(): void {
    this.quartersText = new Array();
    this.quarters = new Map();
    const teamReport = this.teamReports.find(teamReport => teamReport.teamName === this.selectedTeam);
    for (const report of teamReport.reports) {
      const key = this.quarterToText(report.quarter) + report.year;
      this.quartersText.push(key);
      this.quarters.set(key, {
        quarter: report.quarter,
        year: report.year
      });
    }
    if (this.quarters.size > 0) {
      this.selectedQuarter = this.quartersText[0];
    }
  }

  updateTeamQuarterBill(): void {
    this.quarterBill = this.teamReports
    .find(teamReport => teamReport.teamName === this.selectedTeam).reports
    .find(report => report.quarter === this.getSelectedQuarter() && report.year === this.getSelectedYear()).teamWithdrawalCost;
  }

  updateTeamLoss(): void {
    if (this.user.admin) {
      this.teamLoss = this.teamReports
      .find(teamReport => teamReport.teamName === this.selectedTeam).reports
      .find(report => report.quarter === this.getSelectedQuarter() && report.year === this.getSelectedYear()).teamLoss;
    }
  }

  updateTeamWithdrawals(): void {
    this.dtElement.updateTransactionDatatable(this.teamReports
    .find(teamReport => teamReport.teamName === this.selectedTeam).reports
    .find(report => report.quarter === this.getSelectedQuarter() && report.year === this.getSelectedYear()).withdrawnTransactions);
  }

  onTeamChange(): void {
    this.updateQuarters();
    if (this.quarters.size > 0) {
      this.updateTeamQuarterBill();
      this.updateTeamLoss();
      this.updateTeamWithdrawals();
    } else {
      this.quarterBill = new Big(0);
      this.teamLoss = new Big(0);
    }
  }

  onQuarterChange(): void {
    this.updateTeamQuarterBill();
    this.updateTeamLoss();
    this.updateTeamWithdrawals();
  }

  exportToCsv(): AngularCsv {
    const csvOptions = {
      fieldSeparator: ';',
      quoteStrings: '"',
      decimalseparator: '.',
      showLabels: true,
      showTitle: false,
      useBom: true,
    };

    const csvData: Array<Array<any>> = [
      ['Team loss', this.teamLoss, 'Team withdrawal bill', this.quarterBill],
      [],
      this.dtElement.headers,
      ...this.dtElement.items
    ];
    return new AngularCsv(csvData, this.selectedTeam + '_bill_' + this.getSelectedQuarter() + '_' + this.getSelectedYear(), csvOptions);
  }

}
