package co.com.test.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserDataModel {
    @SerializedName("errorCode")
    var errorCode: String? = null

    @SerializedName("errorMessage")
    var errorMessage: String? = null

    @SerializedName("user")
    var user: UserModel? = null

    @Entity(tableName = "user")
    class UserModel {
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int = 1

        @SerializedName("userId")
        @ColumnInfo(name = "user_id")
        var userId: String? = null

        @SerializedName("userName")
        @ColumnInfo(name = "user_name")
        var userName: String? = null
    }


}