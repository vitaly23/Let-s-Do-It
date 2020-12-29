import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserInformationService {

  public headers: HttpHeaders;
  constructor(private httpClient: HttpClient) { 
    //this.headers.set( ,"application/json");
  }

  public login(userMail: string) : Observable<Object>{
    return this.httpClient.get("");
  }
}
