package net.ukr.vlsv.ippon_secretar.data_base.daos


import androidx.room.*
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable

@Dao
interface HatsTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: HatsTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: HatsTable)

    @Query("SELECT * from Hats ORDER BY description ASC")
    fun getListAll():  MutableList<HatsTable>

    @Query("SELECT * from Hats where finish = 0 and competitions_id=:competitionsID AND desk_id=:descID ORDER BY description ASC")
    fun getList(competitionsID: Int, descID: Int):  MutableList<HatsTable>

    @Query("SELECT * from Hats where finish = 1 ORDER BY description ASC")
    fun getListFinish():  MutableList<HatsTable>
}

