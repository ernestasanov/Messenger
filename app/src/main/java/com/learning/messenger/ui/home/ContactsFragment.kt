package com.learning.messenger.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.learning.messenger.R
import com.learning.messenger.api.ApiRepository
import com.learning.messenger.ui.contacts.PersonAdapter
import com.learning.messenger.ui.home.LoginFragment.Companion.USER_ID_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel

    private val args: ContactsFragmentArgs by navArgs()
    private val disposables = CompositeDisposable()

    private lateinit var contactsListView: RecyclerView
    private lateinit var logoutButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsViewModel =
            ViewModelProvider(this).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        contactsListView = root.findViewById(R.id.contactsList)
        contactsListView.layoutManager = LinearLayoutManager(context)

        val userId = args.userId
        val disposable = ApiRepository.api.getContacts(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ contacts ->
                contactsListView.adapter = PersonAdapter(contacts)
            }, { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            })
        disposables.add(disposable)
        logoutButton = root.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            requireActivity().getPreferences(Activity.MODE_PRIVATE).edit {
                remove(USER_ID_KEY)
            }
            val action = ContactsFragmentDirections.actionNavigationContactsToNavigationLogin()
            it.findNavController().navigate(action)
        }
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