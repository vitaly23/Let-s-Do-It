import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  lat = 32.109333;
  lng = 34.855499;
  zoom=10;

  constructor() { 
      
  }

  ngOnInit(): void {
  }

}
