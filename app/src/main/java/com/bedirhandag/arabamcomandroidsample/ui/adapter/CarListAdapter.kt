package com.bedirhandag.arabamcomandroidsample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandag.arabamcomandroidsample.R
import com.bedirhandag.arabamcomandroidsample.databinding.RecyclerRowBinding
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListModel
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_DEFAULT_PHOTO_SIZE
import com.bedirhandag.arabamcomandroidsample.util.ItemClickListener
import com.bedirhandag.arabamcomandroidsample.util.loadImage
import com.bumptech.glide.Glide

class CarListAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CarListAdapter.CarListVH>() {

    private var lastPosition = -1
    private val carList = CarListResponseModel()

    class CarListVH(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarListVH(binding)
    }

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        setAnimation(holder.itemView, position)
        with(holder.binding) {
            carList[position].also { _data ->
                title.text = _data.title
                description.text = _data.modelName
                price.text = _data.priceFormatted
                _data.photo?.replace("{0}", KEY_DEFAULT_PHOTO_SIZE)?.let { _photoUrl ->
                    photo.loadImage(_photoUrl)
                }

                root.setOnClickListener {
                    itemClickListener.onItemClick(_data.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    fun setCarList(newCarList: CarListResponseModel) {
        val beforeSize = carList.size
        carList.addAll(newCarList)
        notifyItemRangeInserted(beforeSize, newCarList.size)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left).apply {
                duration = 350
            }.also { _anim ->
                viewToAnimate.startAnimation(_anim)
                lastPosition = position
            }
        }
    }
}