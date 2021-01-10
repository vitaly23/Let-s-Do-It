import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../core/services/user-information/user';
import { Subject } from 'rxjs';
import { TraineeDetailsService } from '../core/services/trainee-details/trainee-details.service';
import { UserInformationService } from '../core/services/user-information/user-information.service';
import { takeUntil, switchMap } from 'rxjs/operators';
import { TraineeDetails } from '../core/services/trainee-details/trainee-details';
import { UserRole } from '../core/services/user-information/user-role';

@Component({
  selector: 'app-trainee',
  templateUrl: './trainee.component.html',
  styleUrls: ['./trainee.component.scss']
})
export class TraineeComponent implements OnInit, OnDestroy {

  public traineeDetails: FormGroup;
  public submitted = false;
  public loggedUser: User;
  public trainee: TraineeDetails;
  private ngOnUnsubscribe$: Subject<void> = new Subject<void>();

  constructor(private formBuilder: FormBuilder,
    private traineeDetailsService: TraineeDetailsService,
    private userInformationService: UserInformationService) {

  }

  ngOnInit() {

    this.userInformationService.getLoggedInUser()
      .pipe(takeUntil(this.ngOnUnsubscribe$)).subscribe((user: User) => {
        this.loggedUser = user;
        this.traineeDetailsService.getTraineeDetails(this.loggedUser)
          .pipe(takeUntil(this.ngOnUnsubscribe$))
          .subscribe(trainee => {
            this.trainee = trainee;
            this.initForm();
          });
      });
  }
  ngOnDestroy() {
    this.ngOnUnsubscribe$.next();
    this.ngOnUnsubscribe$.complete();
  }

  initForm() {
    this.traineeDetails = this.formBuilder.group({
      name: [this.trainee.name, [Validators.required]],
      age: [this.trainee.age, [Validators.required]],
      height: [this.trainee.height, [Validators.required]],
      weight: [this.trainee.weight, [Validators.required]],
    });
  }


  get f() { return this.traineeDetails.controls; }


  submit() {
    this.submitted = true;
    if (this.traineeDetails.invalid) {
      alert("Invalid details");
      return;
    }

    const trainee: TraineeDetails = {
      name: this.traineeDetails.controls["name"].value,
      age: this.traineeDetails.controls["age"].value,
      height: this.traineeDetails.controls["height"].value,
      weight: this.traineeDetails.controls["weight"].value,
    };

    this.loggedUser.role = UserRole.MANAGER;
    this.userInformationService.updateUser(this.loggedUser.userName,
      this.loggedUser.avatar, this.loggedUser).pipe(takeUntil(this.ngOnUnsubscribe$)).subscribe(
        user => {

        }
      );

    this.traineeDetailsService.create(trainee, this.loggedUser).pipe(
      takeUntil(this.ngOnUnsubscribe$)
    ).subscribe(trainee => {
      console.log(`Welcome ${trainee.name}`);
    }, error => {
      console.log(error.message);
      alert(error.message);
    })
  }
}