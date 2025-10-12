package jeu

import item.Utilisable
import joueur
import monstre.*
import monstre.IndividuMonstre

/**
 * Représente un combat entre deux monstres.
 *
 * Un combat entre deux monstres a lieu lorsque le joueur rencontre un monstre sauvage dans une zone.
 *
 * @property monstreJoueur L'indivuMonstre du joueur.
 * @property monstreSauvage un IndividuMonstre de la zone.
 * @property round le nombre de tours.
 */
class CombatMonstre(
    var monstreJoueur: IndividuMonstre,
    var monstreSauvage: IndividuMonstre
) {
    var round: Int = 1

    /**
     * Détermine si le joueur a perdu un combat.
     *
     * @return `true` si le joueur a perdu le combat,
     *         `false` sinon.
     */
    fun gameOver(): Boolean {
        var res = true
        for (monstre in joueur.equipeMonstre) {
            if (monstre.pv > 0) {
                res = false
            }
        }
        return res
    }

    /**
     * Détermine si le joueur a gagné un combat.
     *
     * @return `true` si le joueur a gagné le combat,
     *         `false` sinon.
     */
    fun joueurGagne(): Boolean {
        if (monstreSauvage.pv <= 0) {
            println("${joueur.nom} a gagné !")
            val gainExp = monstreSauvage.exp * 0.20
            monstreJoueur.exp += gainExp
            println("${monstreJoueur.nom} gagne $gainExp exp.")
            return true
        } else {
            if (monstreSauvage.entraineur == joueur) {
                println("${monstreSauvage.nom} a été capturé !")
                return true
            } else {
                return false
            }
        }
    }

    /**
     * Génère une attaque du monstre sauvage si celui-ci est vivant.
     */
    fun actionAdversaire() {
        if (monstreSauvage.pv > 0) {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    /**
     * Affiche un menu proposant au joueur de saisir une action si le combat est actif.
     *
     * Le joueur peut attaquer le monstre sauvage, utiliser un objet pour faciliter sa capture ou changer de monstre si son équipe le lui permet.
     *
     * @return `true` si le combat continue,
     *         `false` si le joueur perd ou capture le monstre.
     */
    fun actionJoueur(): Boolean {
        if (gameOver()) {
            return false
        } else {
            println("Menu : 1 -> Attaquer, 2 -> Utiliser un objet, 3 -> Changer de monstre")
            val choixAction = readln().toInt()

            if (choixAction == 1) {
                monstreJoueur.attaquer(monstreSauvage)

            } else if (choixAction == 2) {
                if (joueur.sacAItems.isEmpty()) {
                    println("Le sac est vide.")
                    return true
                }

                println("Sac :")
                for (i in 0..joueur.sacAItems.lastIndex) {
                    println("$i : ${joueur.sacAItems[i].nom}")
                }
                println("Saisir l'index de l'objet : ")
                val indexChoix = readln().toIntOrNull()

                if (indexChoix == null || indexChoix !in joueur.sacAItems.indices) {
                    println("Choix invalide.")
                } else {
                    val objetChoisi = joueur.sacAItems[indexChoix]
                    if (objetChoisi is Utilisable) {
                        val captureReussi = objetChoisi.utiliser(monstreSauvage)
                        if (captureReussi) {
                            return false
                        }
                    } else {
                        println("Objet non utilisable.")
                    }
                }

            } else if (choixAction == 3) {
                val listeMonstres: MutableList<IndividuMonstre> = mutableListOf()
                for (monstre in joueur.equipeMonstre) {
                    if (monstre.pv > 0 && monstre != monstreJoueur) {
                        listeMonstres.add(monstre)
                    }
                }

                if (listeMonstres.isEmpty()) {
                    println("Aucun autre monstre disponible !")
                } else {
                    println("Équipe disponible :")
                    for (i in 0..listeMonstres.lastIndex) {
                        println("$i : ${listeMonstres[i].nom} (${listeMonstres[i].pv} PV)")
                    }

                    println("Saisir l'index du monstre : ")
                    val indexChoix = readln().toIntOrNull()
                    if (indexChoix == null || indexChoix !in listeMonstres.indices) {
                        println("Choix invalide.")
                    } else {
                        val choixMonstre = listeMonstres[indexChoix]
                        println("${choixMonstre.nom} remplace ${monstreJoueur.nom}.")
                        monstreJoueur = choixMonstre
                    }
                }
            } else {
                println("Choix invalide.")
            }
        }
        return true
    }

    /**
     * Affiche les détails du combat :
     * - niveau et état (pv) du monstre sauvage
     * - art du monstre
     * - niveau du monstre du joueur
     */
    fun afficheCombat() {
        println("============= Début Round : $round =============")
        println("Niveau sauvage : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv}/${monstreSauvage.pvMax}")
        println("${monstreSauvage.espece.afficheArt()}")
        println("${monstreJoueur.espece.afficheArt(false)}")
        println("Niveau joueur : ${monstreJoueur.niveau}")
        println("PV : ${monstreJoueur.pv}/${monstreJoueur.pvMax}")
    }

    /**
     * Génère un combat entre le joueur et le monstre sauvage.
     *
     * Affiche un menu proposant au joueur de saisir une action si le combat est actif et que le joueur est plus rapide.
     *
     * Le joueur peut attaquer le monstre sauvage, utiliser un objet pour faciliter sa capture ou changer de monstre si son équipe le lui permet.
     * Le monstre sauvage attaque le joueur par défaut.
     */
    fun jouer() {
        println("============= Round : $round =============")
        println("${monstreJoueur.nom} (PV : ${monstreJoueur.pv}/${monstreJoueur.pvMax}) VS ${monstreSauvage.nom} (PV : ${monstreSauvage.pv}/${monstreSauvage.pvMax})")

        val joueurPlusRapide = monstreJoueur.vitesse >= monstreSauvage.vitesse

        if (joueurPlusRapide) {
            actionJoueur()
            if (monstreSauvage.pv > 0) {
                actionAdversaire()
            }
        } else {
            actionAdversaire()
            if (monstreJoueur.pv > 0) {
                actionJoueur()
            }
        }

        afficheCombat()
        round += 1
    }

    /**
     * Affiche le résultat du combat.
     */
    fun lancerCombat() {
        while (!gameOver() && !joueurGagne()) {
            jouer()
            println("======== Fin du Round : $round ========")
        }

        if (gameOver()) {
            for (monstre in joueur.equipeMonstre) {
                monstre.pv = monstre.pvMax
            }
            println("Game Over !")
        }
    }
}