import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';

@Injectable({
  providedIn: 'root'
})
export class UserInformationService {

  constructor(private httpWrapper: HttpWrapperService) { }

  public login(userMail: string) : Observable<Object>{
    return this.httpWrapper.get("");
  }
}
