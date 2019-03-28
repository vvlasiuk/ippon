package net.ukr.vlsv.ippon_secretar.data_base.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Desk")
class DeskTable (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "api_id") var api_id: Int,
    @ColumnInfo(name = "description") var description: String
    )