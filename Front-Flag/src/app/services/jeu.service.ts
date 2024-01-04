import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JeuService {

  constructor(private http: HttpClient) { }

  getQuestion(): Observable<any> {
    return this.http.get<any>('http://localhost:8081/countries/question');
  }

  getReponse(reponse: any): Observable<any> {
    return this.http.get<any>('http://localhost:8081/countries/reponse?id=' + reponse.id + "&nom=" + reponse.answer + "&username=" + reponse.username + "&score=" + reponse.score);// voir pour envoyer la reponse en path param
  }


}
