import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomepageComponent } from './homepage.component';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { HeaderModule } from '../header/header.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { LoginComponent } from './login/login.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MapComponent } from './map/map.component';
import { MapModule } from './map/map.module';
import { AgmCoreModule } from '@agm/core';


@NgModule({
  declarations: [HomepageComponent, LoginComponent, MapComponent],
  imports: [
    CommonModule,
    HeaderModule,
    MatIconModule,
    BrowserModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatInputModule,
    MapModule,
    AgmCoreModule.forRoot({
      apiKey: ''
    }),
  ],
  exports:[HomepageComponent]
})
export class HomepageModule { }
