import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,of } from 'rxjs';
import { Meeting } from './meeting';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  private getMeetingUrl = 'http://127.0.0.1:8081/dts/2021a.demo/';
  private deleteMeetingUrl = 'http://127.0.0.1:8081/dts/2021a.demo/';
  private updateMeetingUrl = 'http://127.0.0.1:8081/dts/2021a.demo/';
  

  constructor(private _httpWrapperService: HttpWrapperService) { }

  getMeetings():Observable<Meeting[]>{
    /*let meetings: Meeting[] = [
      {
        itemId:{space:"space", id:"id"},
        type:"string",
        name:"string",
        active:true,
        createdTimeStamp:"string",
        createdBy :{
          userId:{space: "string", email:"string"}
        },
        location:{lat:34.0, lng:35.0},
        itemAttributes:new Map<string,Object>()
      },
      {
        itemId:{space:"space", id:"id"},
        type:"string",
        name:"string",
        active:true,
        createdTimeStamp:"string",
        createdBy :{
          userId:{space: "string", email:"string"}
        },
        location:{lat:34.011, lng:35.0054},
        
        itemAttributes:new Map<string,Object>()
      },
    ];

    return of(meetings);*/
    return this._httpWrapperService.getWithParams(this.getMeetingUrl,[])
    .pipe(map((res: any[]) => {
      return res;
    }));
  }

  updateMeeting(meeting:Meeting): void{

  }

  deleteMeeting(meeting:Meeting): void{

  }

}
