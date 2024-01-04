import { Component } from '@angular/core';
import { ConnexionService } from './services/connexion.service';
import { Router } from '@angular/router';
import { ClassementComponent } from './component/classement/classement.component';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Front-Flag';
  constructor(private connexionService: ConnexionService, private router: Router) { }
  afficherModal: boolean = false;
  afficherClassement: boolean = false;

  estConnecte(): boolean {
    return this.connexionService.estConnecte;
  }
  openRank(): void {
   
    if (this.afficherClassement == true) {
      this.afficherClassement = false
    }
    else {
      this.afficherClassement = true
    }
  }
  ouvrirModal(): void {
    if(this.afficherModal == true){
      this.afficherModal = false
    }
    else{
      this.afficherModal = true
    }
  }


 
}
