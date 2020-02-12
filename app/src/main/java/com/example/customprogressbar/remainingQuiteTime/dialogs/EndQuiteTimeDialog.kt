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


interface EndQuiteTimeDialogListener {
    fun removePressed(quiteTimeUser: QuiteTimeUser)
}

class EndQuiteTimeDialog(
        private val userList: List<QuiteTimeUser>,
        private val listener: EndQuiteTimeDialogListener
) : DialogFragment(), EndQuiteTimeDialogListener {

    override fun removePressed(quiteTimeUser: QuiteTimeUser) {
        listener.removePressed(quiteTimeUser)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.end_quite_time_dialog, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onStart() {
        super.onStart()

        dialog.findViewById<RecyclerView>(R.id.rv_userList).apply {
            layoutManager = LinearLayoutManager(dialog.context)
            adapter = EndQuiteTimeAdapter(userList, listener, Runnable { dialog.dismiss() })
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