import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';
import { User } from './user';
import { UserRole } from './user-role';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserInformationService {

  constructor(private httpWrapper: HttpWrapperService) { }

  public login(userMail: any) : Observable<User>{
    return this.httpWrapper.getWithParams(
      `http://localhost:8080/dts/users/login/default_space_name/#{0}#`, [userMail.email])
      .pipe(map((res : any) => {
        let user: User = {
          avatar: res.avatar,
          userName: res.username,
          userId: res.userId,
          role: res.role
        } as User;
        return user;
      }));
  }

  public create(user: User) : Observable<User>{
    return this.httpWrapper.post(
      `http://localhost:8080/dts/users`, 
      { userName: user.userName,
        avatar: user.avatar,
        userId: user.userId,
        role: UserRole.PLAYER      
      } as User).pipe(map((res : User) => {
        return res;
      }));
  }
}
