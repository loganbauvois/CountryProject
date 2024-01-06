# Compte rendu TP composent

Lien de notre Github : https://github.com/loganbauvois/CountryProject (notamment pour voir le résultat des Github Actions)

## Backend (12pts)

### Structure (2 pts)

Nous avons deux microservices :

- ms-user qui gère les utilisateurs
- ms-countries qui gère les pays.

Ces deux microservices sont liés à la base de données Mongo avec pour l'un user-db et pour l'autre countries-db représentant
repsectivement les bases de données des utilisateurs et des pays.

Il y a un troisième composant front qui sert de consumer pour les back, et il existe une requête du back ms-countries vers ms-user.

### Connexion à la DB (0,5 pt)

La base de donnée mongo s'éxecute sur le port 27017, elle est directement lié à nos deux microservices via les configuration dans les _application.yml_.

Si les micro-services sont lancés depuis un container Docker, et donc si la variable DBHOST est renseignée dans l'environnement, la connexion s'effectue vers mongodb:// ; sinon vers localhost.

**Concernant le remplissage de la base de données**, toutes les images ont été récupérées depuis un repo github : https://github.com/djaiss/mapsicon. Seuls manquent Tuvalu, les îles Marshall, et la Micronésie aux 193 pays reconnus indépendants par l'ONU. Ces images ont été pour chacunes extraites à l'aide d'un script Python, et réupload sur un dépôt github : https://github.com/Taumicron/CountriesShape.

Ainsi, chaque image est accessible depuis une URL au format https://raw.githubusercontent.com/Taumicron/CountriesShape/main/Pays/\[NomPays\].png.

### Routes (1 pt)

#### GET/GET all

Il est possible de faire un getAllUsers dans le microservices ms-user les requêtes :

- getAll : http://localhost/Users

Le get fonctionne différement car il permet de valider une connexion on a donc :

- get : http://localhost/Users/{username}/{password}
  qui vérifie si l'username et le password correspondent avec un enregistrement dans la base de données, et renvoie true si les identifiants sont corrects ; false sinon

Pour le service ms-countries, on a : un getQuestion qui renvoie une url d'image et un identifiant puis le getResponse qui verifie si la réponse qu'on a envoyé correspond à l'id de la question:

- getQuestion : http://localhost/countries/question qui renvoie l'ID et l'URL d'un pays aléatoire
- getReponse : http://localhost/countries/reponse?id=1&nom=nom&username=username&score=0 qui verifie si la réponse retournée est correcte. Si c'est le cas, renvoie true, et ms-countries envoie une requête à ms-user pour actualiser le score s'il s'agit du meilleur score. Renvoie false sinon.

#### POST (1 pt)

Pour le microservice ms-user on peut créer un utilisateur :

- createUser = http://localhost/Users/ + body
  - Le body doit être de la forme :
    ```json
    {
      "username": "username",
      "password": "password"
    }
    ```

Pour le microservice ms-countries on peut ajouter un pays avec createCountry, ou une liste avec createAllCountries :

- createCountry : http://localhost/countries/ + body
  - body :
  ```json
  {
    "nom": "nom",
    "url": "url"
  }
  ```
- createAllCountry : http://localhost/countries/all + body
  - Le body doit être de la forme :
    ```json
    [
        {
            "nom" : "nom1",
            "url" : "url1"
        },
        {
            "nom" : "nom2",
            "url" : "url2"
        },
        ....
    ]
    ```

#### PUT (1,5 pts)

On peut modifier un utilisateur avec updateUser :

- updateUser : http://localhost/Users/{username} + body
  - Body de la forme
  ```json
  {
    "username": "new username",
    "password": "new password"
  }
  ```

#### DELETE (0,5 pts)

On peut supprimer tous les utilisateurs avec un deleteAll :

- deleteAll : http://localhost/Users

### Swagger UI (2 pts)

On a accès au swagger de chaque microservice via les adresses suivantes :

- ms-countries : http://localhost:8081/swagger-ui/index.html
- ms-user : http://localhost:8080/swagger-ui/index.html

Ces adresses sont valides, que le micro-service soit lancé via un container docker, ou en local sur la machine.

### Dockerfile (1 pt)

Chaque microservice ainsi que le projet front Angular contiennent un Dockerfile permettant de build l'image Docker associée.

Ces Dockerfile utilisent le .jar présent dans le dossier target, les 2 micro-services doivent donc avoir été package avec mvn package.

### docker-compose.yml (1 pt)

Les deux microservices ont un docker-compose.yml permettant de lancer à la fois l'image docker du microservice concerné et sa base de données MongoDB.

Ces 2 docker-compose individuels n'ont pas vocation à être lancés en même temps : le premier instanciera la base de données MongoDB, ce qui sera bloquant pour le deuxième.

