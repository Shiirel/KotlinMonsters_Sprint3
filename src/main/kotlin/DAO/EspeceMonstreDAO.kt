package DAO

import dresseur.Entraineur
import jdbc.BDD
import monstre.EspeceMonstre
import java.sql.*
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.SQLException



/**
 * DAO (Data Access Object) permettant d'interagir avec la table `EspeceMonstre`.
 *
 * Cette classe gère les opérations CRUD :
 * - 🔍 Lecture (findAll, findById, findByNom)
 * - 💾 Sauvegarde (save, saveAll)
 * - ❌ Suppression (deleteById)
 *
 * @param bdd L'objet de connexion à la base de données.
 */
class EspeceMonstreDAO(val bdd: BDD) {


    /**
     * Récupère toutes les espèces présentes dans la base de données.
     *
     * @return Une liste mutable d'entraîneurs trouvés.
     */
    fun findAll(): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        val sql = "SELECT * FROM EspeceMonstres"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)
36
        if (resultatRequete != null) {
            while (resultatRequete.next()) {
                val id = resultatRequete.getInt("id")
                val nom = resultatRequete.getString("nom")
                val type = resultatRequete.getString("type")
                val basePV = resultatRequete.getInt("basePV")
                val baseAttaque = resultatRequete.getInt("baseAttaque")
                val baseDefense = resultatRequete.getInt("baseDefense")
                val baseVitesse = resultatRequete.getInt("baseVitesse")
                val baseAttaqueSpe = resultatRequete.getInt("baseAttaqueSpe")
                val baseDefenseSpe = resultatRequete.getInt("baseDefenseSpe")
                val modPV = resultatRequete.getDouble("modPV")
                val modAttaque = resultatRequete.getDouble("modAttaque")
                val modDefense = resultatRequete.getDouble("modDefense")
                val modVitesse = resultatRequete.getDouble("modVitesse")
                val modAttaqueSpe = resultatRequete.getDouble("modAttaqueSpe")
                val modDefenseSpe = resultatRequete.getDouble("modDefenseSpe")
                val description = resultatRequete.getString("description")
                val particularites = resultatRequete.getString("particularites")
                val caractere = resultatRequete.getString("caracteres")
                result.add(EspeceMonstre(id, nom,type,basePV,baseAttaque,baseDefense,baseVitesse,baseAttaqueSpe,baseDefenseSpe,modPV,modAttaque,modDefense,modVitesse,modAttaqueSpe,modDefenseSpe,description,particularites,caractere))
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
    fun findById(id: Int): EspeceMonstre? {
        var result: EspeceMonstre? = null
        val sql = "SELECT * FROM EspeceMonstres WHERE id = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setInt(1, id) // insere la valeur de l'id dans la requete preparer
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)

        if (resultatRequete != null && resultatRequete.next()) {
            val nom = resultatRequete.getString("nom")
            val type = resultatRequete.getString("type")
            val basePV = resultatRequete.getInt("basePV")
            val baseAttaque = resultatRequete.getInt("baseAttaque")
            val baseDefense = resultatRequete.getInt("baseDefense")
            val baseVitesse = resultatRequete.getInt("baseVitesse")
            val baseAttaqueSpe = resultatRequete.getInt("baseAttaqueSpe")
            val baseDefenseSpe = resultatRequete.getInt("baseDefenseSpe")
            val modPV = resultatRequete.getDouble("modPV")
            val modAttaque = resultatRequete.getDouble("modAttaque")
            val modDefense = resultatRequete.getDouble("modDefense")
            val modVitesse = resultatRequete.getDouble("modVitesse")
            val modAttaqueSpe = resultatRequete.getDouble("modAttaqueSpe")
            val modDefenseSpe = resultatRequete.getDouble("modDefenseSpe")
            val description = resultatRequete.getString("description")
            val particularites = resultatRequete.getString("particularites")
            val caractere = resultatRequete.getString("caracteres")
            result = EspeceMonstre(id,nom,type,basePV,baseAttaque,baseDefense,baseVitesse,baseAttaqueSpe,baseDefenseSpe,modPV,modAttaque,modDefense,modVitesse,modAttaqueSpe,modDefenseSpe,description,particularites,caractere)
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
    fun findByNom(nomRechercher: String): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        val sql = "SELECT * FROM EspeceMonstre WHERE nom = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setString(1, nomRechercher)
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)

        if (resultatRequete != null) {
            while (resultatRequete.next()) {
                val id = resultatRequete.getInt("id")
                val nom = resultatRequete.getString("nom")
                val type = resultatRequete.getString("type")
                val basePV = resultatRequete.getInt("basePV")
                val baseAttaque = resultatRequete.getInt("baseAttaque")
                val baseDefense = resultatRequete.getInt("baseDefense")
                val baseVitesse = resultatRequete.getInt("baseVitesse")
                val baseAttaqueSpe = resultatRequete.getInt("baseAttaqueSpe")
                val baseDefenseSpe = resultatRequete.getInt("baseDefenseSpe")
                val modPV = resultatRequete.getDouble("modPV")
                val modAttaque = resultatRequete.getDouble("modAttaque")
                val modDefense = resultatRequete.getDouble("modDefense")
                val modVitesse = resultatRequete.getDouble("modVitesse")
                val modAttaqueSpe = resultatRequete.getDouble("modAttaqueSpe")
                val modDefenseSpe = resultatRequete.getDouble("modDefenseSpe")
                val description = resultatRequete.getString("description")
                val particularites = resultatRequete.getString("particularites")
                val caractere = resultatRequete.getString("caractere")
                result.add(EspeceMonstre(id, nom,type,basePV,baseAttaque,baseDefense,baseVitesse,baseAttaqueSpe,baseDefenseSpe,modPV,modAttaque,modDefense,modVitesse,modAttaqueSpe,modDefenseSpe,description,particularites,caractere))
            }
        }

        requetePreparer.close()
        return result
    }



    /**
     * Insère ou met à jour une espece dans la base.
     *
     * @param espece L'espece à sauvegarder.
     * @return L'espece sauvegardé avec son ID mis à jour si insertion.
     */
    fun save(espece: EspeceMonstre): EspeceMonstre? {
        val requetePreparer: PreparedStatement

        if (espece.id == 0) {
            // Insertion
            val sql = "INSERT INTO EspeceMonstre (nom,type,basePV,baseAttaque,baseDefense,baseVitesse,baseAttaqueSpe,baseDefenseSpe,modPV,modAttaque,modDefense,modVitesse,modAttaqueSpe,modDefenseSpe,description,particularites,caractere) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?))"
            requetePreparer = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            requetePreparer.setString(1, espece.nom)
            requetePreparer.setString(2, espece.type)
            requetePreparer.setInt(3, espece.basePv)
            requetePreparer.setInt(4, espece.baseAttaque)
            requetePreparer.setInt(5, espece.baseDefense)
            requetePreparer.setInt(6, espece.baseVitesse)
            requetePreparer.setInt(7, espece.baseAttaqueSpe)
            requetePreparer.setInt(8, espece.baseDefenseSpe)
            requetePreparer.setDouble(9, espece.modPv)
            requetePreparer.setDouble(10, espece.modAttaque)
            requetePreparer.setDouble(11, espece.modDefense)
            requetePreparer.setDouble(12, espece.modVitesse)
            requetePreparer.setDouble(13, espece.modAttaqueSpe)
            requetePreparer.setString(14, espece.description)
            requetePreparer.setString(15, espece.particularites)
            requetePreparer.setString(16, espece.caractères)

        } else {
            // Mise à jour
            val sql = "UPDATE EspeceMonstre SET nom=?,type=?,basePV=?,baseAttaque=?,baseDefense=?,baseVitesse=?,baseAttaqueSpe=?,,baseDefenseSpe=?,,modPV=?,,modAttaque=?,,modDefense=?,modVitesse=?,modAttaqueSpe=?,modDefenseSpe=?,description=?,particularites=?,caractere=? WHERE id = ?"
            requetePreparer = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            requetePreparer.setString(1, espece.nom)
            requetePreparer.setString(2, espece.type)
            requetePreparer.setInt(3, espece.basePv)
            requetePreparer.setInt(4, espece.baseAttaque)
            requetePreparer.setInt(5, espece.baseDefense)
            requetePreparer.setInt(6, espece.baseVitesse)
            requetePreparer.setInt(7, espece.baseAttaqueSpe)
            requetePreparer.setInt(8, espece.baseDefenseSpe)
            requetePreparer.setDouble(9, espece.modPv)
            requetePreparer.setDouble(10, espece.modAttaque)
            requetePreparer.setDouble(11, espece.modDefense)
            requetePreparer.setDouble(12, espece.modVitesse)
            requetePreparer.setDouble(13, espece.modAttaqueSpe)
            requetePreparer.setString(14, espece.description)
            requetePreparer.setString(15, espece.particularites)
            requetePreparer.setString(16, espece.caractères)
        }

        val nbLigneMaj = requetePreparer.executeUpdate()

        if (nbLigneMaj > 0) {
            val generatedKeys = requetePreparer.generatedKeys
            if (generatedKeys.next()) {
                espece.id = generatedKeys.getInt(1)
            }
            requetePreparer.close()
            return espece
        }

        requetePreparer.close()
        return null
    }


    /**
     * Supprime un espece par son identifiant.
     *
     * @param id L'ID de l'espece à supprimer.
     * @return `true` si la suppression a réussi, sinon `false`.
     */
    fun deleteById(id: Int): Boolean {
        val sql = "DELETE FROM EspeceMonstre WHERE id = ?"
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
     * Sauvegarde plusieurs especes dans la base de données.
     *
     * @param especes Liste d'especes à sauvegarder.
     * @return Liste des especes sauvegardés.
     */
    fun saveAll(especes: Collection<EspeceMonstre>): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        for (e in especes) {
            val sauvegarde = save(e)
            if (sauvegarde != null) result.add(sauvegarde)
        }
        return result
    }


}