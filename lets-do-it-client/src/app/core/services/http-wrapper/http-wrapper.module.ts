import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpWrapperService } from './http-wrapper.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports:[
    HttpWrapperService
  ],
})
export class HttpWrapperModule { }
