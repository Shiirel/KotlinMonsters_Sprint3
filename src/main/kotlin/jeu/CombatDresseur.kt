package jeu

import dresseur.Entraineur
import monstre.IndividuMonstre

/**
 * Représente un combat entre deux dresseurs.
 *
 * Un entraîneur est responsable de gérer une équipe de monstres, une boîte pour stocker des monstres supplémentaires
 * et un sac contenant des objets appelés MonsterKubes. L'entraîneur a également une somme d'argent associée.
 *
 * @property id L'identifiant unique du combat.
 * @property joueur L'objet Entraineur faisant référence au joueur.
 * @property entraineurAdversaire L'objet Entraineur faisant référence au dresseur adversaire.
 */
class CombatDresseur(
    var id: Int,
    var joueur: Entraineur,
    var entraineurAdversaire: Entraineur
) {

    /**
     * Détermine si le joueur a gagné un combat.
     *
     * @return `true` si le joueur a gagné le combat,
     *         `false` sinon.
     */
    fun avoirGagne(): Boolean {
        for (monstre in entraineurAdversaire.equipeMonstre) {
            if (monstre.pv > 0) {
                return false
            }
        }
        return true
    }

    /**
     * Détermine si le joueur a perdu un combat.
     *
     * @return `true` si le joueur a perdu le combat,
     *         `false` sinon.
     */
    fun avoirPerdu(): Boolean {
        for (monstre in joueur.equipeMonstre) {
            if (monstre.pv > 0) {
                return false
            }
        }
        return true
    }

    /**
     * Lance un combat entre le joueur et le dresseur adversaire.
     *
     */
    fun lanceCombat() {
        println("Combat entre ${joueur.nom} et ${entraineurAdversaire.nom} commence !")
        while (!avoirGagne() && !avoirPerdu()) {
            val monstreJoueur = joueur.choisirMonstre() ?: continue
            val monstreAdverse = entraineurAdversaire.equipeMonstre.first { it.pv > 0 }

            println("${joueur.nom} envoie ${monstreJoueur.nom}")
            println("${entraineurAdversaire.nom} envoie ${monstreAdverse.nom}")

            val combat = CombatMonstre(monstreJoueur, monstreAdverse)
            combat.lancerCombat()
        }
        if (avoirGagne()) {
            val recompense = entraineurAdversaire.argents / 2
            println("Victoire ! Récompense : $recompense")
            joueur.argents += recompense
        } else {
            println("Défaite !")
            joueur.soigneEquipe()
            entraineurAdversaire.soigneEquipe()
        }
    }
}