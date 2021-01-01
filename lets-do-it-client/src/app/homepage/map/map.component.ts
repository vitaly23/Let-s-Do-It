import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Meeting } from 'src/app/core/services/meeting-information/meeting';
import { MeetingService } from 'src/app/core/services/meeting-information/meeting.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  public meetings: Meeting[];

  lat = 32.109333;
  lng = 34.855499;
  zoom=10;

  constructor(private _meetingService: MeetingService) { 
    
  }

  ngOnInit(): void {
    this._meetingService.getMeetings().subscribe(meetings=>{this.meetings=meetings;});
  }

}
