package DAO

import jdbc.BDD
import monstre.IndividuMonstre
import monstre.EspeceMonstre
import dresseur.Entraineur
import entraineurDAO
import especeMonstreDAO
import java.sql.*
/*import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement*/

/**
 * DAO pour interagir avec la table `IndividuMonstre`.
 *
 * Gère les relations avec `EspeceMonstres` et `Entraineurs`.
 */
class IndividuMonstreDAO(val bdd: BDD) {
    val especeDao = especeMonstreDAO
    val entraineurDao = entraineurDAO


    /**
     * Récupère toutes les espèces présentes dans la base de données.
     *
     * @return Une liste mutable d'entraîneurs trouvés.
     */
    fun findAll(): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        val sql = "SELECT * FROM IndividuMonstre"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)
        if (resultatRequete != null) {
            while (resultatRequete.next()) {
                val id = resultatRequete.getInt("id")
                val nom = resultatRequete.getString("nom")
                val idEspece = resultatRequete.getInt("espece_id")
                val idEntraineur = resultatRequete.getInt("entraineur_equipe_id")
                val niveau = resultatRequete.getInt("niveau")
                val attaque = resultatRequete.getInt("attaque")
                val defense = resultatRequete.getInt("defense")
                val vitesse = resultatRequete.getInt("vitesse")
                val attaqueSpe = resultatRequete.getInt("attaqueSpe")
                val defenseSpe = resultatRequete.getInt("defenseSpe")
                val pvMax = resultatRequete.getInt("pvMax")
                val potentiel = resultatRequete.getDouble("potentiel")
                val exp = resultatRequete.getDouble("exp")
                val pv = resultatRequete.getInt("pv")

                val espece = especeDao.findById(idEspece)!!
                val entraineur = entraineurDao.findById((idEntraineur))!!
                result.add(IndividuMonstre(id, nom, espece,entraineur,0.0))
            }
        }

        requetePreparer.close()
        return result
    }


    /**
     * Recherche une espece par son identifiant unique.
     *
     * @param id L'identifiant de l'espece.
     * @return L'espece trouvé ou `null` si aucun résultat.
     */
    fun findById(id: Int): IndividuMonstre? {
        var result: IndividuMonstre? = null
        val sql = "SELECT * FROM IndividuMonstre WHERE id = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setInt(1, id) // insere la valeur de l'id dans la requete preparer
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)

        if (resultatRequete != null && resultatRequete.next()) {
            val id = resultatRequete.getInt("id")
            val nom = resultatRequete.getString("nom")
            val idEspece = resultatRequete.getInt("espece")
            val idEntraineur = resultatRequete.getInt("entraineur")
            val niveau = resultatRequete.getInt("niveau")
            val attaque = resultatRequete.getInt("attaque")
            val defense = resultatRequete.getInt("defense")
            val vitesse = resultatRequete.getInt("vitesse")
            val attaqueSpe = resultatRequete.getInt("attaqueSpe")
            val defenseSpe = resultatRequete.getInt("defenseSpe")
            val pvMax = resultatRequete.getInt("pvMax")
            val potentiel = resultatRequete.getDouble("potentiel")
            val exp = resultatRequete.getDouble("exp")
            val pv = resultatRequete.getInt("pv")

            val espece = especeDao.findById(idEspece)!!
            val entraineur = entraineurDao.findById((idEntraineur))!!
            result = IndividuMonstre(id, nom, espece, entraineur, 0.0)
        }

        requetePreparer.close()
        return result
    }


    /**
     * Recherche une espece par son nom.
     *
     * @param nomRechercher Le nom de l'espece à rechercher.
     * @return Une liste d'espece correspondant au nom donné.
     */
    fun findByNom(nomRechercher: String): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        val sql = "SELECT * FROM IndividuMonstre WHERE nom = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setString(1, nomRechercher)
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)

        if (resultatRequete != null) {
            while (resultatRequete.next()) {
                val id = resultatRequete.getInt("id")
                val nom = resultatRequete.getString("nom")
                val idEspece = resultatRequete.getInt("espece")
                val idEntraineur = resultatRequete.getInt("entraineur")
                val niveau = resultatRequete.getInt("niveau")
                val attaque = resultatRequete.getInt("attaque")
                val defense = resultatRequete.getInt("defense")
                val vitesse = resultatRequete.getInt("vitesse")
                val attaqueSpe = resultatRequete.getInt("attaqueSpe")
                val defenseSpe = resultatRequete.getInt("defenseSpe")
                val pvMax = resultatRequete.getInt("pvMax")
                val potentiel = resultatRequete.getDouble("potentiel")
                val exp = resultatRequete.getDouble("exp")
                val pv = resultatRequete.getInt("pv")

                val espece = especeDao.findById(idEspece)!!
                val entraineur = entraineurDao.findById((idEntraineur))!!
                val result = IndividuMonstre(id, nom, espece, entraineur, 0.0)
            }

        }
        requetePreparer.close()
        return result
    }



        /**
         * Insère ou met à jour un individu dans la base.
         *
         * @param individu L'individu à sauvegarder.
         * @return L'individu sauvegardé avec son ID mis à jour si insertion.
         */
        fun save(individu: IndividuMonstre): IndividuMonstre? {
            val requetePreparer: PreparedStatement

            if (individu.id == 0) {
                // Insertion
                val sql =
                    "INSERT INTO IndividuMonstre (id, nom,niveau,attaque,defense,vitesse,attaqueSpe,defenseSpe,pvMax,potentiel,exp,pv) VALUES (?,?,?,?,?,?,?,?,?,?,?))"
                requetePreparer = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                requetePreparer.setInt(1, individu.id)
                requetePreparer.setString(2, individu.nom)
                requetePreparer.setInt(3, individu.niveau)
                requetePreparer.setInt(4, individu.attaque)
                requetePreparer.setInt(5, individu.defense)
                requetePreparer.setInt(6, individu.vitesse)
                requetePreparer.setInt(7, individu.attaqueSpe)
                requetePreparer.setInt(8, individu.defenseSpe)
                requetePreparer.setInt(9, individu.pvMax)
                requetePreparer.setDouble(10, individu.potentiel)
                requetePreparer.setDouble(11, individu.exp)
                requetePreparer.setInt(12, individu.pv)

            } else {
                // Mise à jour
                val sql =
                    "UPDATE IndivuMonstre SET id=?, nom=?,niveau=?,attaque=?,defense=?,vitesse=?,attaqueSpe=?,defenseSpe=?,pvMax=?,potentiel=?,exp=?,pv=? WHERE id = ?"
                requetePreparer = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                requetePreparer.setInt(1, individu.id)
                requetePreparer.setString(2, individu.nom)
                requetePreparer.setInt(3, individu.niveau)
                requetePreparer.setInt(4, individu.attaque)
                requetePreparer.setInt(5, individu.defense)
                requetePreparer.setInt(6, individu.vitesse)
                requetePreparer.setInt(7, individu.attaqueSpe)
                requetePreparer.setInt(8, individu.defenseSpe)
                requetePreparer.setInt(9, individu.pvMax)
                requetePreparer.setDouble(10, individu.potentiel)
                requetePreparer.setDouble(11, individu.exp)
                requetePreparer.setInt(12, individu.pv)

            }

            val nbLigneMaj = requetePreparer.executeUpdate()

            if (nbLigneMaj > 0) {
                val generatedKeys = requetePreparer.generatedKeys
                if (generatedKeys.next()) {
                    individu.id = generatedKeys.getInt(1)
                }
                requetePreparer.close()
                return individu
            }

            requetePreparer.close()
            return null
        }

        /**
         * Supprime un individu par son identifiant.
         *
         * @param id L'ID de l'individu à supprimer.
         * @return `true` si la suppression a réussi, sinon `false`.
         */
        fun deleteById(id: Int): Boolean {
            val sql = "DELETE FROM IndividuMonstre WHERE id = ?"
            val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
            requetePreparer.setInt(1, id)

            return try {
                val nbLigneMaj = requetePreparer.executeUpdate()
                requetePreparer.close()
                nbLigneMaj > 0
            } catch (erreur: SQLException) {
                println("Erreur lors de la suppression de l'espece : ${erreur.message}")
                false
            }
        }


        /**
         * Sauvegarde plusieurs individus dans la base de données.
         *
         * @param individus Liste d'individus à sauvegarder.
         * @return Liste des individus sauvegardés.
         */
        fun saveAll(individus: Collection<IndividuMonstre>): MutableList<IndividuMonstre> {
            val result = mutableListOf<IndividuMonstre>()
            for (e in individus) {
                val sauvegarde = save(e)
                if (sauvegarde != null) result.add(sauvegarde)
            }
            return result
        }
}