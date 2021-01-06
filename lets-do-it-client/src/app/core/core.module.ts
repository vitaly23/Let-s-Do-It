import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserInformationService } from './services/user-information/user-information.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports:[UserInformationService]
})
export class CoreModule { }
