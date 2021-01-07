import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatListModule} from '@angular/material/list';
import { MeetingComponent } from './meeting.component';



@NgModule({
  declarations: [MeetingComponent],
  imports: [
    CommonModule,
    MatListModule
  ],
  exports:[MeetingComponent],

})
export class MeetingModule { }
