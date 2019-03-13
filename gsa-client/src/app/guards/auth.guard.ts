import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthentificationService } from '../services/authentification.service';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from '../user';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthentificationService, private router : Router, private localStorage: LocalStorage) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
      
    

    return this.checkLogin(state.url);
  }

  checkLogin(url: String): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      this.localStorage.getItem("user").subscribe(user => {

        if (user === null ){
          this.router.navigate(['/login']);
          resolve(false);
        }
        else {
          resolve(true);
        }
      })
    })

  }
}
