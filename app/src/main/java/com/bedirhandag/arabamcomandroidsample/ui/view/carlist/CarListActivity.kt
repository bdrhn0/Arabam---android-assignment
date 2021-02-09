package com.bedirhandag.arabamcomandroidsample.ui.view.carlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedirhandag.arabamcomandroidsample.api.ApiClient
import com.bedirhandag.arabamcomandroidsample.api.ApiService
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarListBinding
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel
import com.bedirhandag.arabamcomandroidsample.ui.adapter.CarListAdapter
import com.bedirhandag.arabamcomandroidsample.ui.view.cardetail.CarDetailsActivity
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_ID
import com.bedirhandag.arabamcomandroidsample.util.EndlessScrollListener
import com.bedirhandag.arabamcomandroidsample.util.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarListActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityCarListBinding
    private lateinit var viewModel: CarListViewModel
    lateinit var carListAdapter: CarListAdapter
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupViewBinding()
        initObservers()
        initAdapter()
        carListRequest(FIRST_PAGE)
    }

    private fun initObservers() {
        viewModel.apply {
            carListLiveData.observe(this@CarListActivity, {
                carListAdapter.setCarList(it)
            })
        }
    }

    private fun initAdapter() {
        CarListAdapter(object : ItemClickListener {
            override fun onItemClick(id: Int?) {
                id?.let {
                    navigateToCarDetails(id)
                }
            }
        }).also { _adapter ->
            carListAdapter = _adapter
            viewbinding.recyclerView.apply {
                adapter = carListAdapter
                addOnScrollListener(object :
                    EndlessScrollListener(layoutManager as LinearLayoutManager) {
                    override fun onLoadMore(page: Int) {
                        carListRequest(page)
                    }
                })
            }

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
            putExtra(KEY_ID, id)
        }.also {
            startActivity(it)
        }
    }

    fun carListRequest(page: Int) {
        apiService = ApiClient.getClient().create(ApiService::class.java)
        val carRequest = apiService.getCarList(page, 0, 10)

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
                Log.e(this@CarListActivity.javaClass.simpleName, t.message.toString(), t)
            }

        })
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}