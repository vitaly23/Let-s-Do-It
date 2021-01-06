import { createAction, props } from '@ngrx/store';
import { Action } from '@ngrx/store';
import { User } from '../../core/services/user-information/user';

export const loadUserInformations =   '[UserInformation] Load UserInformations';

export const getUserInformations = '[UserInformation] Add UserInformations';

export class LoadUserInformationsAction implements Action {
  readonly type = loadUserInformations;

  constructor(public payload: string) { }
}

export class GetUserInformationsAction implements Action {
  readonly type = getUserInformations;

  constructor(public payload: User) { }
}

export type UserInformationActions =
LoadUserInformationsAction | GetUserInformationsAction;



