package net.ukr.vlsv.ippon_secretar.data_base.daos

import androidx.room.*
import net.ukr.vlsv.ippon_secretar.data_base.table.CompetitionsTable

@Dao
interface CompetitionsTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: CompetitionsTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: CompetitionsTable)

    @Query("SELECT * from Competitions ORDER BY description ASC")
    fun getList():  MutableList<CompetitionsTable>

}