package DAO

import jdbc.BDD
import monstre.IndividuMonstre
import monstre.EspeceMonstre
import dresseur.Entraineur
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement

/**
 * DAO pour interagir avec la table `IndividuMonstre`.
 *
 * Gère les relations avec `EspeceMonstres` et `Entraineurs`.
 */
class IndividuMonstreDAO(val bdd: BDD, val especeDAO: EspeceMonstreDAO, val entraineurDAO: EntraineurDAO) {

    /** Récupère tous les individus de la base. */
    fun findAll(): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        val sql = "SELECT * FROM IndividuMonstre"
        val stmt = bdd.connectionBDD!!.prepareStatement(sql)
        val rs = bdd.executePreparedStatement(stmt)

        if (rs != null) {
            while (rs.next()) {
                result.add(mapResultSetToIndividu(rs))
            }
        }
        stmt.close()
        return result
    }

    /** Recherche un individu par son ID. */
    fun findById(id: Int): IndividuMonstre? {
        val sql = "SELECT * FROM IndividuMonstre WHERE id = ?"
        val stmt = bdd.connectionBDD!!.prepareStatement(sql)
        stmt.setInt(1, id)
        val rs = bdd.executePreparedStatement(stmt)
        val result = if (rs != null && rs.next()) mapResultSetToIndividu(rs) else null
        stmt.close()
        return result
    }

    /** Recherche tous les individus d’un entraîneur. */
    fun findByEntraineurId(entraineurId: Int): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        val sql = "SELECT * FROM IndividuMonstre WHERE entraineur_equipe_id = ? OR entraineur_boite_id = ?"
        val stmt = bdd.connectionBDD!!.prepareStatement(sql)
        stmt.setInt(1, entraineurId)
        stmt.setInt(2, entraineurId)
        val rs = bdd.executePreparedStatement(stmt)

        if (rs != null) {
            while (rs.next()) {
                result.add(mapResultSetToIndividu(rs))
            }
        }
        stmt.close()
        return result
    }

    /** Sauvegarde un individu (insert ou update). */
    fun save(individu: IndividuMonstre): IndividuMonstre? {
        val stmt: PreparedStatement
        if (individu.id == 0) {
            val sql = """
                INSERT INTO IndividuMonstre
                (nom,niveau,attaque,defense,vitesse,attaqueSpe,defenseSpe,pvMax,potentiel,exp,pv,
                 espece_id,entraineur_equipe_id,entraineur_boite_id)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """.trimIndent()
            stmt = bdd.connectionBDD!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            setPreparedStatementIndividu(stmt, individu)
        } else {
            val sql = """
                UPDATE IndividuMonstre SET
                nom=?, niveau=?, attaque=?, defense=?, vitesse=?, attaqueSpe=?, defenseSpe=?,
                pvMax=?, potentiel=?, exp=?, pv=?, espece_id=?, entraineur_equipe_id=?, entraineur_boite_id=?
                WHERE id=?
            """.trimIndent()
            stmt = bdd.connectionBDD!!.prepareStatement(sql)
            setPreparedStatementIndividu(stmt, individu)
            stmt.setInt(15, individu.id)
        }

        val nbLigneMaj = stmt.executeUpdate()
        if (nbLigneMaj > 0) {
            val keys = stmt.generatedKeys
            if (keys.next()) individu.id = keys.getInt(1)
            stmt.close()
            return individu
        }

        stmt.close()
        return null
    }

    /** Supprime un individu par son ID. */
    fun deleteById(id: Int): Boolean {
        val sql = "DELETE FROM IndividuMonstre WHERE id = ?"
        val stmt = bdd.connectionBDD!!.prepareStatement(sql)
        stmt.setInt(1, id)
        return try {
            val nb = stmt.executeUpdate()
            stmt.close()
            nb > 0
        } catch (e: SQLException) {
            println("Erreur suppression individu: ${e.message}")
            false
        }
    }

    /** Sauvegarde plusieurs individus. */
    fun saveAll(individus: Collection<IndividuMonstre>): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        for (i in individus) {
            val saved = save(i)
            if (saved != null) result.add(saved)
        }
        return result
    }

    /** Mappe un ResultSet en IndividuMonstre, récupère espèce et entraîneur. */
    private fun mapResultSetToIndividu(rs: java.sql.ResultSet): IndividuMonstre {
        val especeId = rs.getInt("espece_id")
        val espece = especeDAO.findById(especeId) ?: error("Espèce introuvable: $especeId")

        val entraineurEquipeId = rs.getInt("entraineur_equipe_id")
        val entraineurEquipe = if (entraineurEquipeId != 0) entraineurDAO.findById(entraineurEquipeId) else null

        val entraineurBoiteId = rs.getInt("entraineur_boite_id")
        val entraineurBoite = if (entraineurBoiteId != 0) entraineurDAO.findById(entraineurBoiteId) else null

        val individu = IndividuMonstre(
            rs.getInt("id"),
            rs.getString("nom"),
            espece,
            entraineurEquipe,
            rs.getDouble("exp")
        )
        individu.niveau = rs.getInt("niveau")
        individu.attaque = rs.getInt("attaque")
        individu.defense = rs.getInt("defense")
        individu.vitesse = rs.getInt("vitesse")
        individu.attaqueSpe = rs.getInt("attaqueSpe")
        individu.defenseSpe = rs.getInt("defenseSpe")
        individu.pvMax = rs.getInt("pvMax")
        individu.pv = rs.getInt("pv")
        individu.potentiel = rs.getDouble("potentiel")
        return individu
    }

    /** Remplit un PreparedStatement avec les valeurs de l’individu. */
    private fun setPreparedStatementIndividu(ps: PreparedStatement, individu: IndividuMonstre) {
        ps.setString(1, individu.nom)
        ps.setInt(2, individu.niveau)
        ps.setInt(3, individu.attaque)
        ps.setInt(4, individu.defense)
        ps.setInt(5, individu.vitesse)
        ps.setInt(6, individu.attaqueSpe)
        ps.setInt(7, individu.defenseSpe)
        ps.setInt(8, individu.pvMax)
        ps.setDouble(9, individu.potentiel)
        ps.setDouble(10, individu.exp)
        ps.setInt(11, individu.pv)
        ps.setInt(12, individu.espece.id)
        ps.setObject(13, individu.entraineur?.id)
        ps.setObject(14, individu.entraineur?.id)
    }
}
