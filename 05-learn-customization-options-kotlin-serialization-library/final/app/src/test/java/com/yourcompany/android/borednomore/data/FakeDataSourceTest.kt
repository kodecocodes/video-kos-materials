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

import com.yourcompany.android.borednomore.core.Failure
import com.yourcompany.android.borednomore.core.Success
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeDataSourceTest {

  @Test
  fun `should produce fake activities up to given max limit`() = runBlocking {
    val source = FakeDataSource(delayDuration = 0)
    val max = 1
    val result = source.getMany(max)
    assertTrue(result is Success)
    assertTrue((result as Success).data.size <= max)
  }

  @Test
  fun `should return matching activity for a given key`() = runBlocking {
    val source = FakeDataSource(delayDuration = 0)
    val (activity) = source.getMany(Int.MAX_VALUE).unsafeUnwrap()
    val result = source.getOne(activity.key)
    assertTrue(result is Success)
    assertTrue(result.unsafeUnwrap() == activity)
  }

  @Test
  fun `should return an error if no matching activity is found`() = runBlocking {
    val source = FakeDataSource(delayDuration = 0)
    val result = source.getOne("invalid-key")
    assertTrue(result is Failure)
  }
}