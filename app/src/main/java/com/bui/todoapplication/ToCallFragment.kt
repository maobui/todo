package com.bui.todoapplication

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bui.todoapplication.databinding.FragmentToCallBinding
import com.bui.todoapplication.model.User
import kotlinx.android.synthetic.main.fragment_to_call.*
import java.util.ArrayList


internal const val ARG_USERS = "USER_LIST"

class ToCallFragment : Fragment() {
    private final val TAG = ToCallFragment::class.java.simpleName

    private var _binding: FragmentToCallBinding? = null
    private val binding
        get() = _binding!!
    private var users: List<User> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users = it.getParcelableArrayList<User>(ARG_USERS)!!

            Log.d(TAG, "users : $users")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentToCallBinding.inflate(inflater, container, false)
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
        recyclerView_users.run {
            val adapter = UserAdapter()
            setHasFixedSize(true)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            adapter.submitList(users)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(users: List<User>) =
            ToCallFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_USERS, users as ArrayList<out Parcelable>)
                }
            }
    }
}