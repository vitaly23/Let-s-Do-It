import { Component, OnInit } from '@angular/core';
import { Meeting } from '../core/services/meeting-information/meeting';
import { MeetingService } from '../core/services/meeting-information/meeting.service';

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html',
  styleUrls: ['./meeting.component.scss']
})
export class MeetingComponent implements OnInit {

  public meetings: Meeting[] = [];

  constructor(private _meetingService: MeetingService) { 

  }

  ngOnInit(): void {
    this._meetingService.getMeetings().subscribe(meetings=>{this.meetings=meetings;});
  }

  meetingClick():void{
    // go to Meeting screen
  }

}
