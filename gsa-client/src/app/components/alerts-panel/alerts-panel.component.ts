import { Component, OnInit, ViewChild } from '@angular/core';
import { TriggeredAlertsComponent } from './triggered-alerts/triggered-alerts.component';
import { EditAlertsComponent } from './edit-alerts/edit-alerts.component';

@Component({
  selector: 'app-alerts-panel',
  templateUrl: './alerts-panel.component.html',
  styleUrls: ['./alerts-panel.component.css']
})
export class AlertsPanelComponent implements OnInit {

  @ViewChild(TriggeredAlertsComponent)
  private triggeredAlertsComponent: TriggeredAlertsComponent;

  @ViewChild(EditAlertsComponent)
  private editAlertComponent: EditAlertsComponent;

  constructor() { }

  ngOnInit() {
  }

  updateTriggeredAlerts(){
    this.triggeredAlertsComponent.ngOnChanges({});
  }

  updateNewAlert() {
    this.updateTriggeredAlerts();
    this.editAlertComponent.ngOnChanges({});
  }
}
