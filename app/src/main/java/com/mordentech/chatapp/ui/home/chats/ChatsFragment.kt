package com.mordentech.chatapp.ui.home.chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mordentech.chatapp.data.db.entities.Profile
import com.mordentech.chatapp.databinding.FragmentChatsBinding
import com.mordentech.chatapp.util.adapters.ChatsProfileAdapter
import com.mordentech.chatapp.util.toast

class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)
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
                binding.root.context.toast("Profile ${profile.id} clicked")
            }
            layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false }
        }
    }

}