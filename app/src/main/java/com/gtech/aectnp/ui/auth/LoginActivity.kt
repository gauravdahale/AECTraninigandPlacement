package com.gtech.aectnp.ui.auth

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.messaging.FirebaseMessaging
import com.gtech.aectnp.MainActivity
import java.lang.Exception
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken as ForceResendingToken1
import com.gtech.aectnp.R
import com.gtech.aectnp.databinding.ActivityLoginBinding
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mResendToken1: ForceResendingToken1? = null
    private var mVerificationId: String? = null
    private var mDatabaseReference: DatabaseReference? = null
    private lateinit var mobile: String
    private lateinit var name: String
    private lateinit var dob: String
    private lateinit var currentsem: String
    private lateinit var email: String
    private lateinit var branch: String
    lateinit var binding: ActivityLoginBinding
    private val sharedprefFile = "shop_selected"
    val mDatabase =
        FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
       if(mAuth!!.currentUser?.uid !=null){
           val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       }
        mDatabaseReference = mDatabase.reference
        val branchlist = arrayOf("IT", "CSE", "MECH", "CHEM", "Computer","EXTC")
        val semlist = arrayOf("Sem I", "Sem II", "Sem III", "Sem IV", "Sem V", "Sem VI", "Sem VII", "Sem VIII")
        val semadapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, semlist)

        val branchadapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branchlist)
        binding.branch.setAdapter(branchadapter)
        binding.sem.setAdapter(semadapter)
        binding.authDob.editText?.setOnClickListener {
            val cal = Calendar.getInstance()

            val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                binding.authDob.editText?.setText(sdf.format(cal.time))


            }
            DatePickerDialog(
                this,
                date,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        binding.submitNumber.setOnClickListener {
            when {
                !binding.authMobile.editText?.text.isNullOrEmpty() && binding.authMobile.editText?.text!!.length >= 10 &&
                        !binding.authName.editText?.text.toString().isNullOrBlank() &&
                        !binding.authBranch.editText?.text.toString().isNullOrBlank() &&
                        !binding.authDob.editText?.text.toString().isNullOrBlank() &&
                        !binding.authCurrentSem.editText?.text.toString().isNullOrBlank() &&
                        !binding.authEmail.editText?.text.toString().isNullOrBlank() -> {
                    name = binding.authName.editText?.text.toString()
                    mobile = binding.authMobile.editText?.text.toString()
                    dob = binding.authDob.editText?.text.toString()
                    email = binding.authEmail.editText?.text.toString()
                    currentsem = binding.authCurrentSem.editText?.text.toString()
                    branch = binding.branch.text.toString()
                                    sendVerificationCode(mobile)
                    binding.step1.visibility = View.GONE
                    binding.step2.visibility = View.VISIBLE
                }
                binding.authName.editText?.text.toString().isNullOrBlank() -> {
                    Toast.makeText(this, "Please Enter your name", Toast.LENGTH_SHORT).show(); }
                binding.authName.editText?.text.toString().isNullOrBlank() -> {
                    Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show()
                }
                binding.authName.editText?.text.toString().isNullOrBlank() -> {
                    Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show()
                }
                binding.authName.editText?.text.toString().isNullOrBlank() -> {
                    Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show()
                }
                binding.authName.editText?.text.toString().isNullOrBlank() -> {
                    Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.sumbitOtp.setOnClickListener {

                val code = binding.otptext.text.toString().trim { it <= ' ' }
                if (code.isEmpty() || code.length < 6) {
                    binding.editTextCode?.error = "Enter valid code"
                    binding.editTextCode?.requestFocus()
                    return@setOnClickListener
                }
                //verifying the code entered manually
                try {
                    verifyVerificationCode(code)
                } catch (e: Exception) {
                    Log.d("submitotp", "${e.message}")
                }

            }


            binding.resendotp.setOnClickListener { resendVerificationCode(mobile, mResendToken1) }


        }

        //the method is sending verification code
//the country id is concatenated
//you can take the country id as user input as well
        private fun sendVerificationCode(mobile: String?) {
            val options = PhoneAuthOptions.newBuilder(mAuth!!)
                .setPhoneNumber("+91$mobile")
                .setTimeout(30L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
            PhoneAuthProvider.verifyPhoneNumber(options)

        }

        private fun resendVerificationCode(
            mobile: String?,
            mResendToken: ForceResendingToken1?
        ) {

            val options = PhoneAuthOptions.newBuilder(mAuth!!)
                .setPhoneNumber("+91$mobile")
                .setTimeout(30L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(mResendToken!!)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
//        getInstance().verifyPhoneNumber(
//            "+91$mobile",  // Phone number to verify
//            30,  // Timeout duration
//            TimeUnit.SECONDS,  // Unit of timeout
//            this,  // Activity (for callback binding)
//            mCallbacks,  // OnVerificationStateChangedCallbacks
//            mResendToken
//        ) // ForceResendingToken from callbacks
            Toast.makeText(this, "OTP has been sent again", Toast.LENGTH_LONG).show()
        }

//the callback to detect the verification status
        private val mCallbacks: OnVerificationStateChangedCallbacks =
            object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) { //Getting the code sent by SMS
                    val code = phoneAuthCredential.smsCode
                    //sometime the code is not detected automatically
//in this case the code will be null
//so user has to manually enter the code
                    if (code != null) {
                        binding.otptext!!.setText(code)
                        //verifying the code
                        verifyVerificationCode(code)
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken1) {
                    super.onCodeSent(s, forceResendingToken)
                    //storing the verification id that is sent to the user
                    mVerificationId = s
                    mResendToken1 = forceResendingToken

                }
            }

        private fun verifyVerificationCode(code: String) { //creating the credential
//signing the user
            try {
                val credential = getCredential((mVerificationId)!!, code)
                signInWithPhoneAuthCredential(credential)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "verifyVerificationCode" + e.localizedMessage)
                // startActivity( new Intent(VerifyPhoneActivity.this,DrawerActivity.class));
                finish()
            }
        }

        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { //verification successful we will start the profile activity
                        //User Data Feeding to database.
//                    val tags = JSONObject()
//                    try {
//                        tags.put("Id", token)
//                        tags.put("Name", name)
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                    OneSignal.sendTags(tags)
                        val datetime =
                            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
                        NewUser(
                            mobile.trim { it <= ' ' }, name,
                            dob,
                            email,
                            currentsem,
                            branch
                        )
                        val intent = Intent(this, MainActivity::class.java)
                        Toast.makeText(applicationContext, "Login Successfull", Toast.LENGTH_SHORT)
                            .show()
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    } else { //verification unsuccessful.. display an error message
                        var message = "Something is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        val snackbar =
                            Snackbar.make(
                                findViewById(R.id.auth_layout),
                                (message),
                                Snackbar.LENGTH_LONG
                            )
                        snackbar.setAction("Dismiss") { }
                        snackbar.show()
                    }
                }
        }

        private fun NewUser(
            newuserNumber: String,
            newusername: String,
            dob: String?,
            email: String,
            sem: String,
            branch: String
        ) { //Creating a movie object with user defined variables
            val user = UserModel()
//        user.userOccupation = newuserOccupation
//        user.userNumber = newuserNumber
            user.number = newuserNumber
            user.timestamp = ServerValue.TIMESTAMP
            user.dob = dob
            user.email = email
            user.branch = branch
            user.currentsem = sem
//        user.token = newtoken
            user.name = newusername
            //referring to movies node and setting the values from movie object to that location
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                user.usertoken = token
                mDatabaseReference!!.child("users").child(mAuth!!.currentUser!!.uid)
                    .child("usertoken")
                    .setValue(token)

                // Log and toast
                val msg = "Generated Token: $token"
                Log.d(TAG, msg.toString())
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })


            val map = HashMap<String, Any>()
            map["name"] = user.name!!
            map["email"] = user.email!!
            map["number"] = user.number!!
            map["dob"] = user.dob!!
            map["currentsem"] = user.currentsem!!
            map["branch"] = user.branch!!
            map["timestamp"] = ServerValue.TIMESTAMP
//        mDatabaseReference!!.child("users").push().setValue(user)
            mDatabaseReference!!.child("users").child(mAuth!!.currentUser!!.uid).updateChildren(map)
          getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putString("branch",branch).apply()
            val mAnalytics = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString("NewUser", newuserNumber)
            mAnalytics.logEvent("NewUsers", bundle)
            // Get current version code
//        val username = newuserName.trim { it <= ' ' }
            val userphone = newuserNumber.trim { it <= ' ' }
            // Get saved version code
            val prefs = getSharedPreferences(sharedprefFile, Context.MODE_PRIVATE)
            val editor = prefs.edit()
//        editor.putString("USER_NAME", username)
            editor.putString("USER_NUMBER", userphone)
            editor.putString("USER_NAME", newusername)
            editor.putString("CURRENT_SEM", currentsem)
            editor.putString("BRANCH", branch)
            editor.putString("DOB", dob)
            editor.apply()

        }

        companion object {
            private val TAG = "VerifyPhoneActivity"
        }
    }
