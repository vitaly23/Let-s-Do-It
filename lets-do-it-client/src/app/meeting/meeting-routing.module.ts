import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { MeetingComponent } from './meeting.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MeetingComponent
  },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class MeetingRoutingModule { }
