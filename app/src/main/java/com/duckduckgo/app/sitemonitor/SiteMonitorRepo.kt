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

package com.duckduckgo.app.sitemonitor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SiteMonitorRepo @Inject constructor() {

    private val siteMonitors : HashMap<String, SiteMonitor> = HashMap()

    fun registerSite(url: String, siteMonitor: SiteMonitor) {
        siteMonitors.put(url, siteMonitor)
    }

    fun get(url: String) : SiteMonitor? = siteMonitors[url]

}