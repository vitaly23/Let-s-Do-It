import { Component, OnInit, Input } from '@angular/core';
import { Meeting } from 'src/app/core/services/meeting-information/meeting';
import { MeetingService } from 'src/app/core/services/meeting-information/meeting.service';

@Component({
  selector: 'app-find-meeting',
  templateUrl: './find-meeting.component.html',
  styleUrls: ['./find-meeting.component.scss']
})
export class FindMeetingComponent implements OnInit {

  @Input() meetings: Meeting[];
  public chosen: number = -1;
  public sportType: string;
  public radius: number;

  constructor(private _meetingService: MeetingService) { }

  ngOnInit(): void {
  }

  searchClick(): void {
    if (this.sportType && this.sportType != '' && this.radius) {
      console.log(this.sportType);
      console.log(this.radius);
      this._meetingService.getMeetings().subscribe(meetings => { this.meetings = meetings; });
    }

  }

  setChosen(i: number): void {
    this.chosen = i;
    console.log(this.chosen);
  }

  getChosenMeetingLat(): number {
    if (this.chosen < 0) {
      return 0;
    }

    return this.meetings[this.chosen].location.lat;
  }

  getChosenMeetingLng(): number {
    if (this.chosen < 0) {
      return 0;
    }
    return this.meetings[this.chosen].location.lng;
  }

}
