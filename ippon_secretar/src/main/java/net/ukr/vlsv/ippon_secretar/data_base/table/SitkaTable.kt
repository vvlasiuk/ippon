package net.ukr.vlsv.ippon_secretar.data_base.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sitka")
class SitkaTable (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "api_id")  var api_id: Int,
    @ColumnInfo(name = "number") var number: Int,
    @ColumnInfo(name = "parent1") var parent1: Int,
    @ColumnInfo(name = "parent2") var parent2: Int,
    @ColumnInfo(name = "hat_id")  var hat_id: Int,
    @ColumnInfo(name = "base")    var base: Int,
    @ColumnInfo(name = "participant1_id") var participant1_id: Int,
    @ColumnInfo(name = "participant2_id") var participant2_id: Int,
    @ColumnInfo(name = "Description1") var Description1: String,
    @ColumnInfo(name = "Description2") var Description2: String,
    @ColumnInfo(name = "result1") var result1: Int,
    @ColumnInfo(name = "result2") var result2: Int,
    @ColumnInfo(name = "winner") var winner: Int
)