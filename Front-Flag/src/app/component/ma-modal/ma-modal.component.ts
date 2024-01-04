import { Component } from '@angular/core';
import { ConnexionService } from 'src/app/services/connexion.service';
import { AppComponent } from 'src/app/app.component';
@Component({
  selector: 'app-ma-modal',
  templateUrl: './ma-modal.component.html',
  styleUrls: ['./ma-modal.component.css']
})
export class MaModalComponent {
  constructor(private connexionService: ConnexionService, private appComponent: AppComponent) { }
  
  username: string = '';
  password: string = '';
  currentUser: string = '';
  entrezQQC2: boolean = false;
  onSubmit(){
    if (this.username.trim() !== '' && this.password.trim() !== '') {
    const user = {
      username: this.username,
      password: this.password
    };
    this.currentUser = this.connexionService.username;
    this.connexionService.updateUser(user, this.currentUser).subscribe(
      (response) => {
              // Ça s'est bien passé
              this.connexionService.username = user.username;
              this.appComponent.afficherModal = false;
              this.entrezQQC2 = false;
      },(error) => {
          console.error('Erreur lors de la requête : ', error);
          
      }
    )
  }
  else{
    this.entrezQQC2 = true;
    console.log("Entrez quelque chose");
  }
}
}
