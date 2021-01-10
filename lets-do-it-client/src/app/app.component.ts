import { Component, ViewChild } from '@angular/core';
import { User } from './core/services/user-information/user';
import { Router } from '@angular/router';
import { MatDrawer } from '@angular/material/sidenav';
import { UserInformationService } from './core/services/user-information/user-information.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  fillerNav: { title: string, route: string }[] = [
    { title: 'Meetings', route: '/meetings' },
    { title: 'Trainee details', route: '/trainee-details' },
    { title: 'My account', route: '/my-account' }
    // { title: 'map', route: '/map' }
  ]
  loggedUser$: Observable<User | void>;

  title = 'lets-do-it-client';
  public loggedUser: User;
  @ViewChild('drawer') drawer: MatDrawer;
  public showFiller = false;

  constructor(private router: Router, private userInformationService: UserInformationService) {
    this.loggedUser$ = this.userInformationService.getLoggedInUser();
  }

  public toggle() {
    this.drawer.toggle()
  }
}
