import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../services/authentification.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { Router } from "@angular/router"

@Component({
  selector: 'app-connection-form',
  templateUrl: './connection-form.component.html',
  styleUrls: ['./connection-form.component.css']
})
export class ConnectionFormComponent implements OnInit {

  email: String;
  password: String;
  error: boolean;
 
  constructor(
    private authentificationService: AuthentificationService,
    private localStorage: LocalStorage,
    private router: Router) {

    }

  ngOnInit() {
    this.error = false;
  }

  submitCredentials() {
    this.authentificationService.login(this.email, this.password).subscribe(response => {
      if (response.status == 'SUCCESS') {
        this.error = false;
        this.localStorage.setItem("user", response.data).subscribe(() => {});
        console.log("navigate to index page ...");
        
        this.localStorage.getItem("user").subscribe(user => {
          console.log(user);
          
        })
        window.location.reload();
        this.router.navigate(["/"]);
      }
      else {
        this.error = true;
        this.email = "";
        this.password = "";
      }
    });
  }

}
