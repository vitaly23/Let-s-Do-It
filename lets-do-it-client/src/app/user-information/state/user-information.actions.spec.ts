import * as fromUserInformation from './user-information.actions';

describe('loadUserInformations', () => {
  it('should return an action', () => {
    expect(fromUserInformation.loadUserInformations().type).toBe('[UserInformation] Load UserInformations');
  });
});
