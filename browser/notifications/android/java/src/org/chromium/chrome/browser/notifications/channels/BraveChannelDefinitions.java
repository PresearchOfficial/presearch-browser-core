/**
 * Copyright (c) 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.chromium.chrome.browser.notifications.channels;

import android.annotation.SuppressLint;
import android.app.NotificationManager;

import org.chromium.chrome.browser.notifications.R;
import org.chromium.components.browser_ui.notifications.channels.ChannelDefinitions;
import org.chromium.components.browser_ui.notifications.channels.ChannelDefinitions.PredefinedChannel;

import java.util.Map;
import java.util.Set;

public class BraveChannelDefinitions {
    public class ChannelId {
        public static final String BRAVE_ADS = "com.presearch.browser.ads";
        public static final String BRAVE_ADS_BACKGROUND = "com.presearch.browser.ads.background";
        public static final String BRAVE_BROWSER = "com.presearch.browser";
        public static final int BRAVE_BROWSER_CHANNEL_INT = 1;
    }

    public class ChannelGroupId {
        public static final String BRAVE_ADS = "com.presearch.browser.ads";
    }

    @SuppressLint("NewApi")
    static protected void addBraveChannels(
            Map<String, PredefinedChannel> map, Set<String> startup) {
        
    }

    @SuppressLint("NewApi")
    static protected void addBraveChannelGroups(
            Map<String, ChannelDefinitions.PredefinedChannelGroup> map) {
        
    }
}
