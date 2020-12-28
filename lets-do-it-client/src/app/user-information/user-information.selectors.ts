import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromUserInformation from './user-information.reducer';

export const selectUserInformationState = createFeatureSelector<fromUserInformation.State>(
  fromUserInformation.userInformationFeatureKey
);
