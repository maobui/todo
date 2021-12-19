package com.bui.todoapplication.ui.sell

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bui.todoapplication.databinding.FragmentToSellBinding
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.ui.ProductAdapter
import kotlinx.android.synthetic.main.fragment_to_call.*
import kotlinx.android.synthetic.main.fragment_to_sell.*
import java.util.ArrayList

internal const val ARG_PRODUCTS_LOCAL = "PRODUCT_LIST"

class ToSellFragment : Fragment() {
    private final val TAG = ToSellFragment::class.java.simpleName

    private var _binding: FragmentToSellBinding? = null
    private var products: List<Product>? = mutableListOf()

    private val binding: FragmentToSellBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = it.getParcelableArrayList(ARG_PRODUCTS_LOCAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentToSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        recyclerView_products_sell.run {
            val adapter = ProductAdapter()
            setHasFixedSize(true)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            adapter.submitList(products)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(products: List<Product>) =
            ToSellFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PRODUCTS_LOCAL,
                        products as ArrayList<out Parcelable>)
                }
            }
    }
}