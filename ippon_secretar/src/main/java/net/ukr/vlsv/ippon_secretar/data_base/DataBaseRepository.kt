package net.ukr.vlsv.ippon_secretar.data_base

import kotlinx.coroutines.CoroutineScope
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.runBlocking
import net.ukr.vlsv.ippon_secretar.data_base.daos.*
import net.ukr.vlsv.ippon_secretar.data_base.table.*

@OpenClass
@Singleton
class DataBaseRepository @Inject constructor(private val apiClient: ApiClient,
                                             private val dispatchersProvider: CoroutineDispatchersProvider,
                                             private val spCache: SPCache,
                                             private val deskTableDao: DeskTableDao,
                                             private val judgesNumberTableDao: JudgesNumberTableDao,
                                             private val competitionsDao: CompetitionsTableDao,
                                             private val hatsDao: HatsTableDao,
                                             private val sitkaDao: SitkaTableDao
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

    // Competitions Number +++
    var competitions_ID
        get() = spCache.competitions_ID
        set(value) {spCache.competitions_ID = value}

    fun insertCompetitions(table: CompetitionsTable) {competitionsDao.insert(table)}

    fun updateCompetitions(table: CompetitionsTable) {competitionsDao.update(table)}

    fun getListCompetitions(): MutableList<CompetitionsTable> = runBlocking (coroutineContext){competitionsDao.getList()}
    // Competitions Number ---

    // Hats +++
    var hats_ID
        get() = spCache.hats_ID
        set(value) {spCache.hats_ID = value}

    fun insertHats(table: HatsTable) {
        table.competitions_id = competitions_ID
        table.desk_id         = desk_ID

        hatsDao.insert(table)
    }


    fun updateHats(table: HatsTable) {hatsDao.update(table)}

    fun getListHats(): MutableList<HatsTable> = runBlocking (coroutineContext){hatsDao.getList(competitions_ID, desk_ID)}
    // Hats ---

    // Sitka +++
    fun getListSitka(): MutableList<SitkaTable> = runBlocking (coroutineContext){sitkaDao.getList(hats_ID)}

    fun insertListSitka(table: SitkaTable) {
//        table.competitions_id = competitions_ID
        table.hat_id   = hats_ID

        sitkaDao.insert(table)
    }

    // Sitka ---
}