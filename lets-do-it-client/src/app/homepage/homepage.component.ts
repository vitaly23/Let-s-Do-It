import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { TraineeDetailsService } from '../core/services/trainee-details/trainee-details.service';
import { UserInformationService } from '../core/services/user-information/user-information.service';
import { User } from '../core/services/user-information/user';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  private loggedUser: User;
  constructor(private traineeDetailsService: TraineeDetailsService,
    private userInformationService: UserInformationService) { }

  ngOnInit() {
    // this.loggedUser = this.userInformationService.getLoggedUser();
    // this.traineeDetailsService.getTraineeDetails(this.loggedUser);
  }


}
