import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { User } from '../core/services/user-information/user';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  @ViewChild('drawer') drawer: MatDrawer;
  public showFiller;
  public loggedUser: User;

  constructor() { }

  ngOnInit(): void {
  }

  public toggle(){
    this.drawer.toggle()
  }

  public userLogged($event:User){
    this.loggedUser = $event
  }
}
