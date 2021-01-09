import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { TraineeDetailsComponent } from './trainee-details.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: TraineeDetailsComponent
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TraineeDetailsRoutingModule { }
