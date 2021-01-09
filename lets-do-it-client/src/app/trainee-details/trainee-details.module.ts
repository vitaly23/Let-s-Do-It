import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TraineeDetailsComponent } from './trainee-details.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { TraineeDetailsRoutingModule } from './trainee-details-routing.module';



@NgModule({
  declarations: [TraineeDetailsComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    TraineeDetailsRoutingModule
  ],
  exports: [TraineeDetailsComponent]
})
export class TraineeDetailsModule { }
