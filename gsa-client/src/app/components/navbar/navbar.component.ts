import { Component, OnInit, DoCheck } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/user';
import { AuthentificationService } from '../../services/authentification.service';
import { Router } from '@angular/router';
import { Foo } from '../../foo';

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
    
    Foo.subj.subscribe(user => {
      this.user = user;
    });
    this.localStorage.getItem("user").subscribe(user => {
      this.user = <User> user;
    });
  }

  logout() {
    this.authentificationService.logout().subscribe(() => {
      this.localStorage.clear().subscribe(() => {});
      //window.location.reload();
      Foo.subj.next(null);
      this.router.navigate(['/login']);
    });
  }

  toLogin() {
    this.router.navigate(['/login']);
  }
}
