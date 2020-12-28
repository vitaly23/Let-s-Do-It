import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EffectsModule } from '@ngrx/effects';
import { UserInformationEffects } from './user-information.effects';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    EffectsModule.forFeature([UserInformationEffects])
  ]
})
export class UserInformationModule { }
