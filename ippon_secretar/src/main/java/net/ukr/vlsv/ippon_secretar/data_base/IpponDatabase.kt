package net.ukr.vlsv.ippon_secretar.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import net.ukr.vlsv.ippon_secretar.data_base.daos.DeskTableDao
import net.ukr.vlsv.ippon_secretar.data_base.daos.JudgesNumberTableDao
import net.ukr.vlsv.ippon_secretar.data_base.table.DeskTable
import net.ukr.vlsv.ippon_secretar.data_base.table.JudgesNumberTable

@Database(entities = [DeskTable::class, JudgesNumberTable::class], version = 3)
abstract class IpponDatabase: RoomDatabase() {
    abstract fun deskTableDao(): DeskTableDao
    abstract fun judgesNumberTableDao(): JudgesNumberTableDao
}