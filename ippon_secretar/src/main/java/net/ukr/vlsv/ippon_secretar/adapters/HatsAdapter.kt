package net.ukr.vlsv.ippon_secretar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hats_adapter_item.view.*
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable
import kotlinx.android.synthetic.main.hats_adapter_item.view.*
import net.ukr.vlsv.ippon_secretar.R


class HatsAdapter(var mData: MutableList<HatsTable>): RecyclerView.Adapter<HatsAdapter.ViewHolder>() {
    var selectedPosition = 0

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var mDescription: TextView

        init {
            mDescription = v.findViewById(R.id.hats_adapter_item_description) as TextView

            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            notifyItemChanged(adapterPosition)
            notifyItemChanged(selectedPosition)

            selectedPosition = adapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.hats_adapter_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemHats = mData[position]

        holder.mDescription.setText(itemHats.description)

        if (position != RecyclerView.NO_POSITION) {
            if (selectedPosition == position)
                holder.itemView.setBackgroundColor(Color.LTGRAY)
            else
                holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = mData.size

}