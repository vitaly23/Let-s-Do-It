import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './homepage/login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginGuardService } from './core/services/login-guard/login-guard.service';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'homepage', canActivate: [LoginGuardService],
    loadChildren: () => import('./homepage/homepage.module').then(m => m.HomepageModule)
  },
  {
    path: 'meetings', canActivate: [LoginGuardService],
    loadChildren: () => import('./meeting/meeting.module').then(m => m.MeetingModule)
  },
  /*{
    path: 'map', canActivate: [LoginGuardService],
    loadChildren: () => import('./homepage/map/map.module').then(m => m.MapModule)
  },*/
  {
    path: 'trainee-details', canActivate: [LoginGuardService],
    loadChildren: () => import('./trainee-details/trainee-details.module').then(m => m.TraineeDetailsModule)
  },
  {
    path: 'my-account', canActivate: [LoginGuardService],
    loadChildren: () => import('./homepage/user-account/user-account.module').then(m => m.UserAccountModule)
  },
  { path: '', redirectTo: '/homepage', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],

})
export class AppRoutingModule { }
