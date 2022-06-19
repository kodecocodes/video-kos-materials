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

package com.yourcompany.android.borednomore

import com.yourcompany.android.borednomore.data.FakeDataSource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BoredActivityRepositoryFakeDataSourceTest {
  @Test
  fun `should return all activities from its data source`() = runBlocking {
    val source = FakeDataSource(delayDuration = 0)
    val repository = BoredActivityRepository(source)

    val sourceData = source.getMany(Int.MAX_VALUE).unsafeUnwrap()
    val repositoryData = repository.getMany(Int.MAX_VALUE).unsafeUnwrap()

    assertEquals(sourceData, repositoryData)
  }

  @Test
  fun `should search data source for given activity`() = runBlocking {
    val source = FakeDataSource(delayDuration = 0)
    val repository = BoredActivityRepository(source)

    val (activity) = source.getMany(1).unsafeUnwrap()
    val sourceData = source.getOne(activity.key).unsafeUnwrap()
    val repositoryData = repository.getOne(activity.key).unsafeUnwrap()

    assertEquals(sourceData, repositoryData)
  }
}