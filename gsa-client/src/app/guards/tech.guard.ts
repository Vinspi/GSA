import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from '../user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TechGuard implements CanActivate {
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean> {
    return this.isTechArea();
  }

  constructor(private localStorage: LocalStorage, private router: Router) { }

  isTechArea(): Promise<boolean> {
    return new Promise(resolve => {
      this.localStorage.getItem("user").subscribe(user => {
        if(!(<User> user).techArea)
          this.router.navigate(['/']);
        resolve((<User> user).techArea);
      })
    })
  }
}
