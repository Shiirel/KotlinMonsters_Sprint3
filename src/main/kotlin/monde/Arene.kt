package monde

import arene1
import badge
import dresseur.Entraineur
import jeu.CombatDresseur
import joueur
import monstre.IndividuMonstre


/**
 * Représente une arène située dans une ville.
 *
 * Une arène est composée de plusieurs dresseurs et un champion. Elle permet au joueur d'affronter tous les dresseurs de l'arène fin de remporter une récompense.
 *
 * @property id L'identifiant unique de l'arène.
 * @property nom Le nom de l'arène.
 * @property dresseurs Les dresseurs de l'arène
 * @property champion Le champion de l'arène
 */
class Arene (
    val id : Int,
    val nom : String,
    val dresseurs : MutableList<Entraineur> = mutableListOf(),
    val champion : Entraineur
){

    /**
     * Genère un combat entre le joueur et les dresseurs de l'arène.
     */
    fun challenger() {
        val listeDresseurs = mutableListOf<Entraineur>()
        val listeDresseursVivants = mutableListOf<Entraineur>()
        listeDresseurs.addAll(dresseurs)
        listeDresseurs.add(champion)
        for (dresseur in listeDresseurs) {
            var count = 0
            for(i in 0..dresseur.equipeMonstre.lastIndex) {
                if(dresseur.equipeMonstre[i].pv<=0) {
                    count+=1
                }
            }
            if (count<dresseur.equipeMonstre.size) {
                listeDresseursVivants.add(dresseur)
            }
        }

        while(listeDresseursVivants.isNotEmpty()) {
            var adversaire = listeDresseursVivants[0]
            var combat = CombatDresseur(1,joueur,adversaire)
            combat.lanceCombat()
            if (combat.avoirGagne()){
                println("${joueur.nom} a vaincu ${adversaire.nom} !")
                listeDresseursVivants.remove(adversaire)
            } else {
                println("${joueur.nom} a été vaincu par ${adversaire.nom} !")
                break
            }
        }
        println("${joueur.nom} a gagné tous ses combats dans l'arène ${arene1.nom}")
        joueur.sacAItems.add(badge)
        println("${joueur.nom} remporte le badge ${badge.nom}")
    }
}