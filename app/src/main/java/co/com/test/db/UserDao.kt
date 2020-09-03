package co.com.test.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import co.com.test.model.UserDataModel
import io.reactivex.Completable

/**
 * @desc this is class use for write database query
 * @author : Mahesh Vayak
 * @required
 **/

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(insert: UserDataModel.UserModel?): Completable

}