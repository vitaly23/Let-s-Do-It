import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/core/services/user-information/user';
import { Subject } from 'rxjs';
import { UserInformationService } from 'src/app/core/services/user-information/user-information.service';
import { takeUntil } from 'rxjs/operators';
import { TraineeDetails } from 'src/app/core/services/trainee-details/trainee-details';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit, OnDestroy {

  public accountDetails: FormGroup;
  public submitted = false;
  public loggedUser: User;
  private ngOnUnsubscribe$: Subject<void> = new Subject<void>();

  constructor(private formBuilder: FormBuilder,
    private userInformationService: UserInformationService) {

  }
  ngOnInit() {
    this.accountDetails = this.formBuilder.group({
      userName: ['', [Validators.required]],
      avatar: ['', [Validators.required]],
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


  get f() { return this.accountDetails.controls; }


  submit() {
    this.submitted = true;
    if (this.accountDetails.invalid) {
      alert("Invalid details");
      return;
    }

    this.userInformationService.updateUser(
      this.accountDetails.controls["userName"].value,
      this.accountDetails.controls["avatar"].value,
      this.loggedUser
    )
      .pipe(
        takeUntil(this.ngOnUnsubscribe$)
      ).subscribe(user => {
        console.log(`Welcome ${user.userName}`);
      }, error => {
        console.log(error.message);
        alert(error.message);
      })
  }

}
