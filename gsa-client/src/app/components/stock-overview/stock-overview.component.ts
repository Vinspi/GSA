import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ProductOverview } from '../../productOverview';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-stock-overview',
  templateUrl: './stock-overview.component.html',
  styleUrls: ['./stock-overview.component.css']
})
export class StockOverviewComponent implements OnInit {

  data: Array<ProductOverview>;
  dtTrigger: Subject<any> = new Subject();

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getAllProductOverview().subscribe(response => {
      this.data = <Array<ProductOverview>> response.data;
      console.log("data : "+JSON.stringify(this.data));
      this.dtTrigger.next();
    });
  }
}
