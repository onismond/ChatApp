package com.mordentech.chatapp.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mordentech.chatapp.R
import com.mordentech.chatapp.data.db.entities.Profile
import com.mordentech.chatapp.databinding.ItemChatsProfileBinding

class ChatsProfileAdapter(
    private var profiles: MutableList<Profile>,
    private val context: Context
): RecyclerView.Adapter<ChatsProfileAdapter.ChatsProfileViewHolder>() {

    lateinit var onChatsProfileClicked: ((Profile) -> Unit)

    inner class ChatsProfileViewHolder(val binding: ItemChatsProfileBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onChatsProfileClicked.invoke(profiles[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatsProfileViewHolder {
        val binding = ItemChatsProfileBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatsProfileViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChatsProfileViewHolder,
        position: Int
    ) {
        with(holder) {
            with(profiles[position]) {
                binding.textViewName.text = name
                binding.textViewDate.text = messageDate
                binding.textViewMessage.text = recentMessage
            }
        }
    }

    override fun getItemCount() = profiles.size

}