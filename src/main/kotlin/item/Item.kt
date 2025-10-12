package item

/**
 * Représente un item dans le contexte du jeu.
 *
 * La classe Item est ouverte à l'héritage, elle peut instancier un badge ou un MonsterKube.
 * Les items sont parfois utilisables lors des combats de monstres.
 *
 * @property id L'identifiant unique de l'item.
 * @property nom Le nom de l'item.
 * @property description La description de l'item.
 */
open class Item (
    var id : Int,
    var nom : String,
    var description : String
) {
}