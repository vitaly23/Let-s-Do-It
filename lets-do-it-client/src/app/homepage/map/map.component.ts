import { Component, OnInit, Input } from '@angular/core';
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
  @Input() height: number = 180;
  @Input() width: number = 180;
  @Input() zoom: number = 10;
  @Input() lat: number = 32.109333;
  @Input() lng: number = 34.855499;

  constructor(private _meetingService: MeetingService) {

  }

  ngOnInit(): void {
    this._meetingService.getMeetings().subscribe(meetings => { this.meetings = meetings; });
    console.log('width ' + this.width + ' height ' + this.height);
  }

}
