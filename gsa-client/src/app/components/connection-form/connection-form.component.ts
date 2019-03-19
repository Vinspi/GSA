import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../services/authentification.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { Router } from '@angular/router';
import { User } from '../../user';
import { Subject } from 'rxjs';
import { Foo } from '../../foo';

@Component({
  selector: 'app-connection-form',
  templateUrl: './connection-form.component.html',
  styleUrls: ['./connection-form.component.css']
})
export class ConnectionFormComponent implements OnInit {

  email: String;
  password: String;
  error: boolean;
  toastTrigger:  Subject<void> = new Subject();
 
  constructor(
    private authentificationService: AuthentificationService,
    private localStorage: LocalStorage,
    private router: Router) {

    }

  ngOnInit() {
    this.localStorage.getItem("user").subscribe(user => {
      if (user != null && (<User> user).admin)
        this.router.navigate(["/"]);

      if (user != null && !(<User> user).admin)
        this.router.navigate(["/withdraw"]);
    });
    this.error = false;
  }

  submitCredentials() {
    this.authentificationService.login(this.email, this.password).subscribe(response => {
      if (response.status == 'SUCCESS') {
        this.error = false;
        this.localStorage.setItem("user", response.data).subscribe(() => {});

        console.log("submit triggered");

        Foo.subj.next(response.data);

        if(response.data.admin)
          this.router.navigate(['/']);
        else {
          this.router.navigate(['/withdraw']);
        }
        // window.location.reload();
        
      }
      else {
        this.toastTrigger.next();
        this.error = true;
        this.email = "";
        this.password = "";
      }
    });
  }

}
