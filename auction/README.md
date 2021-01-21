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
- Intell Ij
- NetBeans
- De la patience

Voici les étapes à suivre:

- Lancer le container docker pour avoir une base de données, il y a un docker-compose à la racine du projet et pour le lancer il vous faudra executer la commande :
```
$ docker-compose up
```
- Ce docker-compose va également mettre en place Rabbit MQ qui est le message broker utilisé dans le projet pour gérer les messages asynchrones
- Importer le projet sur NetBeans
- La version du projet est Java 11
- Ajouter le Serveur GlassFish 5.0 qui doit utiliser Java 1.8
- Configurer le lancement de GlassFish en précisant le domain 1
- Placer le connecteur JDBC pour mysql qui se trouve dans /lib à la racine
  du projet dans les librairies pour le domaine sur lequel on va servir l'application, c'est
  à dire, ici : Votre installation Glassfish/domains/domain1/lib/ext/
- Créer le pool de connexion de la manière suivante:

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
- Il faut également ouvrir le projet Delivery Manager avec Intell IJ, ce projet est sous Spring Boot et doit être lancé avant le projet principal pour pouvoir gérer les demandes de livraison. Pour ce faire, ouvez le projet comme ceci:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/lancer_delivery.PNG)


- Il faut ensuite lancer le programme principal:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/lancer_dans_intell.PNG)

- Vous pouvez enfin lancer le projet principal, il faut clean&build le projet principal (Clique droit sur la racine sur netbeans puis Clean & Build)
- Vous pouvez alors deployer le projet (F6 ou clique droit -> deploy sur la racine du projet)
- Un bug est connu dans lequel le serveur a du mal à se connecter avec la base de données, pour régler ce problème vous pouvez redémarrer le serveur glassfish de cette manière sur netbeans:

![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_bo.png)
