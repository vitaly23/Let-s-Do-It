import { Action, createReducer, on } from '@ngrx/store';
import * as UserInformationActions from './user-information.actions';
import { User } from '../../core/services/user-information/user'; 
import { getUserInformations } from './user-information.actions';

export const userInformationFeatureKey = 'userInformation';

export interface State {
  user: User;
}

export const initialState: State = {
  user: null
};


export function reducer(state = initialState, action: UserInformationActions.UserInformationActions)
: State {
  switch (action.type) {
    case getUserInformations:
      const user = action.payload;
      return {
        ...initialState,
        user
      }
  
    default:
      return state;
  }
}
