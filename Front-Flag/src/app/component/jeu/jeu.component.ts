import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from 'src/app/services/connexion.service';
import { JeuService } from 'src/app/services/jeu.service';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';
import { ClassementComponent } from '../classement/classement.component';

@Component({
  selector: 'app-jeu',
  templateUrl: './jeu.component.html',
  styleUrls: ['./jeu.component.css']
})
export class JeuComponent {

  constructor(private jeuService: JeuService, private connexionService: ConnexionService, private router: Router, private cdr: ChangeDetectorRef) { }
  username: string = '';
  password: string = '';
  imageUrl: string | undefined = "";
  id: string = '';
  answer: string = '';
  countryFormControl = new FormControl();
  score: number = 0;
  scoreBest: number = 0;
  proposition: boolean = false;
  countries: string[] = [
    'Émirats arabes unis', 'Afghanistan', 'Antigua-et-Barbuda', 'Albanie', 'Arménie', 'Angola', 'Argentine', 'Autriche', 'Australie', 'Azerbaïdjan', 'Bosnie-Herzégovine', 'Barbade', 'Bangladesh', 'Belgique', 'Burkina Faso', 'Bulgarie', 'Bahreïn', 'Burundi', 'Bénin', 'Brunéi', 'Bolivie', 'Brésil', 'Bahamas', 'Bhoutan', 'Botswana', 'Biélorussie', 'Belize', 'Canada', 'République démocratique du Congo', 'République centrafricaine', 'République du Congo', 'Suisse', 'Côte d\'Ivoire', 'Chili', 'Cameroun', 'Chine', 'Colombie', 'Costa Rica', 'Cuba', 'Cap-Vert', 'Chypre', 'Tchéquie', 'Allemagne', 'Djibouti', 'Danemark', 'Dominique', 'République dominicaine', 'Algérie', 'Équateur', 'Estonie', 'Égypte', 'Érythrée', 'Espagne', 'Éthiopie', 'Finlande', 'Fidji', 'France', 'Gabon', 'Royaume-Uni', 'Grenade', 'Géorgie', 'Ghana', 'Gambie', 'Guinée', 'Guinée équatoriale', 'Grèce', 'Guatemala', 'Guinée-Bissau', 'Guyana', 'Honduras', 'Croatie', 'Haïti', 'Hongrie', 'Indonésie', 'Irlande', 'Israël', 'Inde', 'Irak', 'Iran', 'Islande', 'Italie', 'Jamaïque', 'Jordanie', 'Japon', 'Kenya', 'Kirghizistan', 'Cambodge', 'Kiribati', 'Comores', 'Saint-Christophe-et-Niévès', 'Corée du Nord', 'Corée du Sud', 'Koweït', 'Kazakhstan', 'Laos', 'Liban', 'Sainte-Lucie', 'Liechtenstein', 'Sri Lanka', 'Libéria', 'Lesotho', 'Lituanie', 'Luxembourg', 'Lettonie', 'Libye', 'Maroc', 'Monaco', 'Moldavie', 'Monténégro', 'Madagascar', 'Macédoine du Nord', 'Mali', 'Myanmar', 'Mongolie', 'Mauritanie', 'Malte', 'Maurice', 'Maldives', 'Malawi', 'Mexique', 'Malaisie', 'Mozambique', 'Namibie', 'Niger', 'Nigéria', 'Nicaragua', 'Pays-Bas', 'Norvège', 'Népal', 'Nauru', 'Nouvelle-Zélande', 'Oman', 'Panama', 'Pérou', 'Papouasie-Nouvelle-Guinée', 'Philippines', 'Pakistan', 'Pologne', 'Portugal', 'Palaos', 'Paraguay', 'Qatar', 'Roumanie', 'Serbie', 'Russie', 'Rwanda', 'Arabie saoudite', 'Îles Salomon', 'Seychelles', 'Soudan', 'Suède', 'Singapour', 'Slovénie', 'Slovaquie', 'Sierra Leone', 'Saint-Marin', 'Sénégal', 'Somalie', 'Suriname', 'Soudan du Sud', 'Sao Tomé-et-Principe', 'Salvador', 'Syrie', 'Eswatini', 'Tchad', 'Togo', 'Thaïlande', 'Tadjikistan', 'Timor-Leste', 'Turkménistan', 'Tunisie', 'Tonga', 'Turquie', 'Trinité-et-Tobago', 'Tanzanie', 'Ukraine', 'Ouganda', 'États-Unis d\'Amérique', 'Uruguay', 'Ouzbékistan', 'Saint-Vincent-et-les-Grenadines', 'Venezuela', 'Vietnam', 'Vanuatu', 'Samoa', 'Yémen', 'Afrique du Sud', 'Zambie', 'Zimbabwe'
  ]; 
  filteredCountries: string[] = [];
  onSubmit(){
    
    if (this.answer.trim() !== '' ) {

      console.log('Envoie de la reponse');
      console.log(this.answer);
      const rep = {
        answer : this.answer,
        id : this.id,
        username : this.connexionService.username,
        score : this.score
      }
      this.jeuService.getReponse(rep).subscribe(
        (response) =>{
          if(response == true){
            this.score++;
            this.answer = '';
            this.connexionService.getScore().subscribe(
                    (response) => {
                        this.scoreBest = response;
                    },
                    (error) => {
                    console.error(" - ",error)
                    }
                  );
            this.jeuService.getQuestion().subscribe(
              (response) => {
                console.log('Réponse du serveur : ', response);
                this.imageUrl = response.url;
                this.id = response.id;
              },
              (error) => {
                console.error('Erreur lors de la requête : ', error);
              }
            );
            // Le back envoie une requête pour augmenter le score et on demande une nouvelle question
          }
          else{
            // on envoie une requête si c'est le meilleur score on le met à la place de l'ancien sinon rien
            this.score = 0;
            this.answer = '';
            this.jeuService.getQuestion().subscribe(
              (response) => {
                console.log('Réponse du serveur : ', response);
                this.imageUrl = response.url;
                this.id = response.id;
              },
              (error) => {
                console.error('Erreur lors de la requête : ', error);
              }
            );
          }
        },
        (error) => {
          console.error('Erreur lors de la requête 4: ', error);
        }
      )
    }
    else{
      console.log('Entrez une réponse valide');
    }
  }

 
  onKeyUp(event: any) {
    this.filteredCountries = this.countries.filter(country =>
      country.toLowerCase().startsWith(event.target.value.toLowerCase())
    );
    if (event.target.value.trim() && this.filteredCountries.length > 0) {
      this.proposition = true;
    } else {
      this.proposition = false;
    }

  }

  choisirMot(mot: string) {
    console.log(mot);
    this.answer = mot;
    this.filteredCountries = [];
    this.proposition = false;
    this.cdr.detectChanges(); 
  }

  ngOnInit(): void {
    
    if (this.connexionService.estConnecte){
      this.connexionService.getScore().subscribe(
        (response) => {
            this.scoreBest = response;
        },
        (error) => {
            console.error(" - ",error);
        }
      )
      this.jeuService.getQuestion().subscribe(
        (response) => {
          console.log('Réponse du serveur : ', response);
          this.imageUrl = response.url;
          this.id = response.id;
        },
        (error) => {
          console.error('Erreur lors de la requête : ', error);
        }
      );
    }else{
      this.router.navigate(['/']);
    }
    
  }

}
