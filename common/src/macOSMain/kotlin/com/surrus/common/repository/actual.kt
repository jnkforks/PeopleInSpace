package com.surrus.common.repository

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

actual fun createDb(): PeopleInSpaceDatabase? {
    val driver = NativeSqliteDriver(PeopleInSpaceDatabase.Schema, "peopleinspace.db")
    return PeopleInSpaceDatabase(driver)
}

//actual fun ktorScope(block: suspend () -> Unit) = kotlinx.coroutines.runBlocking { block() }

actual fun ktorScope(block: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) { block() }
}
