import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {

  @Input() toastHeader: String;
  @Input() toastBody: String;
  @Input() event: Observable<void>;
  @Input() timeout: number;
  @Input() toastType: String;

  isVisible: boolean;

  constructor() { }

  ngOnInit() {
    this.isVisible = false;
    this.event.subscribe(() => {
      this.triggerToast();
    })
  }

  triggerToast() {
    this.isVisible = true;
    setTimeout(() => {
      this.isVisible = false;
    }, this.timeout);
  }

  closeToast() {
    this.isVisible = false;
  }

}
