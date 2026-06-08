#  Gestion d'une Auto-École

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)

##  Description
Application Java de gestion d'une auto-école développée dans le cadre 
d'un Mini-Projet académique à la Faculté des Sciences de Bizerte, 
Université de Carthage (CPI2 - 2025).

L'application offre une interface graphique complète (JavaFX) permettant 
la gestion des candidats, moniteurs, véhicules, séances et finances.

##  Fonctionnalités
-  Paramétrage des informations de l'auto-école
-  Gestion des candidats (avec documents scannés)
-  Gestion des paiements et facilités
-  Inscription aux examens officiels de conduite et de code
-  Gestion des moniteurs
-  Gestion des séances de code et de conduite
-  Localisation des RDV sur Map
-  Gestion des véhicules et suivi des échéances
-  Tableau de bord financier

##  Technologies utilisées
- Java 8 + JavaFX (interface graphique)
- MySQL (base de données)
- Maven (gestion des dépendances)
- Architecture en couches (MVC)
- Diagrammes UML (Use-case, Classes, Séquence)

##  Structure du projet
```
src/main/java/
├── Auto_Ecolee/    # Point d'entrée et configuration
├── Controleur/     # Coordination entre couches
├── Dao/            # Accès base de données MySQL
├── Entities/       # Classes métier
└── Service/        # Logique métier

src/main/resources/
├── Auto_Ecolee/    # Fichiers FXML (interfaces)
├── icons/          # Icônes de l'application
└── pics/           # Images de l'application
```

##  Prérequis
- Java 8 ou supérieur
- MySQL Server
- Maven

##  Comment lancer le projet
1. Cloner le repository
```bash
git clone https://github.com/hibatallah-hadjkacem/gestion-auto-ecole
```
2. Créer la base de données MySQL
```sql
CREATE DATABASE auto_ecole;
```
3. Importer le fichier SQL fourni dans le dossier `/database`
4. Configurer les paramètres de connexion dans `Dao/`
5. Lancer avec Maven :
```bash
mvn javafx:run
```
ou ouvrir avec Eclipse et lancer `Main.java`

##  Diagrammes UML
- Diagramme Use-case
- Diagramme de Classes
- Diagramme de Séquence Système
- Diagramme de Séquence Objets

##  Réalisé par
- Hibat Allah Hadj Kacem
- Amal Ktiti
- Nermin Kouki

##  Contexte académique
- **Établissement** : Faculté des Sciences de Bizerte, Université de Carthage
- **Classe** : CPI2
- **Matière** : Java 2
- **Année** : 2025
