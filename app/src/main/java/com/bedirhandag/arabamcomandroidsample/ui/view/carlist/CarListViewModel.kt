package com.bedirhandag.arabamcomandroidsample.ui.view.carlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel

class CarListViewModel : ViewModel() {
    val carListLiveData = MutableLiveData<CarListResponseModel>()

}