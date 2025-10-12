package monde
import monde.*
import monstre.EspeceMonstre


/**
 * Représente un lieu où le joueur peut défier une arène et soigner son équipe.
 *
 * @property id L'identifiant unique de la ville.
 * @property nom Le nom de la ville.
 * @property expZone L'expérience moyenne des monstres de cette zone.
 * @property zoneSuivante
 * @property zonePrecedente
 * @property arene Un objet Arene présent (ou non) dans la ville

 */
class Ville (
    id : Int,
    nom : String,
    expZone : Int,
    especeMonstres : MutableList<EspeceMonstre> = mutableListOf(),
    zoneSuivante : Zone? = null,
    zonePrecedente : Zone? = null,
    var arene : Arene? = null
) : Zone(id,nom,expZone,especeMonstres,zoneSuivante,zonePrecedente) {

}