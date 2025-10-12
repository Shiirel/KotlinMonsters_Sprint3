package item
import joueur
import monstre.IndividuMonstre
import kotlin.random.Random



/**
 * Représente un objet MonsterKube permettant au joueur de tenter de capturer un monstre lors d'un combat de monstres.
 *
 * La classe MonsterKube hérite de la classe Item et implémente l'interface Utilisable.
 *
 * @property id L'identifiant unique du MonsterKube.
 *  @property nom Le nom du MonsterKube.
 *  @property description La description du MonsterKube.
 *  @property chanceCapture Chances de capturer la cible.
 */
class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double,
) : Item(id, nom, description), Utilisable {

    /**
     * Applique l'effet de l'objet ou de l'action sur le monstre cible.
     *
     * @param cible Le [IndividuMonstre] sur lequel l'objet est utilisé.
     * @return `true` si la capture est réussie,
     *         `false` sinon.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        val res = true
        println("Vous lancez le Monster Kube !")
        if(cible.entraineur != null) {
            print("Le monstre ne peut pas être capturé.")
        } else {
            val ratioVie = cible.pv/cible.pvMax
            var chanceEffective = chanceCapture * (1.5 - ratioVie)
            if (chanceEffective < 5.0) {
                chanceEffective = 5.0
            }
            val nbAleatoire = kotlin.random.Random.nextInt(0,101)
            if (nbAleatoire<chanceEffective) {
                println("Le monstre est capturé !")
                print("Saisir un nouveau nom : ")
                val nouveauNom = readln()
                if(nouveauNom.isNotEmpty()) {
                    cible.nom = nouveauNom
                }
                if (joueur.equipeMonstre.size >= 6) {
                    joueur.boiteMonstre += cible
                } else {
                    joueur.equipeMonstre += cible
                }
                cible.entraineur = joueur
                val res = true
            } else {
                print("Presque ! Le Kube n'a pas pu capturer le monstre !")
                val res = false
            }
        }
        return res
    }
}