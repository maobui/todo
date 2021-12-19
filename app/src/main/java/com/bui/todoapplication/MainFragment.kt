package com.bui.todoapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bui.todoapplication.databinding.FragmentMainBinding
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import com.bui.todoapplication.remote.ApiBuilder
import com.bui.todoapplication.remote.AppApi
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment(), View.OnClickListener {
    private final val TAG = MainFragment::class.java.simpleName

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!
    private val service = ApiBuilder.buildService(AppApi::class.java)
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
        service.callList().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users: List<User>? = response.body()
                    Log.d(TAG, "List user ${users.toString()}")
                    navigateToCall(users)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "Failure: ", t)
            }
        })
    }

    private fun buyList() {
        service.buyList().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products: List<Product>? = response.body()
                    Log.d(TAG, "List product ${products.toString()}")
                    products?.takeIf { it.isNotEmpty() }
                        ?.let {
                            mainViewModel.saveAll(it)
                        }
                    navigateToBuy(products)

                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e(TAG, "Failure: ", t)
            }

        })
    }

    private fun sellList() {
        mainViewModel.allProducts.observe(this, Observer {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}