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
- Télécharger le connecteur JDBC pour mysql et l'ajouter 
  dans les librairies pour le domaine sur lequel on va servir l'application
- Créer le pool de connexion avec les bons paramètres
- Créer la ressource utilisée par l'application