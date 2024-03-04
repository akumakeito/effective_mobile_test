package ru.akumakeito.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.akumakeito.effectivemobile_test.R
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo

class ProductInfoItemAdapter(
    @ApplicationContext private val context: Context,
    private val list: List<ProductInfo>
) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view: View? = convertView


        if (convertView == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.product_info_item, parent, false)

            val currentProductInfo = getItem(position) as ProductInfo
            val textViewProductInfoTitle = view?.findViewById(R.id.info_title) as TextView
            val textViewProductInfoValue = view?.findViewById(R.id.info_value) as TextView

            textViewProductInfoTitle.text = currentProductInfo.title
            textViewProductInfoValue.text = currentProductInfo.value


        }
        return view

    }
}
