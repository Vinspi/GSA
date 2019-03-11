import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { AddAliquoteComponent } from './add-aliquote/add-aliquote.component';

@Component({
  selector: 'app-product-manager',
  templateUrl: './product-manager.component.html',
  styleUrls: ['./product-manager.component.css']
})
export class ProductManagerComponent implements OnInit {

  @ViewChild(AddAliquoteComponent)
  private form: AddAliquoteComponent;

  toastBody: String;
  toastHeader: String;
  toastType: String;
  toastTrigger: Subject<void> = new Subject();

  constructor() { }

  ngOnInit() {
  }

  toaster(event) {

    this.toastBody = event.toastBody;
    this.toastHeader = event.toastHeader;
    this.toastType = event.toastType;

    this.toastTrigger.next();
  }

  reloadForm() {
    this.form.ngOnChanges({});
  }

}
