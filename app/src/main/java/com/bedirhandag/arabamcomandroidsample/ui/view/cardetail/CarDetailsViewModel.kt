package com.bedirhandag.arabamcomandroidsample.ui.view.cardetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarDetailsViewModel : ViewModel() {
    val carIdLiveData = MutableLiveData<Int>()
}