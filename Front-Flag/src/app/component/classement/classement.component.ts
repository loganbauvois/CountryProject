import { Component } from '@angular/core';
import { ConnexionService } from 'src/app/services/connexion.service';
@Component({
  selector: 'app-classement',
  templateUrl: './classement.component.html',
  styleUrls: ['./classement.component.css']
})
export class ClassementComponent {

  constructor(private connexionService: ConnexionService) { }

  Users: any[] = [];
  classement(){
    this.connexionService.getClassement().subscribe(
      (response) => {
        this.Users=response;
      },
      (error) => {

      }
    );
  }
  
  ngOnInit(){
    this.classement();
  }

}
