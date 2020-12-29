import { createAction, props } from '@ngrx/store';
import { Action } from '@ngrx/store';
import { User } from '../../core/services/user-information/user';

export const loadUserInformations =   '[UserInformation] Load UserInformations';

export const addUserInformations = '[UserInformation] Add UserInformations';

export class LoadUserInformationsAction implements Action {
  readonly type = loadUserInformations;

  constructor(public payload: string) { }
}

export class AddUserInformationsAction implements Action {
  readonly type = addUserInformations;

  constructor(public payload: User) { }
}

export type UserInformationActions =
LoadUserInformationsAction | AddUserInformationsAction;



