import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderModule } from './header/header.module';
import { AgmCoreModule } from '@agm/core';
import { environment } from '../environments/environment';
import { MeetingService } from './core/services/meeting-information/meeting.service';
import { HttpClientModule } from '@angular/common/http';
import { LoginModule } from './homepage/login/login.module';
import { RegistrationModule } from './registration/registration.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatInputModule } from '@angular/material/input';
import { HomepageRoutingModule } from './homepage/homepage-routing.module';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    MatSidenavModule,
    MatToolbarModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    HeaderModule,
    LoginModule,
    RegistrationModule,
    AppRoutingModule,
    HomepageRoutingModule,
    CommonModule,
    HttpClientModule,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatListModule,
    MatIconModule
  ],
  providers: [MeetingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
