import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoadUserInformationsAction } from 'src/app/user-information/state/user-information.actions';
import { User } from 'src/app/core/services/user-information/user';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public loginForm: FormGroup;
  submitted = false;

  constructor(private store: Store<{user: User}>,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    })
  }

  get f() { return this.loginForm.controls; }

  submit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      alert("Invalid user");
      return;
    }
      
    this.store.dispatch(new LoadUserInformationsAction(this.loginForm.value));
  }

}
