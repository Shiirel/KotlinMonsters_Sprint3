package DAO

import jdbc.BDD
import monstre.EspeceMonstre
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.SQLException

/**
 * DAO (Data Access Object) pour interagir avec la table `EspeceMonstres`.
 *
 * Opérations CRUD :
 * - 🔍 Lecture : findAll, findById, findByNom
 * - 💾 Sauvegarde : save, saveAll
 * - ❌ Suppression : deleteById
 *
 * @param bdd Connexion à la base de données.
 */
class EspeceMonstreDAO(val bdd: BDD) {

    /** Récupère toutes les espèces présentes dans la base de données. */
    fun findAll(): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        val sql = "SELECT * FROM EspeceMonstres"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        val rs = bdd.executePreparedStatement(requetePreparer)

        if (rs != null) {
            while (rs.next()) {
                result.add(mapResultSetToEspece(rs))
            }
        }

        requetePreparer.close()
        return result
    }

    /** Recherche une espèce par son identifiant. */
    fun findById(id: Int): EspeceMonstre? {
        val sql = "SELECT * FROM EspeceMonstres WHERE id = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setInt(1, id)
        val rs = bdd.executePreparedStatement(requetePreparer)
        val result = if (rs != null && rs.next()) mapResultSetToEspece(rs) else null
        requetePreparer.close()
        return result
    }

    /** Recherche une espèce par son nom. */
    fun findByNom(nomRechercher: String): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        val sql = "SELECT * FROM EspeceMonstres WHERE nom = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setString(1, nomRechercher)
        val rs = bdd.executePreparedStatement(requetePreparer)

        if (rs != null) {
            while (rs.next()) {
                result.add(mapResultSetToEspece(rs))
            }
        }

        requetePreparer.close()
        return result
    }

    /** Insère ou met à jour une espèce dans la base. */
    fun save(espece: EspeceMonstre): EspeceMonstre? {
        val requetePreparer: PreparedStatement
        if (espece.id == 0) {
            // Insertion
            val sql = """
                INSERT INTO EspeceMonstres
                (nom,type,baseAttaque,baseDefense,baseVitesse,baseAttaqueSpe,baseDefenseSpe,basePv,
                 modAttaque,modDefense,modVitesse,modAttaqueSpe,modDefenseSpe,modPv,
                 description,particularites,caracteres)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """.trimIndent()
            requetePreparer = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            setPreparedStatementEspece(requetePreparer, espece)
        } else {
            // Mise à jour
            val sql = """
                UPDATE EspeceMonstres SET 
                nom=?, type=?, baseAttaque=?, baseDefense=?, baseVitesse=?, baseAttaqueSpe=?, baseDefenseSpe=?, basePv=?,
                modAttaque=?, modDefense=?, modVitesse=?, modAttaqueSpe=?, modDefenseSpe=?, modPv=?,
                description=?, particularites=?, caracteres=?
                WHERE id=?
            """.trimIndent()
            requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
            setPreparedStatementEspece(requetePreparer, espece)
            requetePreparer.setInt(18, espece.id)
        }

        val nbLigneMaj = requetePreparer.executeUpdate()
        if (nbLigneMaj > 0) {
            val generatedKeys = requetePreparer.generatedKeys
            if (generatedKeys.next()) espece.id = generatedKeys.getInt(1)
            requetePreparer.close()
            return espece
        }

        requetePreparer.close()
        return null
    }

    /** Supprime une espèce par son identifiant. */
    fun deleteById(id: Int): Boolean {
        val sql = "DELETE FROM EspeceMonstres WHERE id = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setInt(1, id)

        return try {
            val nbLigneMaj = requetePreparer.executeUpdate()
            requetePreparer.close()
            nbLigneMaj > 0
        } catch (erreur: SQLException) {
            println("Erreur lors de la suppression de l'espèce : ${erreur.message}")
            false
        }
    }

    /** Sauvegarde plusieurs espèces. */
    fun saveAll(especes: Collection<EspeceMonstre>): MutableList<EspeceMonstre> {
        val result = mutableListOf<EspeceMonstre>()
        for (e in especes) {
            val sauvegarde = save(e)
            if (sauvegarde != null) result.add(sauvegarde)
        }
        return result
    }

    /** Mappe un ResultSet en objet EspeceMonstre. */
    private fun mapResultSetToEspece(rs: java.sql.ResultSet): EspeceMonstre {
        return EspeceMonstre(
            rs.getInt("id"),
            rs.getString("nom"),
            rs.getString("type"),
            rs.getInt("basePv"),
            rs.getInt("baseAttaque"),
            rs.getInt("baseDefense"),
            rs.getInt("baseVitesse"),
            rs.getInt("baseAttaqueSpe"),
            rs.getInt("baseDefenseSpe"),
            rs.getDouble("modPv"),
            rs.getDouble("modAttaque"),
            rs.getDouble("modDefense"),
            rs.getDouble("modVitesse"),
            rs.getDouble("modAttaqueSpe"),
            rs.getDouble("modDefenseSpe"),
            rs.getString("description"),
            rs.getString("particularites"),
            rs.getString("caracteres")
        )
    }

    /** Remplit un PreparedStatement avec les valeurs d'une espèce. */
    private fun setPreparedStatementEspece(ps: PreparedStatement, espece: EspeceMonstre) {
        ps.setString(1, espece.nom)
        ps.setString(2, espece.type)
        ps.setInt(3, espece.baseAttaque)
        ps.setInt(4, espece.baseDefense)
        ps.setInt(5, espece.baseVitesse)
        ps.setInt(6, espece.baseAttaqueSpe)
        ps.setInt(7, espece.baseDefenseSpe)
        ps.setInt(8, espece.basePv)
        ps.setDouble(9, espece.modAttaque)
        ps.setDouble(10, espece.modDefense)
        ps.setDouble(11, espece.modVitesse)
        ps.setDouble(12, espece.modAttaqueSpe)
        ps.setDouble(13, espece.modDefenseSpe)
        ps.setDouble(14, espece.modPv)
        ps.setString(15, espece.description)
        ps.setString(16, espece.particularites)
        ps.setString(17, espece.caractères)
    }
}
