package com.bedirhandag.arabamcomandroidsample.ui.view.cardetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.api.ApiClient
import com.bedirhandag.arabamcomandroidsample.api.ApiService
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarDetailsBinding
import com.bedirhandag.arabamcomandroidsample.model.cardetail.CarDetailResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityCarDetailsBinding
    private lateinit var viewModel: CarDetailsViewModel
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        setupViewModel()
        initObservers()
        getArgs()
    }

    private fun getArgs() {
        intent?.getIntExtra("id", 0)?.let {
            viewbinding.textView.text = it.toString()
            viewModel.carIdLiveData.value = it
        }
    }

    private fun setupViewBinding() {
        viewbinding = ActivityCarDetailsBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarDetailsViewModel::class.java)
    }

    fun carDetailsRequest(id: Int) {
        apiService = ApiClient.getClient().create(ApiService::class.java)
        val carDetailRequest = apiService.getDetails(id)

        carDetailRequest.enqueue(object : Callback<CarDetailResponseModel> {
            override fun onResponse(
                call: Call<CarDetailResponseModel>,
                response: Response<CarDetailResponseModel>
            ) {
                response.body()?.let {
                    Toast.makeText(this@CarDetailsActivity, it.modelName, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CarDetailResponseModel>, t: Throwable) {
                Log.e("bedirhan", t.message.toString(), t)
            }

        })
    }

    private fun initObservers() {
        viewModel.apply {
            carIdLiveData.observe(this@CarDetailsActivity, {
                carDetailsRequest(it)
            })
        }
    }
}