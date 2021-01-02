import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserInformationService {

  constructor(private httpWrapper: HttpWrapperService) { }

  public login(userMail: any) : Observable<User>{
    return this.httpWrapper.get(
      `http://localhost:8080/dts/users/login/default_space_name/${userMail.email}`);
  }
}
