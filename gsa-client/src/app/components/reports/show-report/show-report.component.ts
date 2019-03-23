import {
  Component,
  AfterViewInit,
  OnInit,
  ViewChild,
} from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { UserService } from 'src/app/services/user.service';
import { TransactionInfoDatatableComponent } from '../../reports/transaction-info-datatable/transaction-info-datatable.component';
import { EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';
import { Big } from 'big.js';

@Component({
  selector: 'app-show-report',
  templateUrl: './show-report.component.html',
  styleUrls: ['./show-report.component.css']
})
export class ShowReportComponent implements AfterViewInit, OnInit {
  @ViewChild(TransactionInfoDatatableComponent)
  dtElement: TransactionInfoDatatableComponent;

  toast: EventEmitter<any> = new EventEmitter();
  toastTrigger: Subject<void> = new Subject();
  toastBody: String;
  toastHeader: String;
  toastType: String;

  quarterBill: Big;
  teamLoss: Big;

  years: Array<string>;
  teamNames: Array<string>;
  selectedTeam: string;
  selectedQuarter: string;

  quartersText: Array<string>;
  quarters: Map<string, any>;

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {}

  ngAfterViewInit(): void {
    // Need to be sure that the items property is defined in the datatable child component.
    this.initializeQuarters()
      .then(quarterNumber => {
        if (quarterNumber > 0) {
          this.userService.getAllTeamName().subscribe(res => {
            if (res && res.data.length > 0) {
              this.teamNames = res.data;
              this.teamNames.sort((left, right) => (left > right ? 1 : -1));
              this.selectedTeam = this.teamNames[0];
              this.updateAllData();
            }
          });
        }
      }).catch(() => this.displayCouldNotRetrieveDataToast());
  }

  private displayCouldNotRetrieveDataToast() {
    this.toastBody = 'Some data could not be retrieved.';
    this.toastHeader = 'Error';
    this.toastType = 'danger';
    this.toastTrigger.next();
  }

  private initializeQuarters(): Promise<Number> {
    return new Promise((resolve, reject) => {
      this.adminService.getQuarterAndYearValuesOfAllEditableReports().subscribe(res => {
        if (res.status === 'SUCCESS') {
          this.quartersText = new Array();
          this.quarters = new Map();
          for (const obj of res.data) {
            const key = this.quarterToText(obj.quarter) + obj.year;
            this.quartersText.push(key);
            this.quarters.set(key, obj);
          }

          if (this.quarters.size > 0) {
            this.selectedQuarter = this.quartersText[0];
          }
          resolve(this.quarters.size);
        } else {
          reject();
        }
      });
    });
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

  public updateAllData() {
    this.dtElement.updateTransactionData(this.selectedTeam, this.getSelectedQuarter(), this.getSelectedYear());
    this.updateWithdrawnTransactionsTotalCost();
  }

  private updateWithdrawnTransactionsTotalCost() {
    this.adminService.getWithdrawnProductsTotalCost(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.quarterBill = new Big(response.data);
      } else {
        this.quarterBill = new Big(0);
      }
    });
  }

  private updateRemainingLosses() {
    this.adminService.getRemainingReportLosses(this.getSelectedQuarter(), this.getSelectedYear()).subscribe(response => {
      if (response.status === 'SUCCESS') {
        this.teamLoss = new Big(response.data);
      } else {
        this.teamLoss = new Big(0);
      }
    });
  }
}
