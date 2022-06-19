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

package com.yourcompany.android.borednomore.data

import com.yourcompany.android.borednomore.core.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Implementation of [BoredActivityDataSource] that returns static, fake
 * data.
 *
 * Every operation on this data source returns a successful [Result]
 * after a delay of [delayDuration].
 */
class FakeDataSource(
    private val delayDuration: Long
) : BoredActivityDataSource {

  @Inject
  constructor() : this(delayDuration = 1000L)

  private val activities: List<BoredActivity> = listOf(
      BoredActivity(
          activity = "Paint the first thing you see",
          type = "recreational",
          participants = 1,
          price = 0.25,
          link = "",
          key = "1162360",
          accessibility = 0.2
      ),
      BoredActivity(
          activity = "Start a blog for something you're passionate about",
          type = "recreational",
          participants = 1,
          price = 0.05,
          link = "",
          key = "8364626",
          accessibility = 0.1
      ),
      BoredActivity(
          activity = "Start a garden",
          type = "recreational",
          participants = 1,
          price = 0.3,
          link = "",
          key = "1934228",
          accessibility = 0.35
      ),
  )

  override suspend fun getMany(max: Int): Result<List<BoredActivity>> {
    delay(delayDuration)
    val limit = max.coerceAtMost(activities.size)
    return Result.success(activities.slice(0 until limit))
  }

  override suspend fun getOne(key: String): Result<BoredActivity> {
    delay(delayDuration)
    val activity = this.activities.find { it.key == key }
        ?: return Result.failure(Error("Activity with key $key not found"))
    return Result.success(activity)
  }
}
