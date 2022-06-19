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

import android.util.Log
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

@Serializable(with = BoredActivitySerializer::class)
data class BoredActivity(
  val activity: String,
  val type: String,
  val participants: Int,
  val price: Double,
  val link: String,
  val key: String,
  val accessibility: Double,
)

class BoredActivitySerializer: KSerializer<BoredActivity> {
  override fun deserialize(decoder: Decoder): BoredActivity
  = decoder.decodeStructure(descriptor) {
      var activity = ""
      var type = ""
      var participants = -1
      var price = 0.0
      var link = ""
      var key = ""
      var accessibility = 0.0

      while (true) {
        when (val index = decodeElementIndex(descriptor)) {
          0 -> activity = decodeStringElement(descriptor, 0)
          1 -> type = decodeStringElement(descriptor, 1)
          2 -> participants = decodeIntElement(descriptor, 2)
          3 -> price = decodeDoubleElement(descriptor, 3)
          4 -> link = decodeStringElement(descriptor, 4)
          5 -> key = decodeStringElement(descriptor, 5)
          6 -> accessibility = decodeDoubleElement(descriptor, 6)
          CompositeDecoder.DECODE_DONE -> break
          else -> error("Unexpected index: $index")
        }
      }
      Log.d("BoredActivitySerializer", "Using deserializer")
      BoredActivity(activity, type, participants, price,
                    link, key, accessibility)
    }

  override val descriptor: SerialDescriptor
    = buildClassSerialDescriptor("BoredActivity") {
      element<String>("activity")
      element<String>("type")
      element<Int>("participants")
      element<Double>("price")
      element<String>("link")
      element<String>("key")
      element<Double>("accessibility")
    }

  override fun serialize(encoder: Encoder, value: BoredActivity) {
    encoder.encodeStructure(descriptor) {
      encodeStringElement(descriptor, 0, value.activity)
      encodeStringElement(descriptor, 1, value.type)
      encodeIntElement(descriptor, 2, value.participants)
      encodeDoubleElement(descriptor, 3, value.price)
      encodeStringElement(descriptor, 4, value.link)
      encodeStringElement(descriptor, 5, value.key)
      encodeDoubleElement(descriptor, 6, value.accessibility)
    }
  }
}