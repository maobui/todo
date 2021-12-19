package com.bui.todoapplication.ui.buy

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bui.todoapplication.databinding.FragmentToBuyBinding
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.ui.ProductAdapter
import kotlinx.android.synthetic.main.fragment_to_buy.*
import java.util.ArrayList

internal const val ARG_PRODUCTS = "PRODUCT_LIST"

class ToBuyFragment : Fragment() {
    private final val TAG = ToBuyFragment::class.java.simpleName

    private var _binding: FragmentToBuyBinding? = null
    private var products: List<Product>? = mutableListOf()

    private val binding: FragmentToBuyBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = it.getParcelableArrayList(ARG_PRODUCTS);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentToBuyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        recyclerView_products_buy.run {
            val adapter = ProductAdapter()
            this.adapter = adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter.submitList(products)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(products: List<Product>) =
            ToBuyFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PRODUCTS, products as ArrayList<out Parcelable>)
                }
            }
    }
}