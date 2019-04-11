package net.ukr.vlsv.ippon_secretar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hats_adapter_item.view.*
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable
import kotlinx.android.synthetic.main.activity_list_hat_add.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.data_base.table.SitkaTable


class SitkaAdapter(var mData: MutableList<SitkaTable>): RecyclerView.Adapter<SitkaAdapter.ViewHolder>() {
    var selectedPosition = 0

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener {
        var mDescription1: TextView
        var mDescription2: TextView
        var mNumber:       TextView

        init {
            mDescription1 = v.findViewById(R.id.sitka_adapter_item_description1) as TextView
            mDescription2 = v.findViewById(R.id.sitka_adapter_item_description2) as TextView
            mNumber       = v.findViewById(R.id.sitka_adapter_item_number)       as TextView

            v.setOnClickListener(this)
            v.setOnLongClickListener(this)

        }

        override fun onClick(v: View?) {
                notifyItemChanged(adapterPosition)
                notifyItemChanged(selectedPosition)

                selectedPosition = adapterPosition
        }

        override fun onLongClick(v: View?): Boolean {
            var a=1
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sitka_adapter_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemSitka = mData[position]

        holder.mDescription1.setText(itemSitka.Description1)
        holder.mDescription2.setText(itemSitka.Description2)
        holder.mNumber.setText((position + 1).toString())

        if (position != RecyclerView.NO_POSITION) {
            if (selectedPosition == position)
                holder.itemView.setBackgroundColor(Color.LTGRAY)
             else
                holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = mData.size

}


