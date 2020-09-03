package co.com.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.com.test.db.UserDatabase
import co.com.test.model.UserDataModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @desc this is a use for manage query error and success
 * @author : Mahesh Vayak
 * @required
 **/

open class UserDatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private var dataBaseInstance: UserDatabase?= null
    private val compositeDisposable = CompositeDisposable()
    val insertLiveData = MutableLiveData<Boolean>()


    /**
     * @desc this method is use for insert data in database
     * @param data - pass user data model
     **/
    fun insert(data: UserDataModel.UserModel){
        dataBaseInstance?.userDao()?.insert(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                insertLiveData.postValue(true)
            },{
                insertLiveData.postValue(false)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }


    /**
     * @desc this method is use for create database instance
     * @param dataBaseInstance - pass database object
     **/
    fun setInstanceOfDb(dataBaseInstance: UserDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

}