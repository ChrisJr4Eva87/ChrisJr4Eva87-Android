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

package com.duckduckgo.app.trackerdetection

import com.duckduckgo.app.trackerdetection.model.ResourceType

interface Client {

    enum class ClientType {

        BLOCKING,
        WHITELIST

    }

    enum class ClientName(val type: ClientType) {

        EASYLIST(ClientType.BLOCKING),
        EASYPRIVACY(ClientType.BLOCKING),
        TRACKERSWHITELIST(ClientType.WHITELIST),
        DISCONNECT(ClientType.BLOCKING)

    }

    val name: ClientName

    fun matches(url: String, documentUrl: String, resourceType: ResourceType): Boolean

}