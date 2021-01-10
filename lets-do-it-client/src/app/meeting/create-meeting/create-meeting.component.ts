import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-meeting',
  templateUrl: './create-meeting.component.html',
  styleUrls: ['./create-meeting.component.scss']
})
export class CreateMeetingComponent implements OnInit {

  public name: string;
  public avatar: string;
  public typeOfSport: string;
  public startDate: Date;
  public endDate: Date;
  public lat: string;
  public lng: string;

  constructor() { }

  ngOnInit(): void {
  }

  createClick(): void {
    console.log(this.startDate);
    console.log(this.endDate);
  }

}
