# Bati-Cuisine: Application d'Estimation des Coûts de Rénovation de Cuisines

## Description
Bati-Cuisine est une application Java qui permet d'estimer les coûts liés à la construction et à la rénovation de cuisines. Elle permet aux professionnels de gérer les clients, les composants (matériaux, main-d'œuvre), de générer des devis, et d'obtenir une vue d'ensemble sur les coûts avec TVA et marge bénéficiaire.

## Fonctionnalités principales
- Gestion des projets de rénovation/construction.
- Gestion des composants (matériaux, main-d'œuvre).
- Gestion des clients professionnels et particuliers.
- Génération de devis avec détails sur les coûts et la TVA.
- Calcul des coûts totaux incluant une marge bénéficiaire et les taxes applicables.

## Exigences Techniques
- **Java**: Version 17 ou supérieure
- **PostgreSQL**: Base de données pour stocker les informations liées aux projets, clients, matériaux, etc.
- **Maven**: Pour la gestion des dépendances et la compilation du projet.
- **JAR**: Le projet peut être compilé en un fichier `.jar` exécutable.
- **Git et JIRA**: Utilisés pour la gestion du code source et la planification des tâches.
- **Design Patterns**: Singleton, Repository
- **Technologies**: Java Streams, Collections, HashMap, Optional, Enum, Java Time API.

## Installation

### Prérequis
- Java 17+
- Maven 3.6+
- PostgreSQL (Une instance locale ou distante avec les configurations du fichier `application.properties`)

### Étapes

1. Clonez le dépôt Git:
    ```bash
    git clone https://github.com/Kamal-AbdElKerim/Bati-Cuisine
    cd bati-cuisine
    ```

2. Configurez la base de données PostgreSQL:
   - Créez une base de données nommée `bati_cuisine`:
     ```sql
     CREATE DATABASE bati_cuisine;
     ```
   - Modifiez le fichier `src/main/resources/application.properties` pour inclure vos informations de connexion PostgreSQL:
     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/bati_cuisine
     spring.datasource.username=yourUsername
     spring.datasource.password=yourPassword
     ```

3. Compilez et packagez le projet en fichier `.jar`:
    ```bash
    mvn clean package
    ```

4. Exécutez le fichier `.jar` généré:
    ```bash
    java -jar target/bati-cuisine-1.0.jar
    ```

## Utilisation
1. Lors du lancement de l'application, vous serez accueilli par un menu principal:
    ```
    === Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===
    1. Créer un nouveau projet
    2. Afficher les projets existants
    3. Calculer le coût d'un projet
    4. Quitter
    ```

2. Suivez les étapes interactives pour ajouter des projets, des clients, des matériaux, et de la main-d'œuvre.

3. Génération de devis: Le programme permet de générer des devis pour chaque projet en incluant le détail des coûts et la TVA.

## Arborescence du Projet
