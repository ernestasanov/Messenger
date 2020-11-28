package com.learning.messenger.ui.home

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.messenger.R
import com.learning.messenger.api.ApiRepository
import com.learning.messenger.ui.messages.MessagesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MessagesFragment : Fragment() {

    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var messagesViewModel: MessagesViewModel

    private val args: MessagesFragmentArgs by navArgs()
    private val disposables = CompositeDisposable()

    private lateinit var messagesView: RecyclerView
    private lateinit var replyButton: ImageView
    private lateinit var replyEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        messagesViewModel =
            ViewModelProvider(this).get(MessagesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages, container, false)
        messagesView = root.findViewById(R.id.messages)
        messagesView.layoutManager = LinearLayoutManager(context)
        val userId = args.userId
        messagesAdapter = MessagesAdapter(userId)
        messagesView.adapter = messagesAdapter

        val personId = args.personId
        loadMessages(userId, personId)

        replyButton = root.findViewById(R.id.replyButton)
        replyButton.setOnClickListener {
            val disposable = ApiRepository.api.sendMessage(userId, personId, replyEditText.text.toString(), null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->
                    if (success) {
                        loadMessages(userId, personId)
                    }
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                })
            disposables.add(disposable)
        }
        replyEditText = root.findViewById(R.id.replyEditText)
        scheduleMessages(userId, personId)
        return root
    }

    private fun scheduleMessages(userId: Int, personId: Int) {
        replyButton.postDelayed({
            loadMessages(userId, personId)
            scheduleMessages(userId, personId)
        }, 10000
        )
    }

    private fun loadMessages(userId: Int, personId: Int) {
        val disposable = ApiRepository.api.getMessages(userId, personId, 100)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ messages ->
                messagesAdapter.messages = messages
                messagesAdapter.notifyDataSetChanged()
            }, { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            })
        disposables.add(disposable)
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