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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yourcompany.android.borednomore.R
import com.yourcompany.android.borednomore.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {

  private val viewModel: ExploreViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      viewModel.getAll()
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    val binding = FragmentExploreBinding.inflate(inflater, container, false)

    val exploreListAdapter = ExploreListAdapter { activity ->
      findNavController().navigate(ExploreFragmentDirections.showDetails(activity.key))
    }

    binding.exploreList.apply {
      adapter = exploreListAdapter
      layoutManager = LinearLayoutManager(requireContext())

      val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
      addItemDecoration(divider)
    }

    binding.swipeRefresh.setOnRefreshListener {
      viewModel.getAll()
    }

    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.state
          .distinctUntilChanged { old, new -> old == new }
          .collect { state -> renderState(state, binding, exploreListAdapter) }
    }

    return binding.root
  }

  private fun renderState(
      state: ExploreState,
      binding: FragmentExploreBinding,
      adapter: ExploreListAdapter
  ) = with(binding) {
    when (state) {
      ExploreState.Uninitialized,
      is ExploreState.Loading -> {
        swipeRefresh.isRefreshing = true
        state.data()?.let { adapter.submitList(it) }
      }
      is ExploreState.Success -> {
        swipeRefresh.isRefreshing = false
        adapter.submitList(state.activities)
      }
      is ExploreState.Error -> {
        swipeRefresh.isRefreshing = false

        Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
        state.activities?.let { adapter.submitList(it) }
      }
    }
  }
}