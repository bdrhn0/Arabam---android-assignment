package com.bedirhandag.arabamcomandroidsample.ui.view.photodetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bedirhandag.arabamcomandroidsample.databinding.ActivityPhotoDetailBinding
import com.bedirhandag.arabamcomandroidsample.util.Constant.KEY_PHOTO
import com.bedirhandag.arabamcomandroidsample.util.loadImage

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityPhotoDetailBinding
    private lateinit var viewModel: PhotoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        setupViewModel()
        initObservers()
        getArgs()
    }

    private fun setupViewBinding() {
        viewbinding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PhotoDetailViewModel::class.java)
    }

    private fun getArgs() {
        intent?.getStringExtra(KEY_PHOTO)?.let { _photoUrl ->
            viewModel.photoDetailLiveData.value = _photoUrl
        }
    }

    private fun initObservers() {
        viewModel.apply {
            photoDetailLiveData.observe(this@PhotoDetailActivity, {
                viewbinding.photo.loadImage(it)
            })
        }
    }
}