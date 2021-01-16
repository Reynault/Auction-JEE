# JEE-Auction

## Introduction

Ce dépôt contient la partie Back-End réalisée en utilisant JEE et les EJB.

## Installation

Attention: L'installation de ce projet peut être fastidieuse, 
cependant, si vous suivez parfaitement ce README, le projet fonctionnera !

Pour ce projet, il vous faudra:
- Java 1.8
- Java 11
- Docker  
- Glassfish 5
- NetBeans
- De la patience

Voici les étapes à suivre:

- Lancer le container docker pour avoir une base de données
- Importer le projet sur NetBeans
- La version du projet est Java 11
- Ajouter le Serveur GlassFish 5.0 qui doit utiliser Java 1.8
- Configurer le lancement de GlassFish en précisant le domain 1
- Placer le connecteur JDBC pour mysql qui se trouve dans /lib à la racine
  du projet dans les librairies pour le domaine sur lequel on va servir l'application, c'est
  à dire, ici : Votre installation Glassfish/domains/domain1/lib/ext/
- Créer le pool de connexion de la manière suivante:
![connection pool](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_deployement.PNG)
  Avec les paramètres suivants : 
![connection pool parameters](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_connection_pools_parameters_deployement.PNG)
- Créer la ressource utilisée par l'application de la manière suivante: 
![ressources](https://github.com/Reynault/Auction-JEE/blob/main/doc/images/jee_resources_deployment.PNG)
