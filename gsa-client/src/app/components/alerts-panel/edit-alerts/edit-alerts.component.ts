import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit-alerts',
  templateUrl: './edit-alerts.component.html',
  styleUrls: ['./edit-alerts.component.css']
})
export class EditAlertsComponent implements OnInit {

  data: any[] =
  [
    {
        "productName": "WOLF_ANTI_SHARK",
        "seuil": 10,
        "alertType": "VISIBLE_STOCK"
    },
    {
        "productName": "WOLF_ANTI_SHARK",
        "seuil": 10,
        "alertType": "VISIBLE_STOCK"
    },
    {
      "productName": "WOLF_ANTI_SHARK",
      "seuil": 10,
      "alertType": "VISIBLE_STOCK"
    }
  ]

  constructor() { }

  ngOnInit() {
  }

}
