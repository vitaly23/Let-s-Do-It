import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomepageComponent } from './homepage.component';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { HeaderModule } from '../header/header.module';
import { MatSidenavModule } from '@angular/material/sidenav';



@NgModule({
  declarations: [HomepageComponent],
  imports: [
    CommonModule,
    HeaderModule,
    MatIconModule,
    BrowserModule,
    MatCardModule,
    FormsModule,
    MatSidenavModule
  ],
  exports:[HomepageComponent]
})
export class HomepageModule { }
