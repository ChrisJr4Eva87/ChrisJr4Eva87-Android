/*
 * Copyright (c) 2017 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.tabs


import android.arch.lifecycle.MutableLiveData
import com.duckduckgo.app.privacymonitor.PrivacyMonitor
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TabDataRepository @Inject constructor() {

    private val data: LinkedHashMap<String, MutableLiveData<PrivacyMonitor>> = LinkedHashMap()

    fun get(tabId: String): MutableLiveData<PrivacyMonitor> {
        val data = data[tabId]
        if (data == null) {
            val siteMonitor = MutableLiveData<PrivacyMonitor>()
            add(tabId, siteMonitor)
            return siteMonitor
        }
        return data
    }

    private fun add(tabId: String, data: MutableLiveData<PrivacyMonitor>) {
        this.data[tabId] = data
    }

    // TODO: Clear element when tab closes
    fun remove(tabId: String) {

    }

    // TODO: Clear all when fire pressed
    fun clearAll() {

    }

}