Le front a également son docker-compose.yml.

On a également un docker-compose.yml global à la racine, qu'il suffit de lancer pour générer ou télécharger toutes les images et les lancer avec une unique commande.

Ainsi il ne reste plus qu'à se rendre sur le http://localhost:4200 afin d'afficher le front et d'utiliser l'application complète.

TODO : Un script .sh permettant de compiler d'abord les micro-services, puis lancer le docker-compose est également présent à la racine.

### Github action (1 pt)

Les Github Action permettent d'assurer une CI/CD.

Elles sont détaillées dans le fichier .github/docker-image.yml, et permettent à tous les tests d'être déclenchés à chaque push ou pullrequest.

La github action se base sur une image d'Ubuntu récente et :

- Copie le repository github dans la VM
- Configure le JDK 17 (minimum requis pour compiler les projets)
- Compile et package ms-user et ms-countries
- Construit les images ms-user, ms-countries et front
- Pull l'image "Registry" pour créer un repository local, et run cette image sur le port 5000
- Retag puis push les images ms-user, ms-countries et front sur le repository port 5000.

Ce CI/CD ne sert à rien dans les faits, puisque le repository local est détruit directement à la fin de l'exécution, mais il permet effectivement de build et push une image comme demandé.

### Optimisation (0,5pt)

Notre code est optimisé :-), notamment par l'utilisation de streams.

## Consumer (4pts)

Le consumer est un front réalisé en angular. Il effectue tous types de requêtes sauf les delete.

### Requêtes

#### GET (1 pts)

Une requête get est effectuée pour vérifier les identifiants de connexion d'un utilisateur lors de la connexion, mais aussi pour permettre l'affichage de plusieurs éléments sur le site, dont l'obtention de l'URL de l'image à afficher pour une question et pour vérifier si la réponse est exacte. Ces communications se font par une requête GET.

#### POST (2 pts)

On peut s'inscrire avec notre nom d'utilisateur et notre mot de passe. Les communications avec le back se font par une requête POST.

#### PUT

On peut également, lorsqu'on est connecté à notre compte, modifier notre nom d'utilisateur et notre mot de passe. Les communications avec le back se font par une requête PUT.

### Dockerfile (1 pt)

Un dockerfile a également été réalisé pour générer une image docker du front. Il se situe à la raçine du projet Front-Flag.

Pour faire ce dockerfile nous avons utilisé des commandes pré-conçues pour la génération d'image Docker sur angular et des didacticiels sur internet.

Les différents docker-compose de ce projet, lorsqu'ils montent l'image du front, le mappent sur le port 4200. Ainsi, il reste accessible à l'adresse http://localhost:4200 sur la machine hôte.

## Demo (4pts)

### docker-compose.yml (1 pt)

Le docker-compose.yml à la racine du projet permet, si on est placé dans le répertoire, avec simplement la commande `docker-compose up`, de télécharger l'image de Mongo (version latest) si elle n'est pas déjà téléchargée, de générer celles des micro-services et du front, s'ils ont bien été package avec Maven, puis de lancer les différentes images dans des containers séparés.

Ainsi avec simplement `docker-compose up` on a besoin de rien faire d'autre. La base de données est normalement vide, mais lorsque le micro-service countries démarre, si la BDD est vide, il la remplit automatiquement en le notifiant dans les logs.

### Documentation (1 pt)

C'est ici même, mais aussi dans les swaggers.

Une collection Postman est également mise à disposition.

### Parcours fonctionnel (2 pt)

1. Ouvrir un terminal dans le répertoire CountryProject
2. Éxecuter la commande `docker-compose up` si les projets Maven des micro-services ont été compilés et packagés. Sinon, il faut exécuter la commande `mvn package` dans les dossiers Back-Flag-User-Service et countries-service.</br>
   Le script runAll.sh permet également de lancer `docker-compose up`, en compilant et packageant au préalable les 2 micro-services.
3. Ouvrir http://localhost:4200/ dans un navigateur (testé sur Chrome, Firefox, Edge et Safari)
4. S'inscrire avec un username et un password
5. Se connecter avec ces même informations
6. Jouer !

   1. Vous voyez la forme d'un pays
   2. Entrez le nom que vous pensez (Sélectionnez-le dans la liste des propositions pour qu'il soit reconnu)
   3. Validez, si vous avez bon vous gagnez 1 point sinon vous revenez à 0
   4. Votre meilleur score est enregistré
   5. Dans classement (en haut au centre) vous voyez les 10 meilleurs joueurs et leur score
   6. Vous pouvez modifier votre username et password avec le bouton modifier en haut à droite.

      _Si vous voulez tricher, l'URL contenant notamment le nom du pays s'affiche dans la console JavaScript, et il est possible de clic droit/copier l'adresse de l'image._
