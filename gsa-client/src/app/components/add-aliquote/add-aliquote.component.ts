import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { aliquoteOverview } from '../../aliquoteOverview';
import { Subject } from 'rxjs';




@Component({
  selector: 'app-add-aliquote',
  templateUrl: './add-aliquote.component.html',
  styleUrls: ['./add-aliquote.component.css']
})
export class AddAliquoteComponent implements OnInit {

  data: Array<aliquoteOverview>;
  dtTrigger: Subject<any> = new Subject();

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getAllSpeciesName().subscribe(response => {
      this.data = <Array<aliquoteOverview>> response.data;
      console.log("data : "+JSON.stringify(this.data[0]));
      this.dtTrigger.next();
    });
  }

}
