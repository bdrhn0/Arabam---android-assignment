package com.bedirhandag.arabamcomandroidsample.ui.view.cardetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.R
import com.bedirhandag.arabamcomandroidsample.api.ApiClient
import com.bedirhandag.arabamcomandroidsample.api.ApiService
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarDetailsBinding
import com.bedirhandag.arabamcomandroidsample.model.cardetail.CarDetailResponseModel
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_COLOR
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_DEFAULT_PHOTO_SIZE
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_GEAR
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_ID
import com.bedirhandag.arabamcomandroidsample.util.loadImage
import com.bumptech.glide.Glide
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
        intent?.getIntExtra(KEY_ID, 0)?.let {
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

    private fun carDetailsRequest(id: Int) {
        apiService = ApiClient.getClient().create(ApiService::class.java)
        val carDetailRequest = apiService.getDetails(id)

        carDetailRequest.enqueue(object : Callback<CarDetailResponseModel> {
            override fun onResponse(
                call: Call<CarDetailResponseModel>,
                response: Response<CarDetailResponseModel>
            ) {
                response.body()?.let {
                    viewModel.carDetailModelLiveData.value = it
                }
            }

            override fun onFailure(call: Call<CarDetailResponseModel>, t: Throwable) {
                Log.e(this@CarDetailsActivity.javaClass.simpleName, t.message.toString(), t)
            }

        })
    }

    private fun initObservers() {
        viewModel.apply {
            carIdLiveData.observe(this@CarDetailsActivity, {
                carDetailsRequest(it)
            })
            carDetailModelLiveData.observe(this@CarDetailsActivity, {
                initUi()
            })
        }
    }

    private fun initUi() {
        viewbinding.apply {
            viewModel.carDetailModelLiveData.value?.let { _data ->
                modelName.text = _data.modelName
                formattedPrice.text = _data.priceFormatted
                year.text = _data.dateFormatted
                location.text = _data.location?.cityName
                userInfo.text = _data.userInfo?.nameSurname
                _data.properties?.find { it.name == KEY_COLOR }?.let {
                    if(!it.value.isNullOrEmpty()) {
                        color.text = it.value
                    }
                }
                _data.properties?.find { it.name == KEY_GEAR }?.let {
                    if(!it.value.isNullOrEmpty()) {
                        gear.text = it.value
                    }
                }
                _data.photos?.get(0)?.replace("{0}", KEY_DEFAULT_PHOTO_SIZE)?.let {  _photoUrl ->
                    imageViewDetails.loadImage(_photoUrl)
                }
            }
        }
    }
}