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


#### F) Package DAO

Le package DAO centralise les interactions avec la base. Elle contient trois classes correspondant chacune à une table (Entraineurs,EspeceMonstre,IndividuMonstre).

- La classe EntraineursDAO gère les opérations CRUD avec la base Entraineurs. Elle contient six méthodes.
- La classe EspeceMonstreDAO intéragit avec la base de données EspeceMonstre
- La classe IndividuMonstreDAO intéragit avec la base IndividuMonstre et gère les relations avec EspecesMonstre et Entraineur.

#### G) Package jdbc

Le package jdbc contient la classe BDD qui gère la connexion à une base de données et l'exécution de requêtes SQL.


---
## III. DIFFICULTEES RENCONTREES

### A) Sprint 1

Les classes du premier sprint ont généré de nombreuses erreurs au début, notamment des erreurs du type Unresolved Reference.
J’ai appris à les corriger en analysant les messages d’erreur et en consultant des forums en ligne.

Exemple d'erreur :
![diagramme ERD](/docs/bug1-unresolvedFileReference.png)

La méthode genereMonstre de la classe Zone (package monde) a été difficile à mettre en classe. j'ai dû essayer différentes stratégies pour m'assurer que le monstre généré a une id unique.


### B) Sprint 2

Les difficultés du sprint 2 concernaient surtout la logique des algorithmes :
j'ai parfois eu du mal à comprendre la logique des diagrammes 


### C) Sprint 3
Capture du diagramme ERD réalisé : 

![diagramme ERD](/docs/captureERD.png)

Le sprint 3 a été plus rapide à implémenter, mais certaines erreurs ont été rencontrées lors de la création du package DAO, notamment à cause de mauvais labels et de mauvaises relations de clés étrangères.

---
### IV. CONCLUSION

Ce projet m’a permis d’approfondir plusieurs aspects de la programmation orientée objet.
J’ai notamment appris à réaliser des tests fonctionnels et unitaires, ce qui m’a aidé à vérifier la fiabilité et le bon fonctionnement du programme au fur et à mesure de son avancement.

J’ai également développé ma capacité à identifier et corriger des bugs, en apprenant à analyser les messages d’erreur, et à rechercher des solutions pertinentes.

Enfin, j’ai découvert comment intégrer et manipuler une base de données à travers la mise en place du package DAO et de la classe BDD. Cette partie m’a permis de mieux comprendre la communication entre une application et une base de données, ainsi que l’importance d’une bonne structure pour les échanges de données.

