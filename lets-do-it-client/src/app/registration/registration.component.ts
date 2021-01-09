import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { User } from '../core/services/user-information/user';
import { takeUntil } from 'rxjs/operators';
import { UserInformationService } from '../core/services/user-information/user-information.service';
import { UserRole } from '../core/services/user-information/user-role';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  public registerForm: FormGroup;
  submitted = false;
  private ngOnUnsubscribe$: Subject<void> = new Subject<void>();

  constructor(private formBuilder: FormBuilder,
    private userInformationService: UserInformationService) {

  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', [Validators.required]],
      avatar: ['', [Validators.required]],
    })
  }

  get f() { return this.registerForm.controls; }


  submit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      alert("Invalid user");
      return;
    }

    const user: User = {
      avatar: this.registerForm.controls["avatar"].value,
      role: UserRole.PLAYER,
      userId: { email: this.registerForm.controls["email"].value, space: 'default_space' },
      userName: this.registerForm.controls["name"].value
    };
    this.userInformationService.create(user).pipe(
      takeUntil(this.ngOnUnsubscribe$)
    ).subscribe(user => {
      console.log(`Welcome ${user.userName}`);
    }, error => {
      console.log(error.message);
      alert(error.message);
    })
  }
}
