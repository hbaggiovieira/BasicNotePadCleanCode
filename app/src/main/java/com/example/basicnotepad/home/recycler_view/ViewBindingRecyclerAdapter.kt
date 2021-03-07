package com.example.basicnotepad.home.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingRecyclerAdapter<T : Any, Binding : ViewBinding>
    : ListAdapter<T, ViewBindingRecyclerAdapter.GenericViewHolder<T, Binding>>(GenericDiffItemCallback<T>()) {

    abstract fun bindItem(binding: Binding, item: T)

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T, Binding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = createBinding(inflater, parent)
        return GenericViewHolder(binding, ::bindItem)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T, Binding>, position: Int) {
        holder.bind(getItem(position))
    }

    class GenericViewHolder<T, Binding : ViewBinding>(
            private val binding: Binding,
            private val bindFunction: (Binding, T) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            bindFunction(binding, item)
        }
    }

    class GenericDiffItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }
}

