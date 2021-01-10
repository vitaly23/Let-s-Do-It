import { Injectable } from '@angular/core';
import { TraineeDetails } from './trainee-details';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';
import { Operation } from '../operations/operation';
import { User } from '../user-information/user';

@Injectable({
  providedIn: 'root'
})
export class TraineeDetailsService {

  constructor(private httpWrapper: HttpWrapperService) { }

  public create(trainee: TraineeDetails, user: User): Observable<TraineeDetails> {
    const operation: Operation = {
      invokedBy: { userId: { email: user.userId.email, space: user.userId.space } },
      type: "createTrainee",
      item: { itemId: { id: user.userId.email, space: user.userId.space } },
      operationId: { id: user.userId.email, space: user.userId.space },
      operationAttributes: new Map<string, Object>()
    };

    operation.operationAttributes.set("lat", 22);
    operation.operationAttributes.set("lng", 22);
    operation.operationAttributes.set("name", trainee.name);
    operation.operationAttributes.set("age", trainee.age);
    operation.operationAttributes.set("height", trainee.height);
    operation.operationAttributes.set("weight", trainee.weight);

    return this.httpWrapper.post(
      `http://localhost:8081/dts/operations`,
      JSON.parse(
        JSON.stringify(
          {
            type: operation.type,
            invokedBy: {
              userId: {
                space: operation.invokedBy.userId.space,
                email: operation.invokedBy.userId.email
              }
            },
            item: {
              itemId: {
                space: operation.item.itemId.space,
                id: operation.item.itemId.id
              }
            },
            operationAttributes: {
              lat: operation.operationAttributes.get("lat"),
              lng: operation.operationAttributes.get("lng"),
              name: operation.operationAttributes.get("name"),
              age: operation.operationAttributes.get("age"),
              height: operation.operationAttributes.get("height"),
              weight: operation.operationAttributes.get("weight")
            }
          }
        )
      )).pipe(map((res: TraineeDetails) => {
        return res;
      }));
  }
}
