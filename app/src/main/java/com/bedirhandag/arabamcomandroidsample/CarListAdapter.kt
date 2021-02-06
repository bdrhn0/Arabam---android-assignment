package com.bedirhandag.arabamcomandroidsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandag.arabamcomandroidsample.databinding.RecyclerRowBinding

class CarListAdapter(val testStringListe : ArrayList<String>,private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<CarListAdapter.CarListVH>() {

    class CarListVH(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CarListVH(binding)
    }

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        with(holder){
            binding.recTextView.text = testStringListe[position]
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(testStringListe[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return testStringListe.size
    }
}