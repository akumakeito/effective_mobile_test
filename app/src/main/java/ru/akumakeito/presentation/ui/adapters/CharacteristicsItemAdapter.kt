package ru.akumakeito.presentation.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo

class CharacteristicsItemAdapter(
    @ApplicationContext private val context: Context,
    private val list: List<ProductInfo>
) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder


        if (convertView == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.characteristics_item, parent, false)

            viewHolder =
                ViewHolder(view.findViewById(R.id.info_title), view.findViewById(R.id.info_value))
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        Log.d("rvchar", "размер ${list.size} позиция ${position}")

        viewHolder.viewTitle.text = (getItem(position) as ProductInfo).title
        viewHolder.viewValue.text = (getItem(position) as ProductInfo).value

        return view

    }

    class ViewHolder(val viewTitle: TextView, val viewValue: TextView)

}