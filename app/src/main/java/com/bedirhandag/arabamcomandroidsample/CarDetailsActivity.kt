package com.bedirhandag.arabamcomandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityCarDetailsBinding

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityCarDetailsBinding
    private lateinit var viewModel: CarDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewBinding()
        setupViewModel()
        getArgs()

    }

    private fun getArgs() {
        intent?.getStringExtra("data")?.let {
            viewbinding.textView.text = it
        }
    }

    private fun setupViewBinding() {
        viewbinding = ActivityCarDetailsBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarDetailsViewModel::class.java)
    }
}