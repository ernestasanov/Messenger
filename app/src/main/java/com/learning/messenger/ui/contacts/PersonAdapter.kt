package com.learning.messenger.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.learning.messenger.R
import com.learning.messenger.data.Person

class PersonAdapter(
    private val personList: List<Person>,
    private val onPersonClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.person, parent, false)
        return PersonViewHolder(view as ConstraintLayout, onPersonClick)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}

class PersonViewHolder(
    personView: ConstraintLayout,
    private val onPersonClick: (Person) -> Unit
) : RecyclerView.ViewHolder(personView) {
    val personImageView: ImageView = personView.findViewById(R.id.personImage)
    private val personNameView: TextView = personView.findViewById(R.id.personName)
    private var person: Person? = null

    init {
        personView.setOnClickListener {
            person?.let {
                onPersonClick(it)
            }
        }
    }

    // PersonViewHolder(ConstraintLayout personView) {
    //     personView.setOnClickListener(...)
    //
    // }

    fun bind(person: Person) {
        this.person = person
        personNameView.text = "${person.firstName} ${person.lastName}"
    }

}
