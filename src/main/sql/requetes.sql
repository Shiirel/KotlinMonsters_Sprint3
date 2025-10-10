CREATE TABLE Entraineurs(
                            id INTEGER PRIMARY KEY AUTO_INCREMENT,
                            nom VARCHAR(255),
                            argents INTEGER);

CREATE TABLE EspeceMonstres (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                nom VARCHAR(255),
                                type VARCHAR(255),
                                baseAttaque INTEGER,
                                baseDefense INTEGER,
                                baseVitesse INTEGER,
                                baseAttaqueSpe INTEGER,
                                baseDefenseSpe INTEGER,
                                basePv INTEGER,
                                modPv DOUBLE,
                                modAttaque DOUBLE,
                                modDefense DOUBLE,
                                modVitesse DOUBLE,
                                modAttaqueSpe DOUBLE,
                                modDefenseSpe DOUBLE,
                                description TEXT,
                                particularites TEXT,
                                caracteres TEXT
);

CREATE TABLE IndividuMonstre(
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                nom VARCHAR(255),
                                niveau INTEGER,
                                attaque INTEGER,
                                defense INTEGER,
                                vitesse INTEGER,
                                attaqueSpe INTEGER,
                                defenseSpe INTEGER,
                                pvMax INTEGER,
                                potentiel DOUBLE,
                                exp DOUBLE,
                                pv INTEGER,
                                espece_id INT DEFAULT NULL REFERENCES EspeceMonstres (id),
                                entraineur_equipe_id INT DEFAULT NULL REFERENCES Entraineurs (id),
                                entraineur_boite_id INT DEFAULT NULL REFERENCES Entraineurs (id)
);

CREATE TABLE Zone(
                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                     nom VARCHAR(255),
                     expZone DECIMAL);


CREATE TABLE Zone_EspeceMonstre(
                                   espece_id INT DEFAULT NULL REFERENCES EspeceMonstres (id),
                                   zone_id INT DEFAULT NULL REFERENCES Zone (id),
                                   PRIMARY KEY (espece_id,zone_id));




/*INSERTS*/
INSERT INTO EspeceMonstres
(id, nom, type, baseAttaque, baseDefense, baseVitesse, baseAttaqueSpe, baseDefenseSpe, basePv,
 modAttaque, modDefense, modVitesse, modAttaqueSpe, modDefenseSpe, modPv,
 description, particularites, caracteres)
VALUES
    (1, 'springleaf', 'Graine', 9, 11, 10, 12, 14, 60,
     6.5, 9.0, 8.0, 7.0, 10.0, 14.0,
     'Un petit monstre espiègle au corps rond comme une graine. Il aime se cacher dans l’herbe haute et se dorer au soleil.',
     'Sa feuille sur la tête s’incline pour indiquer son humeur.',
     'Curieux, amical, un peu timide.'),

    (4, 'flamkip', 'Animal', 12, 8, 13, 16, 7, 50,
     10.0, 5.5, 9.5, 9.5, 6.5, 12.0,
     'Ce petit animal est toujours entouré d’une flamme dansante. Il déteste le froid et s’énerve facilement quand on tente d’éteindre son feu.',
     'Sa flamme change d’intensité selon son niveau d’énergie.',
     'Impulsif, joueur, loyal.'),

    (5, 'pyrokip', 'Animal', 18, 12, 15, 22, 11, 70,
     12.0, 8.0, 11.0, 12.5, 8.0, 15.0,
     'Pyrokip, l’évolution de Flamkip. Son feu est devenu intense et ses flammes sont capables de fondre la pierre. Fier et courageux, il protège son dresseur à tout prix.',
     'Ses flammes changent de couleur selon son humeur : rouge vif en colère, dorées quand il est calme.',
     'Fier, protecteur, explosif.'),

    (7, 'aquamy', 'Meteo', 10, 11, 9, 14, 14, 55,
     9.0, 10.0, 7.5, 12.0, 12.0, 13.5,
     'Une créature vaporeuse qui ressemble à un petit nuage. Les gouttes qui tombent de son corps sont pures et rafraîchissantes.',
     'Fait légèrement baisser la température autour de lui quand il s’endort.',
     'Calme, rêveur, mystérieux.'),

    (8, 'laoumi', 'Animal', 11, 10, 9, 8, 11, 58,
     11.0, 8.0, 7.0, 6.0, 11.5, 14.0,
     'Un petit ourson au pelage soyeux. Il adore se tenir debout et brandir ses petites pattes comme s’il dansait.',
     'Son grognement est plus mignon qu’effrayant, mais il devient redoutable pour défendre ses amis.',
     'Affectueux, protecteur, gourmand.'),

    (10, 'bugsyface', 'Insecte', 10, 13, 8, 7, 13, 45,
     7.0, 11.0, 6.5, 8.0, 11.5, 10.0,
     'Un insecte à la carapace luisante qui se déplace par petits bonds. Il communique en faisant vibrer ses antennes.',
     'Sa carapace devient plus dure après chaque mue, augmentant sa défense.',
     'Travailleur, sociable, infatigable.'),

    (13, 'galum', 'Mineral', 12, 15, 6, 8, 12, 55,
     9.0, 13.0, 4.0, 6.5, 10.5, 13.0,
     'Un golem ancien sculpté dans la pierre. Ses yeux s’illuminent d’une lueur mystérieuse quand il se met en garde.',
     'Peut rester immobile pendant des heures, le faisant passer pour une statue.',
     'Sérieux, stoïque, fiable.');

INSERT INTO Entraineurs
(id,nom,argents)
VALUES
    (1,'Alice',250),
    (2,'Bob',500),
    (3,'Clara',100);


INSERT INTO IndividuMonstre
(id,nom,niveau,attaque,defense,vitesse,attaqueSpe,defenseSpe,pvMax,potentiel,exp,pv,espece_id,entraineur_equipe_id,entraineur_boite_id)
VALUES
    (1,'kenette',2,15,10,14,15,12,40,22.0,15,10,1,1,1),
    (2,'antonin',1,20,15,6,12,10,36,30.0,15,14,7,2,2),
    (3,'joie',5,20,15,6,12,10,36,30.0,15,14,10,2,2),
    (4,'monk',4,12,25,10,45,12,30,22.0,4,10,13,3,3),
    (5,'monk',4,12,25,10,45,12,30,22.0,4,10,4,3,3);


