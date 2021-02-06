package com.bedirhandag.arabamcomandroidsample.ui.view.carlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarListBinding
import com.bedirhandag.arabamcomandroidsample.ui.adapter.CarListAdapter
import com.bedirhandag.arabamcomandroidsample.ui.view.cardetail.CarDetailsActivity
import com.bedirhandag.arabamcomandroidsample.util.ItemClickListener
import kotlinx.android.synthetic.main.activity_car_list.*

class CarListActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityCarListBinding
    private lateinit var viewModel: CarListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupViewBinding()
        initAdapter()

    }

    private fun initAdapter() {
        CarListAdapter(viewModel.testStringList, object : ItemClickListener {
            override fun onItemClick(item: String) {
                navigateToCarDetails(item)
            }
        }).also { _adapter ->
            recyclerView.adapter = _adapter
        }
    }

    private fun setupViewBinding() {
        viewbinding = ActivityCarListBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarListViewModel::class.java)
    }

    private fun navigateToCarDetails(item: String) {
        Intent(this@CarListActivity, CarDetailsActivity::class.java).apply {
            putExtra("data", item)
        }.also {
            startActivity(it)
        }
    }
}