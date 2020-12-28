import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { concatMap, map } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

import * as UserInformationActions from './user-information.actions';
import { UserInformationService } from '../core/services/user-information/user-information.service';
import { addUserInformations } from './user-information.actions';


@Injectable()
export class UserInformationEffects {


  // loadUserInformations$ = createEffect(() => {
  //   return this.actions$.pipe( 

  //     ofType(UserInformationActions.loadUserInformations),
  //     /** An EMPTY observable only emits completion. Replace with your own observable API request */
  //     concatMap(() => {
  //       return this.userInformationService.login("")
  //     }),
  //       map(user => new addUserInformations())
  //   );
  // });


  constructor(private actions$: Actions, 
              private userInformationService: UserInformationService) {}

}
