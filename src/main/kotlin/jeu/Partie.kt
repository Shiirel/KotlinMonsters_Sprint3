package jeu

import dresseur.Entraineur
import especeAquamy
import especeFlamkip
import especeSpringleaf
import monde.Arene
import monde.Ville
import monde.Zone
import monstre.EspeceMonstre
import monstre.IndividuMonstre

/**
 * Représente le lancement d'une partie de jeu.
 *
 * Un combat entre deux monstres a lieu lorsque le joueur rencontre un monstre sauvage dans une zone.
 *
 * @property id L'identifiant de la partie.
 * @property joueur un objet Entraineur représentant le joueur.
 * @property zone un objet Zone désignant la zone où se trouve le joueur.
 */
class Partie (
    val id : Int,
    var joueur : Entraineur,
    var zone : Zone,
){

    /**
     * Invite le joueur à choisir un monstre de départ et affiche le détail de chaque monstre.
     *
     * Le joueur peut renommer le monstre.
     * Le monstre est ajouté dans l'équipe du joueur.
     */
    fun choixStarter() {
        val m1 = IndividuMonstre(4, "springleaf",especeSpringleaf,null,1500.0)
        val m2 = IndividuMonstre(5, "flamkip", especeFlamkip,null,1500.0)
        val m3 = IndividuMonstre(6, "aquamy", especeAquamy,null,1500.0)

        m1.afficheDetail()
        m2.afficheDetail()
        m3.afficheDetail()

        println("Choisir un monstre (1 -> springleaf, 2 -> flamkip, ou 3 -> aquamy) : ")
        val choix = readln()
        var starter = m1
        if (choix == "1") {
            starter = m1
        } else if (choix == "2") {
            starter = m2
        } else if (choix == "3") {
            starter  = m3
        } else {
            print("Erreur lors de la saisie du choix.")
        }
        starter.renommer()
        joueur.equipeMonstre.add(starter)
        starter.entraineur = joueur
    }



    /**
     * Modifie l'ordre de l'équipe de monstres du joueur si il y a plusieurs monstres dans celle-ci.
     */
    fun modifierOrdreEquipe() {
        if (joueur.equipeMonstre.size>=2) {
            println("Équipe :")
            for (i in 0..joueur.equipeMonstre.lastIndex) {
                println("$i : ${joueur.equipeMonstre[i].nom}")
            }

            println("Saisir la position du premier monstre :")
            val position1 = readln().toInt()

            println("Saisir la position du second monstre :")
            val position2 = readln().toInt()

            val temp = joueur.equipeMonstre[position1]
            joueur.equipeMonstre[position1] = joueur.equipeMonstre[position2]
            joueur . equipeMonstre [position2] = temp
        } else {
            println("Erreur : il n'y a pas assez de monstres dans l'équipe.")
        }
    }



    /**
     * Affiche les détails du monstre séléctionné par le joueur lorsqu'il souhaite examiner son équipe.
     */
    fun examineEquipe() {
        println("Équipe :")
        for (i in 0..joueur.equipeMonstre.lastIndex) {
            println("$i : ${joueur.equipeMonstre[i].nom}")
        }
        println("Saisir la position du monstre pour voir les détails, 'q' pour quitter, 'm' pour modifier l'ordre")
        val position = readln().lowercase()
        if(position!="q" && position!="m") {
            var pos = position.toInt()
            joueur.equipeMonstre[pos].afficheDetail()
        } else if(position=="m") {
            modifierOrdreEquipe()
        } else if (position == "q") {
            print("test")
        }
    }





    /**
     * Affiche un menu permettant au joueur de choisir une action parmis les actions disponibles dans la zone.
     */
    fun jouer() {
        println("Vous êtes dans la zone ${zone.nom}")

        println("Actions possibles :")
        println("1 => Rencontrer un monstre sauvage")
        println("2 => Examiner l'équipe de monstres")
        println("3 => Aller à la zone suivante")
        println("4 => Aller à la zone précédente")
        println("5 => Soigner votre équipe")
        println("6 => Challenger l'arène")

        when (readln()) {
            "1" -> {
                zone.rencontreMonstre()
                jouer()
            }

            "2" -> {
                examineEquipe()
                jouer()
            }

            "3" -> {
                if (zone.zoneSuivante != null) {
                    zone = zone.zoneSuivante!!
                    jouer()
                } else {
                    println("Pas de zone suivante.")
                    jouer()
                }
            }

            "4" -> {
                if (zone.zonePrecedente != null) {
                    zone = zone.zonePrecedente!!
                    jouer()
                } else {
                    println("Pas de zone précédente.")
                    jouer()
                }
            }

            "5" -> {
                if (zone is Ville) {
                    joueur.soigneEquipe()
                    println("L'hôpital soigne votre équipe.")
                } else {
                    println("Déplacez-vous dans une ville pour soigner l'équipe.")
                }
                jouer()
            }

            "6" -> {
                if (zone is Ville) {
                    val ville = zone as Ville
                    if (ville.arene != null) {
                        ville.arene!!.challenger() // méthode sans paramètre
                    } else {
                        println("Cette ville n'a pas d'arène.")
                    }
                } else {
                    println("Déplacez-vous dans une ville pour challenger une arène.")
                }
                jouer()
            }
        }
    }
}