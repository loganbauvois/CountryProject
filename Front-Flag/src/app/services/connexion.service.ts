import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ConnexionService {

  estConnecte: boolean = false;
  username: string = '';
  constructor(private http: HttpClient) { }

  connecter(username: any, password: any): Observable<any>{
    // Votre logique de connexion réussie ici

    return this.http.get<any>('http://localhost:8080/Users/' + username + '/' + password);
    
  }
    getScore(){
        return this.http.get<any>('http://localhost:8080/Users/score?username=' + this.username );
    }
  

  deconnecter() {
    // Votre logique de déconnexion ici
    this.estConnecte = false;
  }

  createUser(user: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/Users', user);
  }

  // Ajouter un utilisateur de base dans la fonction connecté et ici mettre la modification via une popup où on peut choisir un nouveau nom et mot de passe
  updateUser(user: any, current: any): Observable<any> {
    console.log(current);
    return this.http.put<any>('http://localhost:8080/Users/' + current, user);
  }

  getClassement(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Users/classement');
  }

}
