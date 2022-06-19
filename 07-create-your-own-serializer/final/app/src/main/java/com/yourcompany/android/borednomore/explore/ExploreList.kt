/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yourcompany.android.borednomore.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourcompany.android.borednomore.data.BoredActivity
import com.yourcompany.android.borednomore.databinding.ItemBoredActivityBinding

class BoredActivityDiffer : DiffUtil.ItemCallback<BoredActivity>() {

  override fun areItemsTheSame(oldItem: BoredActivity, newItem: BoredActivity): Boolean {
    return oldItem.key == newItem.key
  }

  override fun areContentsTheSame(oldItem: BoredActivity, newItem: BoredActivity): Boolean {
    return oldItem == newItem
  }
}

class ExploreListItemViewHolder(
    private val binding: ItemBoredActivityBinding,
    private val onClick: (BoredActivity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(activity: BoredActivity) {
    binding.apply {
      activityName.text = activity.activity
      root.setOnClickListener { onClick(activity) }
    }
  }
}

class ExploreListAdapter(
    private val onClick: (BoredActivity) -> Unit
) : ListAdapter<BoredActivity, ExploreListItemViewHolder>(
    BoredActivityDiffer()
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreListItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemBoredActivityBinding.inflate(inflater, parent, false)
    return ExploreListItemViewHolder(binding, onClick)
  }

  override fun onBindViewHolder(holder: ExploreListItemViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(item)
  }
}