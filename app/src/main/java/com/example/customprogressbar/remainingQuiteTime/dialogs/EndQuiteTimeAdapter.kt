package com.example.customprogressbar.remainingQuiteTime.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customprogressbar.R
import com.example.customprogressbar.remainingQuiteTime.models.QuiteTimeUser

class EndQuiteTimeAdapter(
        private val userList: List<QuiteTimeUser>,
        private val listener: QuiteTimeRemovedListener,
        private val dismissListener: Runnable

) : RecyclerView.Adapter<EndQuiteTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.quite_time_end_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            picture.setImageDrawable(userList[position].icon)
            name.text = userList[position].name
            stop.setOnClickListener {
                listener.removePressed(userList[position])
//                notifyDataSetChanged()
                if (userList.size == 1) {
                    dismissListener.run()
                } else {
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, userList.size)
                }
            }
            if (position == userList.size - 1) {
                separator.visibility = View.GONE
            } else {
                separator.visibility = View.VISIBLE
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val stop: ImageView = itemView.findViewById(R.id.btn_stop)
        val picture: ImageView = itemView.findViewById(R.id.iv_picture)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val separator: View = itemView.findViewById(R.id.separator)

    }


}