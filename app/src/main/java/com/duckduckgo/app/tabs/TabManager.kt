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

package com.duckduckgo.app.tabs

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import com.duckduckgo.app.home.HomeActivity
import com.duckduckgo.app.privacymonitor.PrivacyMonitor
import java.util.LinkedHashMap
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TabManager @Inject constructor(private val context: Context, private val repository: TabDataRepository): Application.ActivityLifecycleCallbacks {


    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    private val activities: LinkedHashMap<String, HomeActivity> = LinkedHashMap()

    val tabs: Tabs
        get() {
            val tabs = ArrayList<Tab>()
            for (activity in activities) {
                val data = repository.get(activity.key).value
                tabs.add(Tab(data?.url, data?.title))
            }
            return Tabs(tabs)
        }

    fun showCurrentTab(): Boolean {
        val current = tabTasks.lastOrNull() ?: return false
        current.moveToFront()
        return true
    }

    private val tabTasks
        get() = activityManager.appTasks
            .filter {
                it.baseIntentActivity == HomeActivity::class.java.name
            }

    fun selectTab(position: Int) {
        val activity = activities.values.toList()[position]
        activityManager.moveTaskToFront(activity.taskId, 0)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity is HomeActivity) {
            activities.put(activity.intent.tabId, activity)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    val ActivityManager.AppTask.baseIntentActivity: String get() = taskInfo.baseIntent.component.className

    val ActivityManager.AppTask.tabId: String get() = taskInfo.baseIntent.tabId

    data class Tabs(var all: List<Tab>)

    data class Tab(val url: String?, val title: String?)
}
