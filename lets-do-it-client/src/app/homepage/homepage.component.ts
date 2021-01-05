import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { User } from '../core/services/user-information/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  @ViewChild('drawer') drawer: MatDrawer;
  public showFiller;

  constructor() { }

  ngOnInit(): void {
  }

  public toggle(){
    this.drawer.toggle()
  }
}
