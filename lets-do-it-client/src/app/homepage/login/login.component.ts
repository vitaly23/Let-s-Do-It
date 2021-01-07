import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/core/services/user-information/user';
import { UserInformationService } from 'src/app/core/services/user-information/user-information.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  // @Output() userIsLogged = new EventEmitter<User>();
  public loginForm: FormGroup;
  submitted = false;
  private loggedUser: User;
  private ngOnUnsubscribe$: Subject<void> = new Subject<void>();


  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private userInformationService: UserInformationService) { }

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
      
    this.userInformationService.login(this.loginForm.value).pipe(
      takeUntil(this.ngOnUnsubscribe$)
    ).subscribe(user =>{
      this.loggedUser = user;
      this.router.navigate([`/homepage/${this.loggedUser.userName}`]);
      // this.userIsLogged.emit(user);
    }, error => {
      console.log(error.message);
      alert(error.message);
    })
  }

  createUser(){
    this.router.navigate(["/register"]);
  }

  ngOnDestroy(): void {
    this.ngOnUnsubscribe$.next();
    this.ngOnUnsubscribe$.complete();
  }

}
