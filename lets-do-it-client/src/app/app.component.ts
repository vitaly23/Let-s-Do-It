import { Component, ViewChild } from '@angular/core';
import { User } from './core/services/user-information/user';
import { Router } from '@angular/router';
import { MatDrawer } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'lets-do-it-client';
  public loggedUser: User;
  @ViewChild('drawer') drawer: MatDrawer;
  public showFiller = false;

  constructor(private router: Router) {

  }

  public toggle(){
    this.drawer.toggle()
  }
}
