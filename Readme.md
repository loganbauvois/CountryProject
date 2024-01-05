# Compte rendu TP composent

## Backend (12pts)


### Structure (2 pts)

Nous avons deux microservices ms-user qui gère les utilisateurs et ms-countries qui gère les pays.
Ces deux microservices sont lié à la base de donée mongo avec pour l'un user-db et pour l'autre countries-db représentant
repsectivement les bases de données des utilisateurs et des pays. </br>
On a ensuite un front-end nommé front qufais des requêtes aux deux microservices. </br>
Les microservices se font aussi des requêtes entre eux.

### Connexion à la DB (0,5 pt)
    
La base de donnée mongo s'éxecute sur le port 27017, elle est directement lié à nos deux microservices vie les configuration dans les *application.yml*

### Routes (1 pt)
#### GET/GET all   

Il est possible de faire un getAllUsers dans le microservices ms-user les requêtes :
- getAll : http://localhost/Users/
Le get fonctionne différement car il permet de validé une connexion on a donc :
- get : http://localhost/Users/{username}/{password}
qui vérifie si l'username et le password correspondent avec la base de donnée et renvoie ture si c'est bon et false si il y a une erreur
</br>
On a ensuite pour le microservice ms-countries un getQuestion qui renvoie une url d'image et un identifiant puis le getResponse qui verifie si la réponse qu'on a envoyé correspond à l'id de la question:
- getQuestion : http://localhost/countries/question
- getReponse : http://localhost/countries/reponse?id=1&nom=nom&username=username&score=0
getReponse renvera vrai si c'est une bonne réponse et faux sinon, il envoie si la répone est bonne une requête à msUser pour améliorer le score de l'utilisateur si celui-ci dépasse son meilleur score

#### POST (1 pt)

Pour le microservice ms-user on peut créer un utilisateur :
- createUser = http://localhost/Users/ + body
  - body : </br>
    {</br>
        username : username</br>
        password : password</br>
    }</br>

Pour le microservice ms-countries on peut créer un pays avec createCountry, ou avec createAllCountries :
- createCountry : http://localhost/countries/ + body
  - body : </br>
    {</br>
        nom : nom</br>
        url : url</br>
    }</br>
- createAllCountry : http://localhost/countries/all + body
    - body : </br>
    [</br>
        {</br>
            nom : nom</br>
            url : url</br>
        }</br>
        {</br>
            nom : nom</br>
            url : url</br>
        }</br>
        ....</br>
    ]

#### PUT (1,5 pts)     

On peut modifier un utilisateur avec updateUser :
- updateUser : http://localhost/Users/{username} + body
  - body : </br>
    {</br>
    username : new username</br>
    password : new password</br>
    }</br>

#### DELETE (0,5 pts)  

On peut supprimer tous les utilisateurs avec un deleteAll :
- deleteAll : http://localhost/Users


### Swagger UI (2 pts)                                                                                                                           

On a accès au swagger de chaque microservice via les adresses suivantes :
- ms-countries : http://localhost:8081/swagger-ui/index.html
- ms-user : http://localhost:8080/swagger-ui/index.html

### Dockerfile (1 pt)

Chaque microservice ainsi que le projet front Angular sont doté d'un dockerfile permettant pour chcuns d'entre eux de build une image docker.

### docker-compose.yml (1 pt)

Les deux microservices ont un docker-compose.yml permettant de lancer à la fois l'image docker du microservices et de sa BDD.</br>
On a également un docker-compose.yml globale qu'il suffit de lancer pour générer ou télécharger toutes les images et les lancer le tous simplement avec la commande docker-compose up.</br>
Ainsi il ne reste plus qu'à se rendre sur le http://localhost:4200 afin d'afficher le front et d'utiliser l'application complète.

### Github action (1 pt)
### Optimisation (0,5pt)


## Consumer (4pts)

Le consumer est un front réalisé en angular. Il effectue tous types de requêtes sauf les delete. 

### Requêtes

#### GET (1 pts)

La requête get est utilisé pour faire les verification au moment de connecter un utilisateur, mais aussi pour permettre laffichages de plusieurs chose sur le site, comme pour obtenir les images à afficher et pour vérifier si la réponse est exacte.

#### POST (2 pts)

Avec la requête post on peut biensur sur le site s'inscrire et donc créer un utilisateur.

#### PUT 

On peut également une fois qu'on est connecté à notre compte modifié notre nom d'utilisateur et notre mot de passe.

### Dockerfile (1 pt)

Un dockerfil a également été réalisé pour générer une image docker du front. Vous la trouverez à la raçine du projet Front-Flag.
Pour faire ce dockerfile nous avons utilisé des commandes pré-conçus pour la génération d'image docker sur angular et des didactitiels sur internet.

## Demo (4pts)

### docker-compose.yml (1 pt)

Le docker compose à la raçine du projet global où se trouvent les trois projets permet avec simplement la commande </br>
*docker-compose up* de télécharger toutes les images qui ne sont pas déjà télécharger et de générer celles qui ne le sont pas parmis les microservices et le front,</br>
puis de les lancer. Ainsi avec simplement *docker-compose up* on a besoin de rien faire d'autre. Même la base de données des pays a été conçu pour se remplire à la première éxecution de l'image pour pouvoir jouer sans avoir à insérer les pays avant.

### Documentation (1 pt)

C'est ici même.

### Parcours fonctionnel (2 pt)

1) Ouvrir un terminal à CountryProject
2) Éxecuter la commande *docker-compose up*
3) Ouvrir http://localhost:4200/ dans un navigateur
4) S'inscrire avec un username et un password
5) Se connecter avec ces même informations
6) Jouer !
   1) Vous voyez la forme d'un pays
   2) Entrez le nom que vous pensez (Selectionez le ans la liste des propositions pour qu'il soit reconnu)
   3) Validez, si vous avez bon vous gagnez 1 point sinon vous revenez à 0
   4) Votre meilleur score est enregistré
   5) Dans classement (au haut au centre) vous voyez les 10 meilleurs joueurs et leur score
   6) Vous pouvez modifier votre username et password avec le bouton modifier en haut à droite.