import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { MapComponent } from './map.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MapComponent
  },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class MapRoutingModule { }
