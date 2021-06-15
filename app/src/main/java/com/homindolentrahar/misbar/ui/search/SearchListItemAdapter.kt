package com.homindolentrahar.misbar.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.SearchListItemBinding
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.utils.mappers.FormatMapper

class SearchListItemAdapter(private val onClick: (item: SearchModel) -> Unit) :
    PagingDataAdapter<SearchModel, SearchListItemAdapter.SearchListItemHolder>(
        SearchListItemComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListItemHolder =
        SearchListItemHolder.from(parent.context, parent)

    override fun onBindViewHolder(holder: SearchListItemHolder, position: Int) {
        val item = getItem(position)

        item?.let { model ->
            holder.bind(model)

            holder.itemView.setOnClickListener {
                onClick(model)
            }
        }
    }

    class SearchListItemHolder(private val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(context: Context, parent: ViewGroup): SearchListItemHolder {
                return SearchListItemHolder(
                    SearchListItemBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(item: SearchModel) {
            binding.item = item
            binding.tvTitle.text = if (item.mediaType == "movie") item.title else item.name
            binding.tvRelease.text = FormatMapper.formatReleaseDate(
                if (item.mediaType == "movie") item.releaseDate
                else item.firstAirDate
            )
            binding.executePendingBindings()
        }
    }

    companion object SearchListItemComparator : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
            oldItem == newItem
    }

}