package pls.help.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [FirstEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun dao(): SampleDao
}

@Dao
interface SampleDao {

    @Insert
    suspend fun insert1(first: FirstEntity)

    @Transaction
    suspend fun insertTransaction() {
        insert1(FirstEntity(1))
    }

    @Query("SELECT * FROM firstentity")
    fun getAll(): LiveData<List<FirstEntity>>
}

@Entity
data class FirstEntity(
    @PrimaryKey val id: Int
)
