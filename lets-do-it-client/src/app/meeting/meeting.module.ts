import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeetingsComponent } from './meetings.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MapModule } from '../homepage/map/map.module';
import { MeetingInfoComponent } from './meeting-info/meeting-info.component';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { FindMeetingComponent } from './find-meeting/find-meeting.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MeetingRoutingModule } from './meeting-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { CreateMeetingComponent } from './create-meeting/create-meeting.component';


@NgModule({
  declarations: [MeetingsComponent, MeetingInfoComponent, FindMeetingComponent, CreateMeetingComponent],
  imports: [
    CommonModule,
    MatTabsModule,
    MatCardModule,
    MatExpansionModule,
    MatGridListModule,
    MatInputModule,
    MeetingRoutingModule,
    MapModule,
    MatButtonModule,
    FormsModule,
    ScrollingModule
  ],
  exports: [MeetingsComponent],
})
export class MeetingModule { }
