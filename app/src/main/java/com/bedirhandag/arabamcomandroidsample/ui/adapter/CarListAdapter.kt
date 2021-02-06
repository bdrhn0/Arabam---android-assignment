package com.bedirhandag.arabamcomandroidsample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandag.arabamcomandroidsample.databinding.RecyclerRowBinding
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel
import com.bedirhandag.arabamcomandroidsample.util.ItemClickListener

class CarListAdapter(
    val carList: CarListResponseModel,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CarListAdapter.CarListVH>() {

    class CarListVH(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarListVH(binding)
    }

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        with(holder) {
            binding.recTextView.text = carList[position].modelName
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(carList[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }
}