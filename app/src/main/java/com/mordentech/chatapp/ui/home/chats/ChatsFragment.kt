package com.mordentech.chatapp.ui.home.chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mordentech.chatapp.data.db.entities.Profile
import com.mordentech.chatapp.databinding.FragmentChatsBinding
import com.mordentech.chatapp.ui.home.HomeViewModel
import com.mordentech.chatapp.ui.home.HomeViewModelFactory
import com.mordentech.chatapp.util.BaseFragment
import com.mordentech.chatapp.util.adapters.ChatsProfileAdapter
import com.mordentech.chatapp.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChatsFragment : BaseFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var binding: FragmentChatsBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        setupChatsRecyclerview()
        return binding.root
    }

    private fun setupChatsRecyclerview() {
        val chatsProfileAdapter = ChatsProfileAdapter(mutableListOf(
            Profile("1", "John Doe", "", "Happy holidays!", "Just Now"),
            Profile("2", "John Doe", "", "Happy holidays!", "Just Now"),
            Profile("3", "John Doe", "", "Happy holidays!", "Just Now"),
            Profile("4", "John Doe", "", "Happy holidays!", "Just Now"),
            Profile("5", "John Doe", "", "Happy holidays!", "Just Now"),
        ), requireActivity().applicationContext)
        binding.recyclerViewChats.apply {
            adapter = chatsProfileAdapter
            chatsProfileAdapter.onChatsProfileClicked = { profile ->
                profileClicked(profile)
            }
            layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false }
        }
    }

    private fun profileClicked(profile : Profile) {
        val action = ChatsFragmentDirections.actionChatsFragmentToChatFragment()
        this.findNavController().navigate(action)
    }

}