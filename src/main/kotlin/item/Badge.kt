package item
//import item.Item

/**
 * Représente un badge récupérable par le joueur lorsqu'il est champion d'une arène.
 *
 * La classe Badge hérite de la classe Item.
 *
 * @property id L'identifiant unique du badge.
 * @property nom Le nom du badge.
 * @property description La description du badge.
 */
class Badge(truc: Int, nom: String, description: String): Item(truc,nom,description) {
}
