import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnexionComponent } from './component/connexion/connexion.component';
import { JeuComponent } from './component/jeu/jeu.component';
import { provideRouter } from '@angular/router';
import { ClassementComponent } from './component/classement/classement.component';
const routes: Routes = [
  { path: '', component: ConnexionComponent },
  { path: 'jeu', component: JeuComponent },
  { path: 'rank', component: ClassementComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [provideRouter(routes)]
})
export class AppRoutingModule { }
