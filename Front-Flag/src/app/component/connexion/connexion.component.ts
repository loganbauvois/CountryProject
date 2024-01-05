import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from 'src/app/services/connexion.service';

@Component({
  selector: 'app-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.css']
})
export class ConnexionComponent {

  constructor(private connexionService: ConnexionService, private router: Router) { }
  username: string = '';
  password: string = '';
  compteDeja: boolean = false;
  usermdpincorrect: boolean = false;
  entrezQQC: boolean = false;
  createUser(){
    const user = {
      username: this.username,
      password: this.password
    };

    if (this.username.trim() !== '' && this.password.trim() !== '') {
      console.log('Creation');
      this.connexionService.createUser(user).subscribe(
        (response) => {
          console.log('Réponse du serveur : ', response);
         
            console.log("Compte créé");
            this.username = '';
            this.password = '';
            this.compteDeja = false;
            this.usermdpincorrect = false;

        },
        (error) => {
          if(error.status === 409){
            console.log("Compte déjà exsistant");
            this.compteDeja = true;
            this.usermdpincorrect = false;
            this.entrezQQC = false;
          }
          // Gérer l'erreur ici
        }
      );

      // Autres actions de connexion à implémenter ici
    } else {
      console.log('Veuillez saisir un nom d\'utilisateur et un mot de passe valides.');
      // Vous pouvez également afficher un message d'erreur à l'utilisateur
    }
  }

  onSubmit() {
    const user = {
      username: this.username,
      password: this.password
    };

    if (this.username.trim() !== '' && this.password.trim() !== '') {
      console.log('Connexion');
      this.connexionService.connecter(user.username, user.password).subscribe(
        (response) => {
          console.log('Réponse du serveur : ', response);
          if(response == true){

            this.connexionService.estConnecte = true;
            this.connexionService.username = this.username;
            this.username = '';
            this.password = '';
            this.router.navigate(['/jeu']);
          }
          else{
            console.log('username ou password incorrect');
            this.compteDeja = false;
            this.usermdpincorrect = true;
            this.entrezQQC = false;
            //compte pas bon
          }
         
        },
        (error) => {
          if(error.status === 500){
            console.log('username ou password incorrect');
            this.compteDeja = false;
            this.usermdpincorrect = true;
            this.entrezQQC = false;
          }
          if(error.status === 400){
           console.log('username ou password incorrect');
                      this.compteDeja = false;
                      this.usermdpincorrect = true;
                      this.entrezQQC = false;
          }
          // Gérer l'erreur ici
        }
      );
      
      // Autres actions de connexion à implémenter ici
    } else {
      console.log('Veuillez saisir un nom d\'utilisateur et un mot de passe valides.');
      this.compteDeja = false;
      this.usermdpincorrect = false;
      this.entrezQQC = true;
      // Vous pouvez également afficher un message d'erreur à l'utilisateur
    }
  }

}
