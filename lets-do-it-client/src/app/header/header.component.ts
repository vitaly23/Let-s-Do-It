import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})

export class HeaderComponent implements OnInit {

  @Output() toggleDrawer = new EventEmitter<void>();
  public showFiller = false;

  constructor() { }

  ngOnInit() {
  }

  public toggle(){
    this.toggleDrawer.emit();
  }

}
