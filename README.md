# Auction-JEE

## Intitulé du projet

On souhaite développer une application web de vente aux enchères. L’application doit
permettre à des clients de mettre en vente des articles et d'enchérir pour les acheter.

Les fonctionnalités demandées sont les suivantes:
* Gestion des utilisateurs (Connexion et inscription)
* Gestion d'article
* Gestion des enchères
* Affichage de bonnes affaires de manière hebdomadaire
* Gestion de la commande d'articles gagnés
* Gestion du traitement des livraisons

## Mise en place de l'environnement

Pour pouvoir commencer le développement du projet, il faut remplir ces différents points:
* Avoir une base de données Derby nommée : "auction"
* Avoir configuré son serveur d'application Glassfish via l'interface accessible à localhost:4848
  * En ajoutant une ressource dans JDBC, il faut ajouter une ressource nommée jdbc/gla qui utilise le pool de connexions DerbyPool.
  * Il faut ensuite modifier le pool de connexion DerbyPool qui se trouve dans JDBC Connection Pools en ajoutant des propriétés dans l'onglet Additional Properties avec les valeurs suivantes :
  ![Configuration du pool de connexions sur glassfish](https://github.com/Reynault/Auction-JEE/blob/main/doc/configuration_glassfish.png)

## Développeurs

* Maxime Barbier
* Alexis Cesaro
* Yoann Simon
* Reynault Sies

Développement de l'application dans le cadre de l'unité d'enseignement "Génie Logiciel Avancé" de l'année d'étude de Master 2 en Informatique à la faculté des sciences et des technologies à Nancy.

Professeur Encadrant: Horatiu Cirstea
