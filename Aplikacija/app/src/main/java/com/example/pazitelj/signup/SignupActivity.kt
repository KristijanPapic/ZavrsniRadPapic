package com.example.pazitelj.signup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pazitelj.FirstTime.FirstTimeLoginActivity
import com.example.pazitelj.MainActivity
import com.example.pazitelj.R
import com.example.pazitelj.databinding.ActivitySignupBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception


class SignupActivity : AppCompatActivity() {
    private lateinit var gsc: GoogleSignInClient
    private lateinit var binding: ActivitySignupBinding
    val model: SignupViewModel by viewModels()
    val currenUser: CurrentUserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()


         gsc = GoogleSignIn.getClient(this, gso)

        binding.googleBtn.setOnClickListener {
            signIn()
        }

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //signIn()
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account != null) run {
            if(!model.currentUserIsAuthenticated){

                    CoroutineScope(IO).launch{
                        Log.d("loginsecond","second")
                        authenticatedAndSetUser(account.idToken!!)
                        if(model.loginResponse.value == null){
                            signIn()
                        }
                        else{
                            if(model.loginResponse.value!!.IsNewUser == true){
                                navigateToFirstTime()
                            }
                            else{
                                navigateToMain()
                            }
                        }


                    }
            }
            else{
                if(model.loginResponse.value!!.IsNewUser == true){
                    navigateToFirstTime()
                }
                else{
                    navigateToMain()
                    }
                }
        }
        else{
            binding.googleLabel.visibility = View.VISIBLE
            binding.googleBtn.visibility = View.VISIBLE
            binding.spinner.visibility = View.GONE
        }
    }

    private fun signIn(){
        val signInIntent: Intent = gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000) run {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            val token = account.idToken
            Log.d("token",token!!)
            CoroutineScope(IO).launch {
                Log.d("loginfirst","first")
                authenticatedAndSetUser(token)
                updateUI(account)
            }

            try{
                //logedInUser.getUser(model.loginResponse.value!!.UserId)
                }
            catch (e: Exception){
                Log.d("maingetuserfail",e.toString())
            }

            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId",currenUser.user.value!!.Id)
        startActivity(intent)
    }
    private fun navigateToFirstTime(){
        val intent = Intent(this, FirstTimeLoginActivity::class.java)
        intent.putExtra("userId",currenUser.user.value!!.Id)
        startActivity(intent)
    }

    private suspend fun authenticatedAndSetUser(token: String){
        val id = model.authenticate(token)
        model.currentUserIsAuthenticated=true
        setUser(id)


    }

    private suspend fun setUser(id: String){
        withContext(Main){
            currenUser.getUser(id)
            //Log.d("gottenuser",currenUser.user.value!!.Username)
        }

    }


    }
