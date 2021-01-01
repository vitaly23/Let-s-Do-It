import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dictionary } from '@ngrx/entity';
import { Meeting } from './meeting';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  public meetingsUrl = 'http://127.0.0.1:8081/dts/2021a.demo/';

  constructor(private http: HttpClient) { }

  getMeetings():Observable<Meeting[]>{
    return this.http.get<Meeting[]>(this.meetingsUrl);
  }
}
