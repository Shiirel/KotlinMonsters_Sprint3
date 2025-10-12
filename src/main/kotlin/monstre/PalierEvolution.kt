package monstre

/**
 * Représente une évolution possible pour le monstre.
 *
 * Une arène est composée de plusieurs dresseurs et un champion. Elle permet au joueur d'affronter tous les dresseurs de l'arène fin de remporter une récompense.
 *
 * @property id L'identifiant unique de l'évolution.
 * @property niveauRequis Le niveau requis pour générer l'évolution.
 * @property evolution L'espèce évoluée
 */
class PalierEvolution (
    var id : Int,
    var niveauRequis : Int,
    var evolution : EspeceMonstre
) {

    /**
     * Détermine si le monstre a atteint le niveau requis pour évoluer
     *
     * @return `true` si le monstre peut évoluer,
     *        `false` sinon.
     */
    fun peutEvoluer(individuMonstre: IndividuMonstre) : Boolean {
        if(individuMonstre.niveau == niveauRequis) {
            return true
        } else {
            return false
        }
    }
}

