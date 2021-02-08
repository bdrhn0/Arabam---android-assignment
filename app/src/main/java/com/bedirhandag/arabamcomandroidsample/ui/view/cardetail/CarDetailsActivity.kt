package com.bedirhandag.arabamcomandroidsample.ui.view.cardetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.core.text.htmlEncode
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.R
import com.bedirhandag.arabamcomandroidsample.api.ApiClient
import com.bedirhandag.arabamcomandroidsample.api.ApiService
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarDetailsBinding
import com.bedirhandag.arabamcomandroidsample.model.cardetail.CarDetailResponseModel
import com.bedirhandag.arabamcomandroidsample.ui.view.photodetail.PhotoDetailActivity
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_COLOR
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_DEFAULT_PHOTO_SIZE
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_FUEL
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_GEAR
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_ID
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_KM
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_PHOTO
import com.bedirhandag.arabamcomandroidsample.util.loadImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityCarDetailsBinding
    private lateinit var viewModel: CarDetailsViewModel
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        setupViewModel()
        initObservers()
        initListener()
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
                _data.text?.let { _desc ->
                    description.text = HtmlCompat.fromHtml(_desc, HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                location.text = getString(
                    R.string.placeholder_city_name,
                    _data.location?.cityName,
                    _data.location?.townName
                )
                userInfo.text = _data.userInfo?.nameSurname
                _data.properties?.find { it.name == KEY_COLOR }?.let {
                    color.text =
                        if (!it.value.isNullOrEmpty()) it.value
                        else "-"
                }
                _data.properties?.find { it.name == KEY_FUEL }?.let {
                    fuel.text =
                        if (!it.value.isNullOrEmpty()) it.value
                        else "-"
                }

                _data.properties?.find { it.name == KEY_GEAR }?.let {
                    gear.text =
                        if (!it.value.isNullOrEmpty()) it.value
                        else "-"
                }
                _data.properties?.find { it.name == KEY_KM }?.let {
                    km.text =
                        if (!it.value.isNullOrEmpty()) it.value
                        else "-"
                }
                _data.photos?.get(0)?.replace("{0}", KEY_DEFAULT_PHOTO_SIZE)?.let { _photoUrl ->
                    imageViewDetails.loadImage(_photoUrl)
                }
            }
        }
    }

    private fun navigateToPhotoDetailPage() {
        viewModel.carDetailModelLiveData.value?.photos?.get(0)
            ?.replace("{0}", KEY_DEFAULT_PHOTO_SIZE)?.let { _photoUrl ->
                Intent(this, PhotoDetailActivity::class.java).apply {
                    putExtra(KEY_PHOTO, _photoUrl)
                }.also { _intent ->
                    startActivity(_intent)
                }
            }

    }

    private fun initListener() {
        viewbinding.imageViewDetails.setOnClickListener {
            navigateToPhotoDetailPage()
        }
    }
}