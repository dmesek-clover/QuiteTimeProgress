package com.example.customprogressbar.remainingQuiteTime.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser


class EndQuiteTimeDialog(
        private val userList: List<QuiteTimeUser>
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.end_quite_time_dialog, null))
            // Add action buttons
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onStart() {
        super.onStart()

        dialog.findViewById<RecyclerView>(R.id.rv_userList).apply {
            layoutManager = LinearLayoutManager(dialog.context)
            adapter = EndQuiteTimeAdapter(userList)
        }
        dialog.findViewById<ImageButton>(R.id.btn_close).apply {
            setOnClickListener { dialog.dismiss() }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_rectangle_gray)

    }

}