import { Component, OnInit } from '@angular/core';
import { Meeting } from '../core/services/meeting-information/meeting';
import { MeetingService } from '../core/services/meeting-information/meeting.service';
import { MatTabChangeEvent } from '@angular/material/tabs';

@Component({
  selector: 'app-meetings',
  templateUrl: './meetings.component.html',
  styleUrls: ['./meetings.component.scss']
})
export class MeetingsComponent implements OnInit {

  public meetings: Meeting[] = [];

  constructor(private _meetingService: MeetingService) {

  }

  ngOnInit(): void {
    this._meetingService.getMeetings().subscribe(meetings => { this.meetings = meetings; });
  }

  onTabClick(event: MatTabChangeEvent) {
    switch (event.tab.textLabel) {
      case "Joined Meetings":
        break;
      case "My Meetings":
        break;
    }
  }

}
