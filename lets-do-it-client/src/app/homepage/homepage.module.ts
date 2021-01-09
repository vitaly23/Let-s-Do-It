import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomepageComponent } from './homepage.component';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { HeaderModule } from '../header/header.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MapModule } from './map/map.module';
import { MatButtonModule } from '@angular/material/button';
import { MeetingModule } from '../meeting/meeting.module';

@NgModule({
  declarations: [HomepageComponent],
  imports: [
    CommonModule,
    HeaderModule,
    MatIconModule,
    MapModule,
    MatCardModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatInputModule,
    MeetingModule,
  ],
  exports: [HomepageComponent]
})
export class HomepageModule { }
