package net.ukr.vlsv.ippon_secretar.data_base.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Competitions")
class CompetitionsTable (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
        @ColumnInfo(name = "api_id") var api_id: Int,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "date_from") var date_from: Int,
        @ColumnInfo(name = "date_to") var date_to: Int,
        @ColumnInfo(name = "place_id") var place_id: Int
)