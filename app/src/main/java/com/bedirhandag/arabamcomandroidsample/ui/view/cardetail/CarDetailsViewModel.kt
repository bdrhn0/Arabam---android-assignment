package com.bedirhandag.arabamcomandroidsample.ui.view.cardetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bedirhandag.arabamcomandroidsample.model.cardetail.CarDetailResponseModel

class CarDetailsViewModel : ViewModel() {
    val carIdLiveData = MutableLiveData<Int>()
    val carDetailModelLiveData = MutableLiveData<CarDetailResponseModel>()
}