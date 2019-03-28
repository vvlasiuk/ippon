package net.ukr.vlsv.ippon_secretar.data_base.daos

import androidx.room.*
import net.ukr.vlsv.ippon_secretar.data_base.table.JudgesNumberTable

@Dao
interface JudgesNumberTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: JudgesNumberTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: JudgesNumberTable)

    @Query("SELECT * from JudgesNumber ORDER BY description ASC")
    fun getList():  MutableList<JudgesNumberTable>

}