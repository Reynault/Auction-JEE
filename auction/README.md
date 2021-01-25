# JEE-Auction

## Introduction

Ce dépôt contient la partie Back-End réalisée en utilisant JEE et les EJB.

## Installation

Attention: L'installation de ce projet peut être fastidieuse, 
cependant, si vous suivez parfaitement ce README, le projet fonctionnera !

Pour installer ce projet, il vous faudra:
- Java 1.8
- Java 11
- Docker  
- Glassfish 5
- NetBeans
- De la patience, beaucoup de patience...

Voici les étapes à suivre:

- Lancer le container docker pour avoir une base de données, il y a un docker-compose à la racine du projet et pour le lancer il vous faudra executer la commande :
```
$ docker-compose up
```
- Importer le projet sur NetBeans
- La version du projet est Java 11
- Ajouter le Serveur GlassFish 5.0 qui doit utiliser Java 1.8
- Configurer le lancement de GlassFish en précisant le domain 1
- Placer le connecteur JDBC pour mysql qui se trouve dans /lib à la racine
  du projet dans les librairies pour le domaine sur lequel on va servir l'application, c'est
  à dire, ici : Votre installation Glassfish/domains/domain1/lib/ext/
- Créer le pool de connexion en lancant glassfish et en vous connectant sur le serveur via l'addresse suivante localhost:4848
- Il faut ensuite créer le pool de la manière suivante:

![connection pool](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_deployement.PNG)

- Première page :

![connection pool](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_first.png)
- Deuxième page :

![connection pool](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_second.png)

- Avec les paramètres suivants : 

![connection pool parameters](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_parameters_deployement.PNG)

- Créer la ressource utilisée par l'application de la manière suivante:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_resources_deployment.PNG)

- Il faut ouvrir le projet persistence qui se trouve à la racine du projet avec netBeans et réaliser un clean & build

- Il faut ensuite lancer le programme principal:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/lancer_dans_intell.PNG)

- Il vous faut également ajouter les ressources liées aux queues JMS utilisées pour gérer les commandes.
- Puis, vous devez ajouter deux queues JMS de cette manière :

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_destination.PNG)

- Puis cliquez sur new

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_destination_new.PNG)

- La première queue s'appelle ValidatedDeliveries

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_destination_validated.PNG)

- La deuxième queue s'appelle PendingDeliveries

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_destination_pending.PNG)

- Vous pouvez enfin lancer le projet principal, il faut clean&build le projet principal (Clique droit sur la racine sur netbeans puis Clean & Build)
- Vous pouvez alors deployer le projet (F6 ou clique droit -> deploy sur la racine du projet)
- Un bug est connu dans lequel le serveur a du mal à se connecter avec la base de données, pour régler ce problème vous pouvez redémarrer le serveur glassfish de cette manière sur netbeans:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_bo.png)

- Les bugs connus sont ceux avec les messages suivants:
  - Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'auction.SEQUENCE' doesn't exist
  - Attempting to execute an operation on a closed EntityManagerFactory.
- Lorsque vous pouvez enfin déployer le projet, vous pouvez passer à la suite
- Il faut ensuite mettre en place le projet JEE-delivery-manager qui va permettre de gérer les commandes, pour ce faire, ouvrez le projet avec NetBeans
- Clean&Build
- Vous pouvez ensuite le deploy
- Normalement, si toutes les étapes précédentes fonctionnent le projet est correctement lancée !
