package co.com.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.test.model.UserDataModel

/**
 * @desc this is a database calls and this call create database
 * @author : Mahesh Vayak
 * @required
 **/

@Database(entities = [UserDataModel.UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: UserDatabase? = null

        fun getDatabaseInstance(mContext: Context): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabaseInstance(mContext).also {
                    INSTANCE = it
                }
            }


        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, UserDatabase::class.java, "interview_user")
                .fallbackToDestructiveMigration()
                .build()

    }

}