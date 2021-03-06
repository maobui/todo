package com.bui.todoapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bui.todoapplication.databinding.ActivityMainBinding
import com.bui.todoapplication.viewmodel.MainViewModel
import com.bui.todoapplication.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private final val TAG: String = MainActivity::class.java.simpleName

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }


}