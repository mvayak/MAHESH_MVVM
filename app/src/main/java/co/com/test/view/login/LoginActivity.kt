package co.com.test.view.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import co.com.test.R
import co.com.test.databinding.ActivityLoginBinding
import co.com.test.db.UserDatabase
import co.com.test.viewmodel.UserDatabaseViewModel
import co.com.test.extention.isOnline
import co.com.test.extention.makeSnackBar
import co.com.test.model.LoginForm
import co.com.test.view.BaseActivity
import co.com.test.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @desc this is class will show list of IDs
 * @author : Mahesh Vayak
 * @required
 **/
class LoginActivity : BaseActivity() {
    private val mViewModel: LoginViewModel by viewModel()
    private val database: UserDatabaseViewModel by inject()
    private lateinit var binding: ActivityLoginBinding
    private var loginForm = LoginForm()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginForm = loginForm
        binding.viewModel = mViewModel
        database.setInstanceOfDb(UserDatabase.getDatabaseInstance(this))
        listenToViewModel()
    }


    /**
     * @desc this method will use for manage API success and failure,Internet connectivity, make json object for API and un authorization.
     */
    private fun listenToViewModel() {
        mViewModel.validationLiveData.observe(this, Observer {
            when (it) {
                1 -> {
                    binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.email_validation_msg))
                }
                else -> {
                    binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.password_validation_msg))
                }
            }
        })

        mViewModel.isLoginFormValid.observe(this, Observer {
            mViewModel.makeObjectForLogin(loginForm)
        })

        mViewModel.buildJsonObjectForLogin.observe(this, Observer {
            launchProgressDialog()
            mViewModel.userLogin(it)
        })

        mViewModel.successUserResponse.observe(this, Observer {
            dismissProgressDialog()
            it.user?.apply {
                database.insert(this)
            }
        })

        database.insertLiveData.observe(this, Observer {
            if(it){
                binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.login_success_mess))
            }else{
                binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.something_went_wrong))
            }
        })

        mViewModel.errorUserResponse.observe(this, Observer {
            dismissProgressDialog()
            binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.something_went_wrong))
        })

        mViewModel.noInternetException.observe(this, Observer {
            dismissProgressDialog()
            if (isOnline()) {
                binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.something_went_wrong))
            } else {
                binding.nestedScrollViewSignIn.makeSnackBar(resources.getString(R.string.no_internet_connection))
            }
        })
    }


    /**
     * @desc stop apis call and clear view model
     */
    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDetach()
    }
}