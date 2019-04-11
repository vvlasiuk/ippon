package net.ukr.vlsv.ippon_secretar.data_base.daos

import androidx.room.*
import net.ukr.vlsv.ippon_secretar.data_base.table.DeskTable

@Dao
interface DeskTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: DeskTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: DeskTable)

    @Query("SELECT * from Desk ORDER BY description ASC")
    fun getList():  MutableList<DeskTable>

}