# RAPPORT PROJET KOTLINMONSTERS

## I. CONTEXTE

KotlinMonsters est un jeu inspiré de Pokemon, permettant au joueur de capturer des monstres et explorer des zones.
Les fonctionnalités du jeu ont été développé selon un cahier des charges et des instructions transmises par les professeurs du BTS SIO.

Le langage utilisé lors du développement du jeu est Kotlin, l'éditeur de texte utilisé est Intellij.

---
## II. DESCRIPTION

### 1. Fonctionnalités du Jeu

Le joueur démarre une nouvelle partie et sélectionne un Monstre. Il a ensuite la possibilité de chercher d'autres monstres (sauvages) à combattre et capturer en explorant diverses zones.
Le joueur a également accès aux informations des monstres de son équipe.

### 2. Structure du Programme

Le jeu contient 5 packages disposant chacun de une ou plusieurs classes.


#### A) Package Dresseur

Le package dresseur contient une classe Entraineur permettant au joueur de gérer une équipe de monstres et un sac d'objets.
Cette classe est notamment utilisé pour initialiser un objet joueur.

Elle dispose de trois méthodes :
- une méthode affichant les détails de l'entraîneur.
- une méthode permettant de soigner l'équipe de monstres du dresseur.
- une méthode invitant le dresseur à choisir un monstre de son équipe lors d'un combat.


#### B) Package Item

Le package Item contient trois classes et une interface.

- La classe Item permet d'instancier des objets utilisables ou non lors des combats.
- La classe Badge hérite de la classe Item et permet d'initialiser un objet Item faisant office de récompense lorsque le joueur est champion d'une arène.
- La classe MonsterKube hérite de la classe Item et permet d'initialiser un objet utilisable lors d'un combat de monstres pour capturer la cible.

- L'interface Utilisable définit le comportement d'un objet pouvant être utilisé sur un IndividuMonstre.

#### C) Package Jeu

Le package Jeu contient trois classes implémentant la logique du jeu et des combats.

- La classe CombatMonstre génère un combat entre le joueur et un monstre sauvage.
- La classe CombatDresseur est inspiré de la classe CombatMonstre, elle permet notamment de lancer un combat entre deux dresseurs dans une arène.
- La classe Partie génère une partie de jeu et permet au joueur de réaliser des actions dans une zone.

#### D) Package Monde

Le package Monde contient trois classes définissant les différentes zones disponibles dans le jeu (arène, ville etc.).

- La classe Zone est ouverte à l'héritage. Elle permet notamment de générer un monstre dans une zone et un combat avec un monstre sauvage.
- La classe Ville hérite de la classe Zone. Elle peut contenir une arène et intègre des actions spéciales pour le joueur (soigner son équipe, challenger l'arène).
- La classe Arene gère l'intégration d'une arène dans une ville. Elle comprend une méthode permettant au joueur de challenger l'arène.

#### E) Package Monstre

Le package Monstre comporte trois classes intégrant les différentes espèces de monstres dans le jeu et la logique de leurs évolutions.

- La classe EspeceMonstre représente les especes de monstres
- La classe IndividuMonstre représente un monstre, appartenant à une espèce et parfois relié à un dresseur. Cette classe contient les méthodes nécessaires pour la gestion de l'évolution d'un monstre.
- La classe PalierEvolution permet de créer les espèces évoluées et contient une méthode déterminant si le monstre a atteint le niveau requis pour générer son évolution.
---
## III. DIFFICULTEES RENCONTREES

### A) Sprint 1

### B) Sprint 2

### C) Sprint 3

---
### IV. CONCLUSION



