package net.ukr.vlsv.ippon_secretar.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import net.ukr.vlsv.ippon_secretar.data_base.daos.*
import net.ukr.vlsv.ippon_secretar.data_base.table.*

@Database(entities = [DeskTable::class, JudgesNumberTable::class, CompetitionsTable::class, HatsTable::class, SitkaTable::class], version = 1)
abstract class IpponDatabase: RoomDatabase() {
    abstract fun deskTableDao(): DeskTableDao
    abstract fun judgesNumberTableDao(): JudgesNumberTableDao
    abstract fun competitionsTableDao(): CompetitionsTableDao
    abstract fun hatsTableDao(): HatsTableDao
    abstract fun sitkaTableDao(): SitkaTableDao
}