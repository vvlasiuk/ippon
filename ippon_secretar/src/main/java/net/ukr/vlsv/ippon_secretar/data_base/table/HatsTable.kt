package net.ukr.vlsv.ippon_secretar.data_base.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Hats")
class HatsTable (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
        @ColumnInfo(name = "api_id") var api_id: Int,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "competitions_id") var competitions_id: Int,
        @ColumnInfo(name = "categories_id") var categories_id: Int,
        @ColumnInfo(name = "type_competition_id") var type_competition_id: Int,
        @ColumnInfo(name = "desk_id") var desk_id: Int,
        @ColumnInfo(name = "finish") var finish: Int
)

