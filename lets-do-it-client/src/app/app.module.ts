import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomepageModule } from './homepage/homepage.module';
import { HeaderModule } from './header/header.module';
import { MatButtonModule } from '@angular/material/button';
import { AgmCoreModule } from '@agm/core';
import { StoreModule } from '@ngrx/store';
import { reducers, metaReducers } from '.';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import { MeetingService } from './core/services/meeting-information/meeting.service';
import { HttpClientModule } from '@angular/common/http';

import * as UserInformationReducer from './user-information/state/user-information.reducer';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    // StoreModule.forFeature("login",reducers: UserInformationReducer)],
    MatButtonModule,
    HeaderModule,
    HomepageModule,
    CommonModule,
    StoreModule.forRoot(reducers, { metaReducers }),
    !environment.production ? StoreDevtoolsModule.instrument() : [],
    HttpClientModule
  ],
  providers: [MeetingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
