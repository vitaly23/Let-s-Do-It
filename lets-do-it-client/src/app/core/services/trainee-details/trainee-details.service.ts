import { Injectable } from '@angular/core';
import { TraineeDetails } from './trainee-details';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { HttpWrapperService } from '../http-wrapper/http-wrapper.service';
import { Operation } from '../operations/operation';
import { User } from '../user-information/user';
import { Item } from '../operations/item';
import { ItemBoundary } from '../operations/item-boundary';

@Injectable({
  providedIn: 'root'
})
export class TraineeDetailsService {

  public traineeDetails$: Subject<TraineeDetails | void> = new BehaviorSubject(null);
  constructor(private httpWrapper: HttpWrapperService) { }

  public getCurrentTraineDetails(): Observable<TraineeDetails | void> {
    return this.traineeDetails$.asObservable();
  }

  public updateExistingTrainee(managerSpace: string, managerEmail: string, itemSpace: string, itemId: string, item: ItemBoundary) {
    return this.httpWrapper.putWithParams(
      `http://localhost:8081/dts/items/#{0}#/#{1}#/#{2}#/#{3}#`, [managerSpace, managerEmail, itemSpace, itemId],
      JSON.parse(
        JSON.stringify({
          name: item.name,
          type: item.type,
          itemId: item.itemId,
          //role: UserRole.PLAYER
        })
      )
    ).pipe(map((res: TraineeDetails) => {
      return res;
    }), tap(traineeDetails => this.traineeDetails$.next(traineeDetails)));
  }

  public getTraineeDetails(user: User): Observable<TraineeDetails> {
    const operation: Operation = {
      invokedBy: { userId: { email: user.userId.email, space: user.userId.space } },
      type: "getTraineeDetails",
      item: { itemId: { id: user.userId.email, space: user.userId.space } },
      operationId: { id: user.userId.email, space: user.userId.space },
    };
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
                id: "5ff999207b7abf51c3395fc9"
              }
            },
          }
        )
      )).pipe(map((res: any) => {
        const traineeDetails: TraineeDetails = {
          age: res.itemAttributes.age,
          height: res.itemAttributes.height,
          weight: res.itemAttributes.wieght,
          name: res.name
        };
        return traineeDetails;
      }), tap(traineeDetails => this.traineeDetails$.next(traineeDetails)));
  }

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
                id: "5ff998f97b7abf51c3395fc8"
              }
            },
            operationAttributes: {
              lat: operation.operationAttributes.get("lat"),
              lng: operation.operationAttributes.get("lng"),
              name: operation.operationAttributes.get("name"),
              age: operation.operationAttributes.get("age"),
              height: operation.operationAttributes.get("height"),
              wieght: operation.operationAttributes.get("weight")
            }
          }
        )
      )).pipe(map((res: TraineeDetails) => {
        return res;
      }));
  }
}
