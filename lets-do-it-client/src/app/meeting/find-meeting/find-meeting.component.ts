import { Component, OnInit, Input } from '@angular/core';
import { Meeting } from 'src/app/core/services/meeting-information/meeting';

@Component({
  selector: 'app-find-meeting',
  templateUrl: './find-meeting.component.html',
  styleUrls: ['./find-meeting.component.scss']
})
export class FindMeetingComponent implements OnInit {

  @Input() meetings: Meeting[];

  constructor() { }

  ngOnInit(): void {
  }

}
