package pls.help.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.apache.tools.ant.types.resources.First
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class AsyncTest {

    lateinit var db: SampleDatabase
    lateinit var dao: SampleDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            SampleDatabase::class.java
        ).build()
        dao = db.dao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `the only one working`() = runBlocking {
        dao.insertTransaction()

        val all = dao.getAll().value
        assertEquals(FirstEntity(1), all?.single())
    }

    @Test
    fun asyncRunBlockingTest() = runBlockingTest {
        dao.insertTransaction()
    }
}
