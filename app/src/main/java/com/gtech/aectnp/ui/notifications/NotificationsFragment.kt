package com.gtech.aectnp.ui.notifications

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gtech.aectnp.R
import com.gtech.aectnp.ui.notifications.placeholder.NotificationModel

class NotificationsFragment : Fragment() {
    val branches = ArrayList<String>()
    val list = ArrayList<NotificationModel>()
    private var columnCount = 1
    val mAdapter = MyItemRecyclerViewAdapter(list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = mAdapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMessages()
    }

    fun getMessages() {
        FirebaseDatabase.getInstance().reference.child("branches")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        branches.add(it.getValue(String::class.java)!!)
                    }
                    branches.forEach {
                        getNotifications(it) } }
                override fun onCancelled(error: DatabaseError) {
                } }) }

    fun getNotifications(branch: String) {
        FirebaseDatabase.getInstance().reference.child(branch)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        list.add(child.getValue(NotificationModel::class.java)!!)
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            NotificationsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}