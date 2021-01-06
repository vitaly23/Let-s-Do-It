import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { UserInformationEffects } from './user-information.effects';

describe('UserInformationEffects', () => {
  let actions$: Observable<any>;
  let effects: UserInformationEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UserInformationEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.inject(UserInformationEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
