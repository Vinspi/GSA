import {Component, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {AdminService} from 'src/app/services/admin.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  private speciesNames: string[] = [];
  private selectedTarget: string;
  private selectedSource: string;
  private defaultOption: String = 'Choose';
  messageAlert: string;
  typeAlert: string;
  private _success = new Subject<string>();


  constructor(private adminService: AdminService) { }

  ngOnInit() {

    this.adminService.getAllSpeciesName().subscribe(res => {
      this.speciesNames = res.data;
      this.selectedSource = this.speciesNames[0];
      this.selectedTarget = this.speciesNames[0];
    });
  }

  private configureMessageAlert(type: string, message: string, time: number) {
    this.typeAlert = type;
    this._success.subscribe((m) => this.messageAlert = m);
    this._success.pipe(
      debounceTime(time)
    ).subscribe(() => this.messageAlert = null);
    this._success.next(message);
  }

  addProduct() {
    // this.selectedSource = $('#source option:selected').text();
    // this.selectedTarget = $('#target option:selected').text();

    console.log(this.selectedSource);
    

    if (this.selectedSource === this.defaultOption || this.selectedTarget === this.defaultOption){
      this.configureMessageAlert('danger', 'Both fields are required.', 4000);
      return;
    }

    this.adminService.addProduct({
      targetName : this.selectedTarget,
      sourceName : this.selectedSource
    }).subscribe(res => {
      if (res.status === 'SUCCESS') {
        this.configureMessageAlert('success', 'The product was successfully added.', 4000);
      } else if (res.status === 'FAIL') {
        this.configureMessageAlert('danger', 'An error occurred, this product could not be added.', 4000);
      }
    });
  }
}
