package pls.help.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class TestCoroutineDispatcherTest {

    val dispatcher = TestCoroutineDispatcher()

    lateinit var db: SampleDatabase
    lateinit var dao: SampleDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            SampleDatabase::class.java
        )
            .setQueryExecutor(dispatcher.asExecutor())
            .setTransactionExecutor(dispatcher.asExecutor())
            .build()
        dao = db.dao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun withTestDispatcher() = dispatcher.runBlockingTest {
        dao.insertTransaction()

        val all = dao.getAll().value
        assertEquals(FirstEntity(1), all?.single())
    }
}
