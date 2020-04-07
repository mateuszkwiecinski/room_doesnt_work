package pls.help.myapplication

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class BlockingTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun instantRunBlocking() = runBlocking {
        dao.insertTransaction()

        val all = dao.getAll().value
        assertEquals(FirstEntity(1), all?.single())
    }

    @Test
    fun instantRunBlockintTest() = runBlockingTest {
        dao.insertTransaction()
    }
}
