/*
 * Copyright (c) 2018 DuckDuckGo
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

package com.duckduckgo.app.privacymonitor.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.duckduckgo.app.blockingObserve
import com.duckduckgo.app.global.db.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NetworkLeaderboardDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: NetworkLeaderboardDao

    @Before
    fun before() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
                .build()
        dao = db.networkLeaderboardDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun whenNetworksDetectedPercentsAreCorrect() {
        // 2/3
        dao.insert(NetworkLeaderboardEntry("Network1", domainVisited = "www.example1.com"))
        dao.insert(NetworkLeaderboardEntry("Network1", domainVisited = "www.example2.com"))

        // 1/3
        dao.insert(NetworkLeaderboardEntry("Network2", domainVisited = "www.example3.com"))

        val percents: Array<NetworkPercent>? = dao.networkPercents().blockingObserve()

        assertEquals(2, percents!!.size)
        assertEquals(3, percents[0].totalDomainsVisited)
        assertEquals(3, percents[1].totalDomainsVisited)

        assertEquals(66, (percents[0].percent * 100).toInt())
        assertEquals("Network1", percents[0].networkName)

        assertEquals(33, (percents[1].percent * 100).toInt())
        assertEquals("Network2", percents[1].networkName)
    }

}
