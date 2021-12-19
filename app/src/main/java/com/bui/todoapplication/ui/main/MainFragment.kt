package com.bui.todoapplication.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bui.todoapplication.R
import com.bui.todoapplication.databinding.FragmentMainBinding
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import com.bui.todoapplication.ui.buy.ARG_PRODUCTS
import com.bui.todoapplication.ui.call.ARG_USERS
import com.bui.todoapplication.ui.sell.ARG_PRODUCTS_LOCAL
import com.bui.todoapplication.viewmodel.MainViewModel
import com.bui.todoapplication.viewmodel.ViewModelFactory
import com.bui.todoapplication.widget.LoadingDialog
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), View.OnClickListener {
    private final val TAG = MainFragment::class.java.simpleName

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(requireActivity().application)
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listener() {
        button_call_list.setOnClickListener(this)
        button_buy_list.setOnClickListener(this)
        button_sell_list.setOnClickListener(this)

        mainViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) context?.let { it1 -> LoadingDialog.showLoading(it1) } else LoadingDialog.hideLoading()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_call_list -> callList()
            R.id.button_buy_list -> buyList()
            R.id.button_sell_list -> sellList()
            else -> return
        }
    }

    private fun callList() {
        mainViewModel.callList().observe(this, Observer {
            Log.d(TAG, "List user $it")
            navigateToCall(it)
        })
    }

    private fun buyList() {
        mainViewModel.buyList().observe(this, Observer { it ->
            Log.d(TAG, "List product $it")
            mainViewModel.saveAll(it)
            navigateToBuy(it)
        })
    }

    private fun sellList() {
        mainViewModel.productsSaved.observe(this, Observer {
            Log.d(TAG, "List product in local ${it.toString()}")
            navigateToSell(it)
        })
    }

    private fun navigateToCall(users: List<User>?) {
        val bundle = bundleOf(ARG_USERS to users)
        findNavController().navigate(R.id.action_mainFragment_to_toCallFragment, bundle, options)
    }

    private fun navigateToBuy(products: List<Product>?) {
        val bundle = bundleOf(ARG_PRODUCTS to products)
        findNavController().navigate(R.id.action_mainFragment_to_toBuyFragment, bundle, options)
    }

    private fun navigateToSell(products: List<Product>?) {
        val bundle = bundleOf(ARG_PRODUCTS_LOCAL to products)
        findNavController().navigate(R.id.action_mainFragment_to_toSellFragment, bundle, options)
    }
}