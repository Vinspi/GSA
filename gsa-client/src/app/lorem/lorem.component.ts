import { Component, OnInit } from '@angular/core';
import { LoremServiceService, Lorem } from './../services/lorem-service.service'

@Component({
  selector: 'app-lorem',
  templateUrl: './lorem.component.html',
  styleUrls: ['./lorem.component.css']
})
export class LoremComponent implements OnInit {

  lorem: Lorem;

  constructor(private loremService: LoremServiceService) { }

  ngOnInit() {
    
    this.lorem = null;
    
    this.loremService.fetchLorem()
      .subscribe((l: Lorem) => {this.lorem = {
        text: l.text
      }
    })
  }

}
