import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './homepage/login/login.component';


const routes: Routes = [
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
  {path: 'login', component: LoginComponent},
  {path:'homepage', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule { }
