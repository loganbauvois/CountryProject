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

### Swagger UI (2 pts)                                                                                                                           

### Dockerfile (1 pt)

### docker-compose.yml (1 pt)

### Github action (1 pt)
### Optimisation (0,5pt)


## Consumer (4pts)


### Requêtes
#### GET (1 pts)
#### POST (2 pts)

### Dockerfile (1 pt)


## Demo (4pts)

### docker-compose.yml (1 pt)
### Documentation (1 pt)
### Parcours fonctionnel (2 pt)
