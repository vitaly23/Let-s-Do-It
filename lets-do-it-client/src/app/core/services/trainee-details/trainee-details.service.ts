import { Injectable } from '@angular/core';
import { TraineeDetails } from './trainee-details';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';

@Injectable({
  providedIn: 'root'
})
export class TraineeDetailsService {

  constructor(private httpWrapper: HttpWrapperService) { }

  public create(trainee: TraineeDetails): Observable<TraineeDetails> {
    return this.httpWrapper.post(
      `http://localhost:8081/dts/operations`,
      JSON.parse(
        JSON.stringify({

        })
      )).pipe(map((res: TraineeDetails) => {
        return res;
      }));
  }
}
