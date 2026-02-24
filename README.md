# 🎬 FilmSerieTrackerFX

Application bureau de gestion de films et séries TV développée avec
**JavaFX** et connectée à une **API REST Spring Boot** utilisant une
base de données **PostgreSQL hébergée sur Neon Cloud**.

------------------------------------------------------------------------

## 📌 Description

Cette application permet aux utilisateurs de :

-   Ajouter des films et des séries
-   Modifier ou supprimer un contenu
-   Suivre la progression des séries (épisodes vus)
-   Attribuer une note personnelle
-   Gérer une watchlist
-   Consulter des statistiques (films, séries, watchlist)

------------------------------------------------------------------------

## 🏗️ Architecture du Projet

Le projet suit une architecture **MVC (Modèle - Vue - Contrôleur)** :

JavaFX (FXML)\
↓\
Controller\
↓\
ApiService\
↓\
ApiClient (HttpClient)\
↓\
API REST (Spring Boot)\
↓\
Repository (JPA)\
↓\
Neon PostgreSQL

------------------------------------------------------------------------

## 📂 Structure du Repository

FilmSerieTrackerProject\
│\
├── filmserie-api → Backend Spring Boot (API REST)\
│\
└── FilmSerieTrackerFX → Frontend JavaFX (Application Bureau)

------------------------------------------------------------------------

## ⚙️ Technologies Utilisées

-   Java JDK 21+
-   JavaFX 21+
-   Spring Boot 3+
-   PostgreSQL (Neon Cloud)
-   Maven
-   Gson
-   HttpClient

------------------------------------------------------------------------

## 🚀 Lancer le Backend (API REST)

Dans le terminal :

cd filmserie-api\
mvn spring-boot:run

L'API sera disponible sur :\
http://localhost:8080

------------------------------------------------------------------------

## 🚀 Lancer le Frontend (JavaFX)

Dans le terminal :

cd FilmSerieTrackerFX\
mvn javafx:run

------------------------------------------------------------------------

## 📊 Base de Données

La base de données est hébergée sur **Neon PostgreSQL** et contient les
tables suivantes :

-   genres
-   contenus
-   progression_series

------------------------------------------------------------------------

## 🧠 Fonctionnalité de Progression

Pour les séries, l'utilisateur peut suivre :

-   Nombre d'épisodes vus
-   Nombre total d'épisodes

La progression est enregistrée dans la table :

progression_series

------------------------------------------------------------------------

## 📡 API REST

  Méthode   Endpoint             Description
  --------- -------------------- ---------------------
  GET       /api/contenus        Liste des contenus
  POST      /api/contenus        Ajouter contenu
  PUT       /api/contenus/{id}   Modifier contenu
  DELETE    /api/contenus/{id}   Supprimer contenu
  POST      /api/progression     Ajouter progression

------------------------------------------------------------------------

## 👨‍💻 Auteur

Projet réalisé dans le cadre du cours\
Application Bureau -- Session Automne 2025\
Collège de Maisonneuve
