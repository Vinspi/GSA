import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceGuard implements CanActivate {

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean> {
    return this.checkMaintenance();
  }

  constructor(private userService: UserService, private router: Router) { }

  checkMaintenance(): Promise<boolean> {

    return new Promise<boolean>(resolve => {
      this.userService.isMaintenance().subscribe(res => {
        if (res.data)
          this.router.navigate(['maintenance']);
        resolve(!res.data);
      });
    });

  }
}
