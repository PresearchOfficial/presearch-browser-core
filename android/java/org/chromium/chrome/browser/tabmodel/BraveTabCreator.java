/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.tabmodel;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import org.chromium.base.Log;
import org.chromium.base.BraveReflectionUtil;
import org.chromium.base.supplier.ObservableSupplier;
import org.chromium.base.supplier.Supplier;
import org.chromium.chrome.browser.ChromeTabbedActivity;
import org.chromium.chrome.browser.app.BraveActivity;
import org.chromium.chrome.browser.app.ChromeActivity;
import org.chromium.chrome.browser.compositor.CompositorViewHolder;
import org.chromium.chrome.browser.homepage.HomepageManager;
import org.chromium.chrome.browser.init.StartupTabPreloader;
import org.chromium.chrome.browser.ntp_background_images.NTPBackgroundImagesBridge;
import org.chromium.chrome.browser.ntp_background_images.util.SponsoredImageUtil;
import org.chromium.chrome.browser.preferences.BravePref;
import org.chromium.chrome.browser.profiles.Profile;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.tab.TabDelegateFactory;
import org.chromium.chrome.browser.tab.TabLaunchType;
import org.chromium.components.embedder_support.util.UrlConstants;
import org.chromium.components.user_prefs.UserPrefs;
import org.chromium.content_public.browser.LoadUrlParams;
import org.chromium.ui.base.WindowAndroid;

public class BraveTabCreator extends ChromeTabCreator {
    public BraveTabCreator(Activity activity, WindowAndroid nativeWindow,
            StartupTabPreloader startupTabPreloader,
            Supplier<TabDelegateFactory> tabDelegateFactory, boolean incognito,
            OverviewNTPCreator overviewNTPCreator, AsyncTabParamsManager asyncTabParamsManager,
            Supplier<TabModelSelector> tabModelSelectorSupplier,
            Supplier<CompositorViewHolder> compositorViewHolderSupplier) {
        super(activity, nativeWindow, startupTabPreloader, tabDelegateFactory, incognito,
                overviewNTPCreator, asyncTabParamsManager, tabModelSelectorSupplier,
                compositorViewHolderSupplier);
    }

    @Override
    public Tab launchUrl(String url, @TabLaunchType int type) {
        String homePageUrl = HomepageManager.getHomepageUri();
        if (url.equals(UrlConstants.NTP_URL) && TextUtils.isEmpty(homePageUrl))
            url = "https://presearch.com";
        Log.e("Mamy Linx - launchUrl", url + "--" + homePageUrl);
        return super.launchUrl(url, type);
    }

    @Override
    public Tab createNewTab(LoadUrlParams loadUrlParams, @TabLaunchType int type, Tab parent) {
        String homePageUrl = HomepageManager.getHomepageUri();
        if (loadUrlParams.getUrl().equals(UrlConstants.NTP_URL)
                && type == TabLaunchType.FROM_TAB_GROUP_UI && TextUtils.isEmpty(homePageUrl))
            loadUrlParams.setUrl("https://presearch.com");
        Log.e("Mamy Linx - createNewTab", homePageUrl);
        return super.createNewTab(loadUrlParams, type, parent, null);
    }
}
