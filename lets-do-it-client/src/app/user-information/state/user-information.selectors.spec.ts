import * as fromUserInformation from './user-information.reducer';
import { selectUserInformationState } from './user-information.selectors';

describe('UserInformation Selectors', () => {
  it('should select the feature state', () => {
    const result = selectUserInformationState({
      [fromUserInformation.userInformationFeatureKey]: {}
    });

    expect(result).toEqual({});
  });
});
