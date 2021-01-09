import { Component, OnInit, Input } from '@angular/core';
import { Meeting } from 'src/app/core/services/meeting-information/meeting';

@Component({
  selector: 'app-meeting-info',
  templateUrl: './meeting-info.component.html',
  styleUrls: ['./meeting-info.component.scss']
})
export class MeetingInfoComponent implements OnInit {

  @Input() meeting: Meeting = {
    itemId: { space: "space", id: "id" },
    type: "string",
    name: "string",
    active: true,
    createdTimeStamp: "string",
    createdBy: {
      userId: { space: "string", email: "string" }
    },
    location: { lat: 34.011, lng: 35.0054 },

    itemAttributes: new Map<string, Object>([['name', 5], ['type', 5], ['startDate', 5]])
  };

  @Input() buttons: string = 'not-joined';

  constructor() {

  }

  ngOnInit(): void {
  }

  joinClick(): void {

  }

  editClick(): void {

  }

  deleteClick(): void {

  }

  leaveClick(): void {

  }

}
