import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TraineeDetails } from '../core/services/trainee-details/trainee-details';
import { TraineeDetailsService } from '../core/services/trainee-details/trainee-details.service';
import { UserInformationService } from '../core/services/user-information/user-information.service';
import { User } from '../core/services/user-information/user';

@Component({
  selector: 'app-trainee-details',
  templateUrl: './trainee-details.component.html',
  styleUrls: ['./trainee-details.component.scss']
})
export class TraineeDetailsComponent implements OnInit, OnDestroy {

  public traineeDetails: FormGroup;
  public submitted = false;
  public loggedUser: User;
  private ngOnUnsubscribe$: Subject<void> = new Subject<void>();

  constructor(private formBuilder: FormBuilder,
    private traineeDetailsService: TraineeDetailsService,
    private userInformationService: UserInformationService) {

  }

  ngOnInit() {
    this.traineeDetails = this.formBuilder.group({
      name: ['', [Validators.required]],
      age: ['', [Validators.required]],
      height: ['', [Validators.required]],
      weight: ['', [Validators.required]],
    });

    this.userInformationService.getLoggedInUser()
      .pipe(takeUntil(this.ngOnUnsubscribe$)).subscribe((user: User) => {
        this.loggedUser = user;
      });
  }
  ngOnDestroy() {
    this.ngOnUnsubscribe$.next();
    this.ngOnUnsubscribe$.complete();
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
