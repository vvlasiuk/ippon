package net.ukr.vlsv.ippon_secretar.data_base.daos

import androidx.room.*
import net.ukr.vlsv.ippon_secretar.data_base.table.SitkaTable

@Dao
interface SitkaTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: SitkaTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: SitkaTable)

    @Query("SELECT * from Sitka ORDER BY number ASC")
    fun getListAll():  MutableList<SitkaTable>

    @Query("SELECT * from Sitka where hat_id=:hatID ORDER BY number ASC")
    fun getList(hatID: Int):  MutableList<SitkaTable>
}

