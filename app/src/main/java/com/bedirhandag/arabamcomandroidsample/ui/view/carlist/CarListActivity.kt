package com.bedirhandag.arabamcomandroidsample.ui.view.carlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.api.ApiClient
import com.bedirhandag.arabamcomandroidsample.api.ApiService
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarListBinding
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel
import com.bedirhandag.arabamcomandroidsample.ui.adapter.CarListAdapter
import com.bedirhandag.arabamcomandroidsample.ui.view.cardetail.CarDetailsActivity
import com.bedirhandag.arabamcomandroidsample.util.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CarListActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityCarListBinding
    private lateinit var viewModel: CarListViewModel
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupViewBinding()
        initObservers()
        carListRequest()
    }

    private fun initObservers() {
        viewModel.apply {
            carListLiveData.observe(this@CarListActivity, {
                initAdapter(it)
            })
        }
    }

    private fun initAdapter(carListResponseModel: CarListResponseModel) {
        CarListAdapter(carListResponseModel, object : ItemClickListener {
            override fun onItemClick(id: Int?) {
                id?.let {
                    navigateToCarDetails(id)
                }
            }
        }).also { _adapter ->
            viewbinding.recyclerView.adapter = _adapter
        }
    }

    private fun setupViewBinding() {
        viewbinding = ActivityCarListBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarListViewModel::class.java)
    }

    private fun navigateToCarDetails(id: Int) {
        Intent(this@CarListActivity, CarDetailsActivity::class.java).apply {
            putExtra("id", id)
        }.also {
            startActivity(it)
        }
    }

    fun carListRequest() {
        apiService = ApiClient.getClient().create(apiService::class.java)
        val carRequest = apiService.getCarList(1, 0, 10)

        carRequest.enqueue(object : Callback<CarListResponseModel> {
            override fun onResponse(
                call: Call<CarListResponseModel>,
                response: Response<CarListResponseModel>
            ) {
                response.body()?.let {
                    viewModel.carListLiveData.value = it
                }
            }

            override fun onFailure(call: Call<CarListResponseModel>, t: Throwable) {
                Log.e("bedirhan", t.message.toString(), t)
            }

        })
    }
}