# Back Spring du projet Auction pour le module de GLA

### Pré-requis
- Docker
- Java 11
- Maven

## Mise en place
### Récupération du repository Git
Si ce n'est pas déjà le cas, effectuer la commande suivante : 
```
git clone https://github.com/Reynault/Auction-JEE
```
Depuis le terminal à l'endroit où vous souhaitez l'avoir.

--------------------------

### Compilation du module persistence-auction
Avant tout, il faut compiler le projet persistence-auction, pour cela depuis le terminal :
```
cd Auction-JEE/persistence-auction
``` 
Le compiler avec la commande : 
```
mvn compile
```
L'installer avec : 
```
mvn install
```

--------------------------

### Lancement du conteneur Docker
La base de données SQL ainsi que RabbitMQ pour la communication asynchrone sont à lancer dans un conteneur docker, pour cela ouvrir un terminal dans le
dossier Auction-JEE et faire la commande :
```
docker-compose up
```
Cela lance les deux services dans le conteneur, laissez le terminal ouvert (pour l'arrêter Ctrl+C devrait rendre la main dans le terminal, faite ensuite `docker-compose down` 
pour arrêter et détruire complètement le conteneur).

--------------------------

### Lancement du back Spring
Une fois que RabbitMQ et la base de données SQL lancés, il ne reste plus qu'à lancer le projet Spring: pour cela ouvrir un autre terminal dans le dossier spring-auction puis 
faire la commande :
```
mvn spring-boot:run
```
Lorsque le message *Initialisation OK!* s'affiche, il est prêt à être utilisé avec Postman ou bien avec le front que vous trouverez à l'addresse 
https://github.com/Reynault/Auction-JEE/tree/main/front-auction

Il est également possible de le lancer sous IntelliJ, en ouvrant le dossier spring-auction et une fois ouvert, faire la commande `Run` en haut à droite.

--------------------------

REMARQUE : Si vous choisissez de lancer le back JEE, il faut supprimer le conteneur et relancer le docker-compose up (les jeux de données à l'initialisation des backs sont différents)
