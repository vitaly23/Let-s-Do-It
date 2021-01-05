import { Component } from '@angular/core';
import { User } from './core/services/user-information/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'lets-do-it-client';
  public loggedUser: User;

  constructor(private router: Router) {

  }
  public userLogged($event:User){
    this.loggedUser = $event
    this.router.navigate(["/homepage"]);
  }
}
