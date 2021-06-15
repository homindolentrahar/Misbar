package com.homindolentrahar.misbar.ui.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.ShowsCarouselItemBinding
import com.homindolentrahar.misbar.domain.models.ShowsModel

class ShowsCarouselItemAdapter(private val onClick: (item: ShowsModel) -> Unit) :
    ListAdapter<ShowsModel, ShowsCarouselItemAdapter.ShowsCarouselHolder>(
        ShowsCarouselComparator
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowsCarouselHolder = ShowsCarouselHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(
        holder: ShowsCarouselHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
//        Click
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class ShowsCarouselHolder(private val binding: ShowsCarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShowsModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): ShowsCarouselHolder {
                val binding =
                    ShowsCarouselItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return ShowsCarouselHolder(binding)
            }
        }
    }

    companion object ShowsCarouselComparator : DiffUtil.ItemCallback<ShowsModel>() {
        override fun areItemsTheSame(oldItem: ShowsModel, newItem: ShowsModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShowsModel, newItem: ShowsModel): Boolean =
            oldItem == newItem
    }
}