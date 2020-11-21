package com.learning.messenger.ui.home

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.learning.messenger.R
import com.learning.messenger.api.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginFragment : Fragment() {
    companion object {
        const val USER_ID_KEY = "user_id"
    }

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val sharedPreferences = requireActivity().getPreferences(Activity.MODE_PRIVATE)
        val userId = sharedPreferences.getInt(USER_ID_KEY, 0)
        if (userId != 0) {
            root.postDelayed(
                {
                    val action = LoginFragmentDirections.actionLoginToContacts(userId)
                    root.findNavController().navigate(action)
                }, 500
            )
            return root
        }
        loginButton = root.findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = loginEditText.text.toString()
            val password = passwordEditText.text.toString()
            val disposable = ApiRepository.api.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userId ->
                    sharedPreferences.edit {
                        putInt(USER_ID_KEY, userId)
                    }
                    val action = LoginFragmentDirections.actionLoginToContacts(userId)
                    root.findNavController().navigate(action)
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                })
            disposables.add(disposable)
        }
        loginEditText = root.findViewById(R.id.editTextPersonName)
        passwordEditText = root.findViewById(R.id.editTextPassword)
        return root
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}