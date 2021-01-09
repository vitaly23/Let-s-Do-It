import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserInformationService } from '../user-information/user-information.service';
import { take, switchMap, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginGuardService implements CanActivate {

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> {
    return this.userInformationService.getLoggedInUser().pipe(take(1), map(loggedInUser => {
      if (loggedInUser == null) {
        this.router.navigateByUrl('/login');
        return false;
      }

      return true;
    }));
  }

  constructor(private userInformationService: UserInformationService, private router: Router) { }
}
