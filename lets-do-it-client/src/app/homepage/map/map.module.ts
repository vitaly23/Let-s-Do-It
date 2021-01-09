import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgmCoreModule } from '@agm/core';
import { MapComponent } from './map.component';
import { MapRoutingModule } from './map-routing.module';


@NgModule({
  declarations: [MapComponent],
  imports: [
    CommonModule,
    // MapRoutingModule
    AgmCoreModule
  ],
  exports: [MapComponent]
})
export class MapModule { }
