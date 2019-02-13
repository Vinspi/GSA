import { Component, OnInit } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/user';
import { AuthentificationService } from '../../services/authentification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  user: User;

  constructor(
    private authentificationService: AuthentificationService,
    private localStorage: LocalStorage,
    private router: Router) {

    }

  ngOnInit() {
    console.log("init navbar");
    
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    })
  }

  logout() {
    this.authentificationService.logout().subscribe(() => {
      this.localStorage.clear().subscribe(() => {});
      window.location.reload();
      this.router.navigate(['/']);
    });
  }

  toLogin() {
    this.router.navigate(['/login']);
  }
}
