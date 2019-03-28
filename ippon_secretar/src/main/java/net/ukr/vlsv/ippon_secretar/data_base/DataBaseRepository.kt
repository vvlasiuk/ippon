package net.ukr.vlsv.ippon_secretar.data_base

import kotlinx.coroutines.CoroutineScope
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.table.DeskTable
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.runBlocking
import net.ukr.vlsv.ippon_secretar.data_base.daos.DeskTableDao
import net.ukr.vlsv.ippon_secretar.data_base.daos.JudgesNumberTableDao
import net.ukr.vlsv.ippon_secretar.data_base.table.JudgesNumberTable

@OpenClass
@Singleton
class DataBaseRepository @Inject constructor(private val apiClient: ApiClient,
                                             private val dispatchersProvider: CoroutineDispatchersProvider,
                                             private val spCache: SPCache,
                                             private val deskTableDao: DeskTableDao,
                                             private val judgesNumberTableDao: JudgesNumberTableDao
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchersProvider.IO

    // DESK +++
    var desk_ID
        get() = spCache.desk_ID
        set(value) {spCache.desk_ID = value}

    fun insertDesk(table: DeskTable) {deskTableDao.insert(table)}

    fun updateDesk(table: DeskTable) {deskTableDao.update(table)}

    fun getListDesk(): MutableList<DeskTable> = runBlocking (coroutineContext){deskTableDao.getList()}
    // DESK ---

    // Judges Number +++
    var judgesNumber_ID
        get() = spCache.judgesNumber_ID
        set(value) {spCache.judgesNumber_ID = value}

    fun insertJudgesNumber(table: JudgesNumberTable) {judgesNumberTableDao.insert(table)}

    fun updateJudgesNumber(table: JudgesNumberTable) {judgesNumberTableDao.update(table)}

    fun getListJudgesNumber(): MutableList<JudgesNumberTable> = runBlocking (coroutineContext){judgesNumberTableDao.getList()}
    // Judges Number ---
}