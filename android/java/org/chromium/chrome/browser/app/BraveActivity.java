/* Copyright (c) 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.app;

import static com.android.billingclient.api.BillingClient.SkuType.SUBS;

import static org.chromium.ui.base.ViewUtils.dpToPx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wireguard.android.backend.GoBackend;
import com.wireguard.android.backend.Tunnel;
import com.wireguard.crypto.KeyPair;

import org.json.JSONException;

import org.chromium.base.ApplicationStatus;
import org.chromium.base.BraveReflectionUtil;
import org.chromium.base.CollectionUtil;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.IntentUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeMethods;
import org.chromium.base.supplier.ObservableSupplier;
import org.chromium.base.supplier.UnownedUserDataSupplier;
import org.chromium.base.task.PostTask;
import org.chromium.base.task.TaskTraits;
import org.chromium.brave_wallet.mojom.BraveWalletService;
import org.chromium.brave_wallet.mojom.JsonRpcService;
import org.chromium.brave_wallet.mojom.KeyringService;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.ApplicationLifetime;
import org.chromium.chrome.browser.BraveConfig;
import org.chromium.chrome.browser.BraveFeatureList;
import org.chromium.chrome.browser.BraveHelper;
import org.chromium.chrome.browser.BraveRelaunchUtils;
import org.chromium.chrome.browser.BraveRewardsHelper;
import org.chromium.chrome.browser.BraveRewardsNativeWorker;
import org.chromium.chrome.browser.BraveSyncInformers;
import org.chromium.chrome.browser.BraveSyncWorker;
import org.chromium.chrome.browser.ChromeTabbedActivity;
import org.chromium.chrome.browser.CrossPromotionalModalDialogFragment;
import org.chromium.chrome.browser.DormantUsersEngagementDialogFragment;
import org.chromium.chrome.browser.homepage.HomepageManager;
import org.chromium.chrome.browser.InternetConnection;
import org.chromium.chrome.browser.LaunchIntentDispatcher;
import org.chromium.chrome.browser.bookmarks.BookmarkBridge;
import org.chromium.chrome.browser.bookmarks.BookmarkModel;
import org.chromium.chrome.browser.brave_news.models.FeedItemsCard;
import org.chromium.chrome.browser.brave_stats.BraveStatsUtil;
import org.chromium.chrome.browser.browsing_data.BrowsingDataBridge;
import org.chromium.chrome.browser.browsing_data.BrowsingDataType;
import org.chromium.chrome.browser.browsing_data.ClearBrowsingDataFragmentAdvanced;
import org.chromium.chrome.browser.browsing_data.TimePeriod;
import org.chromium.chrome.browser.compositor.CompositorViewHolder;
import org.chromium.chrome.browser.compositor.layouts.Layout;
import org.chromium.chrome.browser.compositor.layouts.LayoutManagerChrome;
import org.chromium.chrome.browser.compositor.layouts.LayoutManagerImpl;
import org.chromium.chrome.browser.crypto_wallet.BraveWalletServiceFactory;
import org.chromium.chrome.browser.crypto_wallet.JsonRpcServiceFactory;
import org.chromium.chrome.browser.crypto_wallet.KeyringServiceFactory;
import org.chromium.chrome.browser.crypto_wallet.activities.BraveWalletActivity;
import org.chromium.chrome.browser.crypto_wallet.activities.BraveWalletDAppsActivity;
import org.chromium.chrome.browser.crypto_wallet.activities.NetworkSelectorActivity;
import org.chromium.chrome.browser.crypto_wallet.util.Utils;
import org.chromium.chrome.browser.dependency_injection.ChromeActivityComponent;
import org.chromium.chrome.browser.flags.ChromeFeatureList;
import org.chromium.chrome.browser.flags.ChromeSwitches;
import org.chromium.chrome.browser.fullscreen.BrowserControlsManager;
import org.chromium.chrome.browser.informers.BraveAndroidSyncDisabledInformer;
import org.chromium.chrome.browser.notifications.retention.RetentionNotificationUtil;
import org.chromium.chrome.browser.ntp.NewTabPage;
import org.chromium.chrome.browser.ntp_background_images.util.NewTabPageListener;
import org.chromium.chrome.browser.onboarding.BraveTalkOptInPopupListener;
import org.chromium.chrome.browser.onboarding.OnboardingActivity;
import org.chromium.chrome.browser.onboarding.OnboardingPrefManager;
import org.chromium.chrome.browser.onboarding.v2.HighlightDialogFragment;
import org.chromium.chrome.browser.preferences.BravePrefServiceBridge;
import org.chromium.chrome.browser.preferences.BravePreferenceKeys;
import org.chromium.chrome.browser.preferences.Pref;
import org.chromium.chrome.browser.preferences.SharedPreferencesManager;
import org.chromium.chrome.browser.preferences.website.BraveShieldsContentSettings;
import org.chromium.chrome.browser.prefetch.settings.PreloadPagesSettingsBridge;
import org.chromium.chrome.browser.prefetch.settings.PreloadPagesState;
import org.chromium.chrome.browser.privacy.settings.BravePrivacySettings;
import org.chromium.chrome.browser.profiles.Profile;
import org.chromium.chrome.browser.rate.RateDialogFragment;
import org.chromium.chrome.browser.rate.RateUtils;
import org.chromium.chrome.browser.set_default_browser.BraveSetDefaultBrowserUtils;
import org.chromium.chrome.browser.set_default_browser.OnBraveSetDefaultBrowserListener;
import org.chromium.chrome.browser.settings.BraveNewsPreferences;
import org.chromium.chrome.browser.settings.BraveRewardsPreferences;
import org.chromium.chrome.browser.settings.BraveSearchEngineUtils;
import org.chromium.chrome.browser.settings.SettingsLauncherImpl;
import org.chromium.chrome.browser.share.ShareDelegate;
import org.chromium.chrome.browser.share.ShareDelegate.ShareOrigin;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.tab.TabImpl;
import org.chromium.chrome.browser.tab.TabLaunchType;
import org.chromium.chrome.browser.tab.TabSelectionType;
import org.chromium.chrome.browser.tabmodel.TabModel;
import org.chromium.chrome.browser.tabmodel.TabModelUtils;
import org.chromium.chrome.browser.tasks.tab_management.BraveTabUiFeatureUtilities;
import org.chromium.chrome.browser.toolbar.top.BraveToolbarLayoutImpl;
import org.chromium.chrome.browser.util.BraveDbUtil;
import org.chromium.chrome.browser.util.BraveReferrer;
import org.chromium.chrome.browser.util.ConfigurationUtils;
import org.chromium.chrome.browser.util.PackageUtils;
import org.chromium.chrome.browser.vpn.BraveVpnNativeWorker;
import org.chromium.chrome.browser.vpn.BraveVpnObserver;
import org.chromium.chrome.browser.vpn.activities.BraveVpnProfileActivity;
import org.chromium.chrome.browser.vpn.fragments.BraveVpnCalloutDialogFragment;
import org.chromium.chrome.browser.vpn.utils.BraveVpnApiResponseUtils;
import org.chromium.chrome.browser.vpn.utils.BraveVpnPrefUtils;
import org.chromium.chrome.browser.vpn.utils.BraveVpnProfileUtils;
import org.chromium.chrome.browser.vpn.utils.BraveVpnUtils;
import org.chromium.chrome.browser.vpn.utils.InAppPurchaseWrapper;
import org.chromium.chrome.browser.vpn.wireguard.TunnelModel;
import org.chromium.chrome.browser.vpn.wireguard.WireguardConfigUtils;
import org.chromium.chrome.browser.widget.crypto.binance.BinanceAccountBalance;
import org.chromium.chrome.browser.widget.crypto.binance.BinanceWidgetManager;
import org.chromium.components.bookmarks.BookmarkId;
import org.chromium.components.bookmarks.BookmarkType;
import org.chromium.components.browser_ui.settings.SettingsLauncher;
import org.chromium.components.embedder_support.util.UrlConstants;
import org.chromium.components.embedder_support.util.UrlUtilities;
import org.chromium.components.search_engines.TemplateUrl;
import org.chromium.components.user_prefs.UserPrefs;
import org.chromium.mojo.bindings.ConnectionErrorHandler;
import org.chromium.mojo.system.MojoException;
import org.chromium.ui.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Brave's extension for ChromeActivity
 */
@JNINamespace("chrome::android")
public abstract class BraveActivity<C extends ChromeActivityComponent> extends ChromeActivity
        implements BrowsingDataBridge.OnClearBrowsingDataListener, BraveVpnObserver,
                   OnBraveSetDefaultBrowserListener, ConnectionErrorHandler {
    public static final int SITE_BANNER_REQUEST_CODE = 33;
    public static final int VERIFY_WALLET_ACTIVITY_REQUEST_CODE = 34;
    public static final int USER_WALLET_ACTIVITY_REQUEST_CODE = 35;
    public static final int MONTHLY_CONTRIBUTION_REQUEST_CODE = 36;
    public static final String ADD_FUNDS_URL = "presearch://";
    public static final String BRAVE_REWARDS_SETTINGS_URL = "presearch://";
    public static final String BRAVE_REWARDS_SETTINGS_WALLET_VERIFICATION_URL =
            "presearch://";
    public static final String REWARDS_AC_SETTINGS_URL = "presearch://";
    public static final String REWARDS_LEARN_MORE_URL = " https://support.presearch.org/";
    public static final String BRAVE_TERMS_PAGE =
            "https://presearch.com/terms";
    public static final String P3A_URL = "https://presearch.com/";
    public static final String BRAVE_PRIVACY_POLICY = "https://presearch.com/privacy";
    private static final String PREF_CLOSE_TABS_ON_EXIT = "close_tabs_on_exit";
    private static final String PREF_CLEAR_ON_EXIT = "clear_on_exit";
    public static final String OPEN_URL = "open_url";

    public static final String BRAVE_PRODUCTION_PACKAGE_NAME = "com.presearch";
    public static final String BRAVE_BETA_PACKAGE_NAME = "com.presearch.browser_beta";
    public static final String BRAVE_NIGHTLY_PACKAGE_NAME = "com.presearch.browser_nightly";

    private static final int DAYS_1 = 1;
    private static final int DAYS_4 = 4;
    private static final int DAYS_5 = 5;
    private static final int DAYS_12 = 12;

    /**
     * Settings for sending local notification reminders.
     */
    public static final String CHANNEL_ID = "com.presearch";

    // Explicitly declare this variable to avoid build errors.
    // It will be removed in asm and parent variable will be used instead.
    private UnownedUserDataSupplier<BrowserControlsManager> mBrowserControlsManagerSupplier;

    private static final List<String> yandexRegions =
            Arrays.asList("AM", "AZ", "BY", "KG", "KZ", "MD", "RU", "TJ", "TM", "UZ");

    private String mPurchaseToken = "";
    private String mProductId = "";
    private boolean mIsVerification;
    private boolean isDefaultCheckOnResume;
    private boolean isSetDefaultBrowserNotification;
    private BraveWalletService mBraveWalletService;
    private KeyringService mKeyringService;
    private JsonRpcService mJsonRpcService;
    public CompositorViewHolder compositorView;
    public View inflatedSettingsBarLayout;
    public boolean mLoadedFeed;
    public boolean mComesFromNewTab;
    public CopyOnWriteArrayList<FeedItemsCard> mNewsItemsFeedCards;
    private int mLastTabId;

    @SuppressLint("VisibleForTests")
    public BraveActivity() {
        // Disable key checker to avoid asserts on Brave keys in debug
        SharedPreferencesManager.getInstance().disableKeyCheckerForTesting();
    }

    @Override
    public void onPauseWithNative() {
        if (BraveVpnUtils.isBraveVpnFeatureEnable()) {
            BraveVpnNativeWorker.getInstance().removeObserver(this);
        }
        super.onPauseWithNative();
    }

    private NewTabPageListener newTabPageListener;

    @Override
    public boolean onMenuOrKeyboardAction(int id, boolean fromMenu) {
        final TabImpl currentTab = (TabImpl) getActivityTab();
        // Handle items replaced by Brave.
        if (id == R.id.info_menu_id && currentTab != null) {
            ShareDelegate shareDelegate = (ShareDelegate) getShareDelegateSupplier().get();
            shareDelegate.share(currentTab, false, ShareOrigin.OVERFLOW_MENU);
            return true;
        } else if (id == R.id.reload_menu_id) {
            setComesFromNewTab(true);
        }

        if (super.onMenuOrKeyboardAction(id, fromMenu)) {
            return true;
        }

        // Handle items added by Brave.
        if (currentTab == null) {
            return false;
        } else if (id == R.id.exit_id) {
            ApplicationLifetime.terminate(false);
        } else if (id == R.id.set_default_browser) {
            BraveSetDefaultBrowserUtils.showBraveSetDefaultBrowserDialog(BraveActivity.this, true);
        } else if (id == R.id.brave_rewards_id) {
            openNewOrSelectExistingTab(BRAVE_REWARDS_SETTINGS_URL);
        } else if (id == R.id.brave_wallet_id) {
            openBraveWallet(false);
        } else if (id == R.id.brave_news_id) {
            openBraveNewsSettings();
        } else if (id == R.id.request_brave_vpn_id || id == R.id.request_brave_vpn_check_id) {
            if (!InternetConnection.isNetworkAvailable(BraveActivity.this)) {
                Toast.makeText(BraveActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            } else {
                if (BraveVpnProfileUtils.getInstance().isBraveVPNConnected(BraveActivity.this)) {
                    BraveVpnUtils.showProgressDialog(BraveActivity.this,
                            getResources().getString(R.string.vpn_disconnect_text));
                    BraveVpnProfileUtils.getInstance().stopVpn(BraveActivity.this);
                    BraveVpnUtils.dismissProgressDialog();
                } else {
                    BraveVpnUtils.showProgressDialog(BraveActivity.this,
                            getResources().getString(R.string.vpn_connect_text));
                    if (BraveVpnPrefUtils.isSubscriptionPurchase()) {
                        verifySubscription();
                    } else {
                        BraveVpnUtils.dismissProgressDialog();
                        BraveVpnUtils.openBraveVpnPlansActivity(BraveActivity.this);
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionError(MojoException e) {
        mBraveWalletService.close();
        mBraveWalletService = null;
        mKeyringService.close();
        mKeyringService = null;
        mJsonRpcService.close();
        mJsonRpcService = null;
        InitBraveWalletService();
        InitKeyringService();
        InitJsonRpcService();
    }

    @Override
    protected void onDestroyInternal() {
        super.onDestroyInternal();
        if (mBraveWalletService != null) {
            mBraveWalletService.close();
        }
        if (mKeyringService != null) {
            mKeyringService.close();
        }
        if (mJsonRpcService != null) {
            mJsonRpcService.close();
        }
    }

    private void InitBraveWalletService() {
        if (mBraveWalletService != null) {
            return;
        }

        mBraveWalletService = BraveWalletServiceFactory.getInstance().getBraveWalletService(this);
    }

    private void InitKeyringService() {
        if (mKeyringService != null) {
            return;
        }

        mKeyringService = KeyringServiceFactory.getInstance().getKeyringService(this);
    }

    private void InitJsonRpcService() {
        if (mJsonRpcService != null) {
            return;
        }

        mJsonRpcService = JsonRpcServiceFactory.getInstance().getJsonRpcService(this);
    }

    private void maybeHasPendingUnlockRequest() {
        assert mKeyringService != null;
        mKeyringService.hasPendingUnlockRequest(pending -> {
            if (pending) {
                BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
                assert layout != null;
                if (layout != null) {
                    layout.showWalletPanel();
                }

                return;
            }
            maybeShowSignMessageRequestLayout();
        });
    }

    private void maybeShowSignMessageRequestLayout() {
        assert mBraveWalletService != null;
        mBraveWalletService.getPendingSignMessageRequests(requests -> {
            if (requests != null && requests.length != 0) {
                openBraveWalletDAppsActivity(BraveWalletDAppsActivity.ActivityType.SIGN_MESSAGE);

                return;
            }
            maybeShowChainRequestLayout();
        });
    }

    private void maybeShowChainRequestLayout() {
        assert mJsonRpcService != null;
        mJsonRpcService.getPendingAddChainRequests(networks -> {
            if (networks != null && networks.length != 0) {
                openBraveWalletDAppsActivity(
                        BraveWalletDAppsActivity.ActivityType.ADD_ETHEREUM_CHAIN);

                return;
            }
            maybeShowSwitchChainRequestLayout();
        });
    }

    private void maybeShowSwitchChainRequestLayout() {
        assert mJsonRpcService != null;
        mJsonRpcService.getPendingSwitchChainRequests(requests -> {
            if (requests != null && requests.length != 0) {
                openBraveWalletDAppsActivity(
                        BraveWalletDAppsActivity.ActivityType.SWITCH_ETHEREUM_CHAIN);

                return;
            }
            maybeShowAddSuggestTokenRequestLayout();
        });
    }

    private void maybeShowAddSuggestTokenRequestLayout() {
        assert mBraveWalletService != null;
        mBraveWalletService.getPendingAddSuggestTokenRequests(requests -> {
            if (requests != null && requests.length != 0) {
                openBraveWalletDAppsActivity(BraveWalletDAppsActivity.ActivityType.ADD_TOKEN);

                return;
            }
            BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
            assert layout != null;
            if (layout != null) {
                layout.showWalletPanel();
            }
        });
    }

    public void showWalletPanel() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.showWalletIcon(true);
        }
        assert mKeyringService != null;
        mKeyringService.isLocked(locked -> {
            if (locked) {
                layout.showWalletPanel();
                return;
            }
            maybeHasPendingUnlockRequest();
        });
    }

    public void showWalletOnboarding() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.showWalletIcon(true);
            layout.showWalletPanel();
        }
    }

    private void verifySubscription() {
        List<Purchase> purchases = InAppPurchaseWrapper.getInstance().queryPurchases();
        if (purchases != null && purchases.size() == 1) {
            Purchase purchase = purchases.get(0);
            mPurchaseToken = purchase.getPurchaseToken();
            mProductId = purchase.getSkus().get(0).toString();
            BraveVpnNativeWorker.getInstance().verifyPurchaseToken(mPurchaseToken, mProductId,
                    BraveVpnUtils.SUBSCRIPTION_PARAM_TEXT, getPackageName());
        } else {
            BraveVpnApiResponseUtils.queryPurchaseFailed(BraveActivity.this);
            if (!mIsVerification) {
                BraveVpnUtils.openBraveVpnPlansActivity(BraveActivity.this);
            }
        }
    }

    @Override
    public void onVerifyPurchaseToken(String jsonResponse, boolean isSuccess) {
        if (isSuccess) {
            Long purchaseExpiry = BraveVpnUtils.getPurchaseExpiryDate(jsonResponse);
            int paymentState = BraveVpnUtils.getPaymentState(jsonResponse);
            if (purchaseExpiry > 0 && purchaseExpiry >= System.currentTimeMillis()) {
                BraveVpnPrefUtils.setPurchaseToken(mPurchaseToken);
                BraveVpnPrefUtils.setProductId(mProductId);
                BraveVpnPrefUtils.setPurchaseExpiry(purchaseExpiry);
                BraveVpnPrefUtils.setSubscriptionPurchase(true);
                BraveVpnPrefUtils.setPaymentState(paymentState);
                if (BraveVpnPrefUtils.isResetConfiguration()) {
                    BraveVpnUtils.dismissProgressDialog();
                    BraveVpnUtils.openBraveVpnProfileActivity(BraveActivity.this);
                } else {
                    if (!mIsVerification) {
                        checkForVpn();
                    } else {
                        mIsVerification = false;
                        if (BraveVpnProfileUtils.getInstance().isBraveVPNConnected(
                                    BraveActivity.this)
                                && !TextUtils.isEmpty(BraveVpnPrefUtils.getHostname())
                                && !TextUtils.isEmpty(BraveVpnPrefUtils.getClientId())
                                && !TextUtils.isEmpty(BraveVpnPrefUtils.getSubscriberCredential())
                                && !TextUtils.isEmpty(BraveVpnPrefUtils.getApiAuthToken())) {
                            BraveVpnNativeWorker.getInstance().verifyCredentials(
                                    BraveVpnPrefUtils.getHostname(),
                                    BraveVpnPrefUtils.getClientId(),
                                    BraveVpnPrefUtils.getSubscriberCredential(),
                                    BraveVpnPrefUtils.getApiAuthToken());
                        }
                    }
                    BraveVpnUtils.dismissProgressDialog();
                }
            } else {
                BraveVpnApiResponseUtils.queryPurchaseFailed(BraveActivity.this);
                if (!mIsVerification) {
                    BraveVpnUtils.openBraveVpnPlansActivity(BraveActivity.this);
                }
                mIsVerification = false;
            }
            mPurchaseToken = "";
            mProductId = "";
        } else {
            BraveVpnApiResponseUtils.queryPurchaseFailed(BraveActivity.this);
            if (!mIsVerification) {
                BraveVpnUtils.openBraveVpnPlansActivity(BraveActivity.this);
            }
            mIsVerification = false;
        }
    };

    private void checkForVpn() {
        new Thread() {
            @Override
            public void run() {
                Intent intent = GoBackend.VpnService.prepare(BraveActivity.this);
                if (intent != null
                        || !WireguardConfigUtils.isConfigExist(getApplicationContext())) {
                    BraveVpnUtils.dismissProgressDialog();
                    BraveVpnUtils.openBraveVpnProfileActivity(BraveActivity.this);
                    return;
                }
                BraveVpnProfileUtils.getInstance().startVpn(BraveActivity.this);
            }
        }.start();
    }

    @Override
    public void onVerifyCredentials(String jsonVerifyCredentials, boolean isSuccess) {
        if (!isSuccess) {
            if (BraveVpnProfileUtils.getInstance().isBraveVPNConnected(BraveActivity.this)) {
                BraveVpnProfileUtils.getInstance().stopVpn(BraveActivity.this);
            }
            Intent braveVpnProfileIntent =
                    new Intent(BraveActivity.this, BraveVpnProfileActivity.class);
            braveVpnProfileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            braveVpnProfileIntent.putExtra(BraveVpnUtils.VERIFY_CREDENTIALS_FAILED, true);
            startActivity(braveVpnProfileIntent);
        }
    }

    @Override
    public void initializeState() {
        super.initializeState();
        if (isNoRestoreState()) {
            CommandLine.getInstance().appendSwitch(ChromeSwitches.NO_RESTORE_STATE);
        }

        if (isClearBrowsingDataOnExit()) {
            List<Integer> dataTypes = Arrays.asList(
                    BrowsingDataType.HISTORY, BrowsingDataType.COOKIES, BrowsingDataType.CACHE);

            int[] dataTypesArray = CollectionUtil.integerListToIntArray(new ArrayList<>(dataTypes));

            // has onBrowsingDataCleared() as an @Override callback from implementing
            // BrowsingDataBridge.OnClearBrowsingDataListener
            BrowsingDataBridge.getInstance().clearBrowsingData(
                    this, dataTypesArray, TimePeriod.ALL_TIME);
        }

        // setLoadedFeed(false);
        setComesFromNewTab(false);
        // setNewsItemsFeedCards(null);
        BraveSearchEngineUtils.initializeBraveSearchEngineStates(getTabModelSelector());
    }

    // Alternative to brave omaha server. By Mamy Linx
    private void loadAdblockFilter(int fromRId, String toDataFile) {
        Context context = ContextUtils.getApplicationContext();
        String dPath = context.getApplicationInfo().dataDir + File.separator
                    + "app_chrome" + File.separator + "cffkpbalmllkdoenhmdmpbkajipdjfam"
                    + File.separator + "1.0.1344" + File.separator;
        try {
            InputStream ins = getResources().openRawResource(fromRId);
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            FileOutputStream fos = new FileOutputStream(new File(dPath + toDataFile));

            int size = 0;
            byte[] buffer = new byte[1024];

            while((size=ins.read(buffer,0,1024))>=0){
                outputStream.write(buffer,0,size);
            }
            ins.close();
            buffer=outputStream.toByteArray();
            
            fos.write(buffer);
            fos.close();
        } catch (Exception e){
            return;
        }
    }

    private boolean fileExists(Context context, String filename) {    
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public int getLastTabId() {
        return mLastTabId;
    }

    public void setLastTabId(int lastTabId) {
        this.mLastTabId = lastTabId;
    }

    public boolean isLoadedFeed() {
        return mLoadedFeed;
    }

    public void setLoadedFeed(boolean loadedFeed) {
        this.mLoadedFeed = loadedFeed;
    }

    public CopyOnWriteArrayList<FeedItemsCard> getNewsItemsFeedCards() {
        return mNewsItemsFeedCards;
    }

    public void setNewsItemsFeedCards(CopyOnWriteArrayList<FeedItemsCard> newsItemsFeedCards) {
        this.mNewsItemsFeedCards = newsItemsFeedCards;
    }

    public void setComesFromNewTab(boolean comesFromNewTab) {
        this.mComesFromNewTab = comesFromNewTab;
    }

    public boolean isComesFromNewTab() {
        return mComesFromNewTab;
    }

    @Override
    public void onBrowsingDataCleared() {}

    @Override
    public void OnCheckDefaultResume() {
        isDefaultCheckOnResume = true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isDefaultCheckOnResume) {
            isDefaultCheckOnResume = false;

            if (BraveSetDefaultBrowserUtils.isBraveSetAsDefaultBrowser(this)) {
                BraveSetDefaultBrowserUtils.setBraveDefaultSuccess();
            }
        }
        Tab tab = getActivityTab();
        if (tab == null)
            return;

        // Set proper active DSE whenever presearch returns to foreground.
        // If active tab is private, set private DSE as an active DSE.
        BraveSearchEngineUtils.updateActiveDSE(tab.isIncognito());
        BraveStatsUtil.removeShareStatsFile();
    }

    @Override
    public void onPause() {
        super.onPause();
        Tab tab = getActivityTab();
        if (tab == null)
            return;

        // Set normal DSE as an active DSE when presearch goes in background
        // because currently set DSE is used by outside of presearch(ex, presearch search widget).
        if (tab.isIncognito()) {
            BraveSearchEngineUtils.updateActiveDSE(false);
        }
    }

    @Override
    public void performPostInflationStartup() {
        super.performPostInflationStartup();

        createNotificationChannel();
    }

    @Override
    protected void initializeStartupMetrics() {
        super.initializeStartupMetrics();

        // Disable FRE for arm64 builds where ChromeActivity is the one that
        // triggers FRE instead of ChromeLauncherActivity on arm32 build.
        BraveHelper.DisableFREDRP();
    }

    @Override
    public void finishNativeInitialization() {
        super.finishNativeInitialization();

        if (SharedPreferencesManager.getInstance().readBoolean(
                    BravePreferenceKeys.BRAVE_DOUBLE_RESTART, false)) {
            SharedPreferencesManager.getInstance().writeBoolean(
                    BravePreferenceKeys.BRAVE_DOUBLE_RESTART, false);
            BraveRelaunchUtils.restart();
            return;
        }

        // Make sure this option is disabled
        if (PreloadPagesSettingsBridge.getState() != PreloadPagesState.NO_PRELOADING) {
            PreloadPagesSettingsBridge.setState(PreloadPagesState.NO_PRELOADING);
        }

        if (BraveRewardsHelper.hasRewardsEnvChange()) {
            BravePrefServiceBridge.getInstance().resetPromotionLastFetchStamp();
            BraveRewardsHelper.setRewardsEnvChange(false);
        }

        int appOpenCount = SharedPreferencesManager.getInstance().readInt(BravePreferenceKeys.BRAVE_APP_OPEN_COUNT);
        SharedPreferencesManager.getInstance().writeInt(BravePreferenceKeys.BRAVE_APP_OPEN_COUNT, appOpenCount + 1);

        if (PackageUtils.isFirstInstall(this) && appOpenCount == 0) {
            checkForYandexSE();
        }

        //set bg ads to off for existing and new installations
        setBgBraveAdsDefaultOff();

        Context app = ContextUtils.getApplicationContext();

        checkForNotificationData();

        if (!RateUtils.getInstance(this).getPrefRateEnabled()) {
            RateUtils.getInstance(this).setPrefRateEnabled(true);
            RateUtils.getInstance(this).setNextRateDateAndCount();
        }

        if (RateUtils.getInstance(this).shouldShowRateDialog())
            showBraveRateDialog();

        if (SharedPreferencesManager.getInstance().readInt(BravePreferenceKeys.BRAVE_APP_OPEN_COUNT) == 1) {
            Calendar calender = Calendar.getInstance();
            calender.setTime(new Date());
            calender.add(Calendar.DATE, DAYS_12);
            OnboardingPrefManager.getInstance().setNextCrossPromoModalDate(
                calender.getTimeInMillis());
        }

        if (!OnboardingPrefManager.getInstance().isOneTimeNotificationStarted()
                && PackageUtils.isFirstInstall(this)) {
            RetentionNotificationUtil.scheduleNotification(this, RetentionNotificationUtil.DEFAULT_BROWSER_1);
            RetentionNotificationUtil.scheduleNotification(this, RetentionNotificationUtil.DEFAULT_BROWSER_2);
            RetentionNotificationUtil.scheduleNotification(this, RetentionNotificationUtil.DEFAULT_BROWSER_3);
            OnboardingPrefManager.getInstance().setOneTimeNotificationStarted(true);
        }
        if (!TextUtils.isEmpty(BinanceWidgetManager.getInstance().getBinanceAccountBalance())) {
            try {
                BinanceWidgetManager.binanceAccountBalance = new BinanceAccountBalance(
                        BinanceWidgetManager.getInstance().getBinanceAccountBalance());
            } catch (JSONException e) {
                Log.e("NTP", e.getMessage());
            }
        }

        if (PackageUtils.isFirstInstall(this)
                && SharedPreferencesManager.getInstance().readInt(
                           BravePreferenceKeys.BRAVE_APP_OPEN_COUNT)
                        == 1) {
            Calendar calender = Calendar.getInstance();
            calender.setTime(new Date());
            calender.add(Calendar.DATE, DAYS_4);
            BraveRewardsHelper.setNextRewardsOnboardingModalDate(calender.getTimeInMillis());
        }

        if (!isSetDefaultBrowserNotification) {
            BraveSetDefaultBrowserUtils.checkSetDefaultBrowserModal(this);
        }

        checkFingerPrintingOnUpgrade();
        compositorView = null;
        inflatedSettingsBarLayout = null;

        InitBraveWalletService();
        InitKeyringService();
        InitJsonRpcService();
    }

    public void setDormantUsersPrefs() {
    }

    private void showVpnCalloutDialog() {
        BraveVpnCalloutDialogFragment braveVpnCalloutDialogFragment =
                new BraveVpnCalloutDialogFragment();
        braveVpnCalloutDialogFragment.setCancelable(false);
        braveVpnCalloutDialogFragment.show(
                getSupportFragmentManager(), "BraveVpnCalloutDialogFragment");
    }

    public void inflateNewsSettingsBar() {
        // get the main compositor view that we'll use to manipulate the views
        compositorView = findViewById(R.id.compositor_view_holder);
        ViewGroup controlContainer = findViewById(R.id.control_container);
        if (compositorView != null && controlContainer != null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            // inflate the settings bar layout
            View inflatedSettingsBarLayout =
                    inflater.inflate(R.layout.brave_news_settings_bar_layout, null);
            View newContentButtonLayout =
                    inflater.inflate(R.layout.brave_news_load_new_content, null);
            // add the bar to the layout stack
            if (compositorView.findViewById(R.id.news_settings_bar) != null) {
                inflatedSettingsBarLayout = compositorView.findViewById(R.id.news_settings_bar);
            } else {
                compositorView.addView(inflatedSettingsBarLayout, 2);
            }
            inflatedSettingsBarLayout.setAlpha(0f);
            if (compositorView.findViewById(R.id.new_content_layout_id) != null) {
                newContentButtonLayout = compositorView.findViewById(R.id.new_content_layout_id);
            } else {
                compositorView.addView(newContentButtonLayout, 3);
            }
            FrameLayout.LayoutParams inflatedLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(this, 55));

            FrameLayout.LayoutParams newContentButtonLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            int compensation = ConfigurationUtils.isTablet(this)
                    ? (getToolbarShadowHeight() > 3) ? 28 : (dpToPx(this, 12))
                    : getToolbarShadowHeight();
            // position bellow the control_container element (nevigation bar) with
            // compensation
            inflatedLayoutParams.setMargins(0, controlContainer.getBottom() - compensation, 0, 0);
            newContentButtonLayoutParams.setMargins(0, dpToPx(this, 200), 0, 0);
            newContentButtonLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            inflatedSettingsBarLayout.setLayoutParams(inflatedLayoutParams);
            newContentButtonLayout.setLayoutParams(newContentButtonLayoutParams);

            // inflatedSettingsBarLayout.setVisibility(View.VISIBLE);

            compositorView.invalidate();
        }
    }

    public void removeSettingsBar() {
        CompositorViewHolder compositorView = findViewById(R.id.compositor_view_holder);

        if (compositorView != null) {
            View settingsBar = compositorView.getChildAt(2);
            if (settingsBar != null) {
                if (settingsBar.getId() == R.id.news_settings_bar) {
                    compositorView.removeView(settingsBar);
                }
            }
        }
    }

    // Sets NTP background
    public void setBackground(Bitmap bgWallpaper) {
        CompositorViewHolder compositorView = findViewById(R.id.compositor_view_holder);

        if (compositorView != null) {
            ViewGroup root = (ViewGroup) compositorView.getChildAt(1);

            if (root.getChildAt(0) instanceof NestedScrollView) {
                NestedScrollView scrollView = (NestedScrollView) root.getChildAt(0);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int mDeviceHeight = displayMetrics.heightPixels;
                int mDeviceWidth = displayMetrics.widthPixels;

                Glide.with(this)
                        .asBitmap()
                        .load(bgWallpaper)
                        .centerCrop()
                        .override(mDeviceWidth, mDeviceHeight)
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource,
                                    @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);
                                scrollView.setBackground(drawable);
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {}
                        });
            }
        }
    }

    private void checkFingerPrintingOnUpgrade() {
        if (!PackageUtils.isFirstInstall(this)
                && SharedPreferencesManager.getInstance().readInt(
                           BravePreferenceKeys.BRAVE_APP_OPEN_COUNT)
                        == 0) {
            SharedPreferences sharedPreferences = ContextUtils.getAppSharedPreferences();
            boolean value = sharedPreferences.getBoolean(
                    BravePrivacySettings.PREF_FINGERPRINTING_PROTECTION, true);
            if (value) {
                BravePrefServiceBridge.getInstance().setFingerprintingControlType(
                        BraveShieldsContentSettings.DEFAULT);
            } else {
                BravePrefServiceBridge.getInstance().setFingerprintingControlType(
                        BraveShieldsContentSettings.ALLOW_RESOURCE);
            }
        }
    }

    private void openBraveNewsSettings() {
        SettingsLauncher settingsLauncher = new SettingsLauncherImpl();
        settingsLauncher.launchSettingsActivity(this, BraveNewsPreferences.class);
    }

    public void openBraveWallet(boolean fromDapp) {
        Intent braveWalletIntent = new Intent(this, BraveWalletActivity.class);
        braveWalletIntent.putExtra(Utils.IS_FROM_DAPPS, fromDapp);
        braveWalletIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(braveWalletIntent);
    }

    // should only be called if the wallet is setup and unlocked
    public void openNetworkSelection() {
        Intent braveNetworkSelectionIntent = new Intent(this, NetworkSelectorActivity.class);
        braveNetworkSelectionIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(braveNetworkSelectionIntent);
    }

    public void openBraveWalletDAppsActivity(BraveWalletDAppsActivity.ActivityType activityType) {
        Intent braveWalletIntent = new Intent(this, BraveWalletDAppsActivity.class);
        braveWalletIntent.putExtra("activityType", activityType.getValue());
        braveWalletIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(braveWalletIntent);
    }

    private void checkForPresearchSE() {
        TemplateUrl presearchTemplateUrl =
            BraveSearchEngineUtils.getTemplateUrlByShortName(OnboardingPrefManager.PRESEARCH);
        if (presearchTemplateUrl != null) {
            BraveSearchEngineUtils.setDSEPrefs(presearchTemplateUrl, false);
            BraveSearchEngineUtils.setDSEPrefs(presearchTemplateUrl, true);
        }
    }

    private void checkForYandexSE() {
        String countryCode = Locale.getDefault().getCountry();
        if (yandexRegions.contains(countryCode)) {
            TemplateUrl yandexTemplateUrl =
                    BraveSearchEngineUtils.getTemplateUrlByShortName(OnboardingPrefManager.YANDEX);
            if (yandexTemplateUrl != null) {
                BraveSearchEngineUtils.setDSEPrefs(yandexTemplateUrl, false);
                BraveSearchEngineUtils.setDSEPrefs(yandexTemplateUrl, true);
            }
        } else {
            checkForPresearchSE();
        }
    }

    private void checkForNotificationData() {
        Intent notifIntent = getIntent();
        if (notifIntent != null && notifIntent.getStringExtra(RetentionNotificationUtil.NOTIFICATION_TYPE) != null) {
            String notificationType = notifIntent.getStringExtra(RetentionNotificationUtil.NOTIFICATION_TYPE);
            switch (notificationType) {
            case RetentionNotificationUtil.DEFAULT_BROWSER_1:
            case RetentionNotificationUtil.DEFAULT_BROWSER_2:
            case RetentionNotificationUtil.DEFAULT_BROWSER_3:
                if (!BraveSetDefaultBrowserUtils.isBraveSetAsDefaultBrowser(BraveActivity.this)
                        && !BraveSetDefaultBrowserUtils.isBraveDefaultDontAsk()) {
                    isSetDefaultBrowserNotification = true;
                    BraveSetDefaultBrowserUtils.showBraveSetDefaultBrowserDialog(
                            BraveActivity.this, false);
                }
                break;
            }
        }
    }

    public void checkForBraveStats() {
        if (OnboardingPrefManager.getInstance().isBraveStatsEnabled()) {
            BraveStatsUtil.showBraveStats();
        } else {
            if (getActivityTab() != null && getActivityTab().getUrl().getSpec() != null
                    && !UrlUtilities.isNTPUrl(getActivityTab().getUrl().getSpec())) {
                OnboardingPrefManager.getInstance().setFromNotification(true);
                if (getTabCreator(false) != null) {
                    getTabCreator(false).launchUrl(
                            UrlConstants.NTP_URL, TabLaunchType.FROM_CHROME_UI);
                }
            } else {
                showOnboardingV2(false);
            }
        }
    }

    public void showOnboardingV2(boolean fromStats) {
        try {
            OnboardingPrefManager.getInstance().setNewOnboardingShown(true);
            FragmentManager fm = getSupportFragmentManager();
            HighlightDialogFragment fragment = (HighlightDialogFragment) fm
                                               .findFragmentByTag(HighlightDialogFragment.TAG_FRAGMENT);
            FragmentTransaction transaction = fm.beginTransaction();

            if (fragment != null) {
                transaction.remove(fragment);
            }

            fragment = new HighlightDialogFragment();
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putBoolean(OnboardingPrefManager.FROM_STATS, fromStats);
            fragment.setArguments(fragmentBundle);
            transaction.add(fragment, HighlightDialogFragment.TAG_FRAGMENT);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.e("HighlightDialogFragment", e.getMessage());
        }
    }

    public void hideRewardsOnboardingIcon() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.hideRewardsOnboardingIcon();
        }
    }

    private void createNotificationChannel() {
        Context context = ContextUtils.getApplicationContext();
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Presearch Browser";
            String description = "Notification channel for Presearch Browser";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean isNoRestoreState() {
        return ContextUtils.getAppSharedPreferences().getBoolean(PREF_CLOSE_TABS_ON_EXIT, false);
    }

    private boolean isClearBrowsingDataOnExit() {
        return ContextUtils.getAppSharedPreferences().getBoolean(PREF_CLEAR_ON_EXIT, false);
    }

    public void OnRewardsPanelDismiss() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.onRewardsPanelDismiss();
        }
    }

    public void dismissRewardsPanel() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.dismissRewardsPanel();
        }
    }

    public void dismissShieldsTooltip() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.dismissShieldsTooltip();
        }
    }

    public void openRewardsPanel() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.openRewardsPanel();
        }
    }

    public void openBraveTalkOptInPopup(BraveTalkOptInPopupListener popupListener) {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.openBraveTalkOptInPopup(popupListener);
        }
    }

    public void onBraveTalkOptInPopupDismiss() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.onBraveTalkOptInPopupDismiss();
        }
    }

    public void closeBraveTalkOptInPopup() {
        BraveToolbarLayoutImpl layout = (BraveToolbarLayoutImpl) findViewById(R.id.toolbar);
        assert layout != null;
        if (layout != null) {
            layout.closeBraveTalkOptInPopup();
        }
    }

    public Tab selectExistingTab(String url) {
        Tab tab = getActivityTab();
        if (tab != null && tab.getUrl().getSpec().equals(url)) {
            return tab;
        }

        TabModel tabModel = getCurrentTabModel();
        int tabIndex = TabModelUtils.getTabIndexByUrl(tabModel, url);

        // Find if tab exists
        if (tabIndex != TabModel.INVALID_TAB_INDEX) {
            tab = tabModel.getTabAt(tabIndex);
            // Set active tab
            tabModel.setIndex(tabIndex, TabSelectionType.FROM_USER, false);
            return tab;
        } else {
            return null;
        }
    }

    public Tab openNewOrSelectExistingTab(String url) {
        String homePageUrl = HomepageManager.getHomepageUri();
        if (url.equals(UrlConstants.NTP_URL)) {
            url = ( TextUtils.isEmpty(homePageUrl) || "chrome://newtab/".equals(homePageUrl)) ? 
                    "https://presearch.com" : homePageUrl;
        }

        TabModel tabModel = getCurrentTabModel();
        int tabRewardsIndex = TabModelUtils.getTabIndexByUrl(tabModel, url);
        Tab tab = selectExistingTab(url);
        if (tab != null) {
            return tab;
        } else { // Open a new tab
            return getTabCreator(false).launchUrl(url, TabLaunchType.FROM_CHROME_UI);
        }
    }

    private void showBraveRateDialog() {
        RateDialogFragment mRateDialogFragment = new RateDialogFragment();
        mRateDialogFragment.setCancelable(false);
        mRateDialogFragment.show(getSupportFragmentManager(), "RateDialogFragment");
    }

    private void showCrossPromotionalDialog() {
        CrossPromotionalModalDialogFragment mCrossPromotionalModalDialogFragment = new CrossPromotionalModalDialogFragment();
        mCrossPromotionalModalDialogFragment.setCancelable(false);
        mCrossPromotionalModalDialogFragment.show(getSupportFragmentManager(), "CrossPromotionalModalDialogFragment");
    }

    public void showDormantUsersEngagementDialog(String notificationType) {
        if (!BraveSetDefaultBrowserUtils.isBraveSetAsDefaultBrowser(BraveActivity.this)
                && !BraveSetDefaultBrowserUtils.isBraveDefaultDontAsk()) {
            DormantUsersEngagementDialogFragment dormantUsersEngagementDialogFragment =
                    new DormantUsersEngagementDialogFragment();
            dormantUsersEngagementDialogFragment.setCancelable(false);
            dormantUsersEngagementDialogFragment.setNotificationType(notificationType);
            dormantUsersEngagementDialogFragment.show(
                    getSupportFragmentManager(), "DormantUsersEngagementDialogFragment");
            setDormantUsersPrefs();
        }
    }

    static public ChromeTabbedActivity getChromeTabbedActivity() {
        for (Activity ref : ApplicationStatus.getRunningActivities()) {
            if (!(ref instanceof ChromeTabbedActivity)) continue;

            return (ChromeTabbedActivity)ref;
        }

        return null;
    }

    static public BraveActivity getBraveActivity() {
        for (Activity ref : ApplicationStatus.getRunningActivities()) {
            if (!(ref instanceof BraveActivity)) continue;

            return (BraveActivity)ref;
        }

        return null;
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkForNotificationData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK &&
                (requestCode == VERIFY_WALLET_ACTIVITY_REQUEST_CODE ||
                 requestCode == USER_WALLET_ACTIVITY_REQUEST_CODE ||
                 requestCode == SITE_BANNER_REQUEST_CODE) ) {
            dismissRewardsPanel();
            if (data != null) {
                String open_url = data.getStringExtra(BraveActivity.OPEN_URL);
                if (!TextUtils.isEmpty(open_url)) {
                    openNewOrSelectExistingTab(open_url);
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == MONTHLY_CONTRIBUTION_REQUEST_CODE) {
            dismissRewardsPanel();

        } else if (resultCode == RESULT_OK
                && requestCode == BraveSetDefaultBrowserUtils.DEFAULT_BROWSER_ROLE_REQUEST_CODE) {
            BraveSetDefaultBrowserUtils.setBraveDefaultSuccess();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == BraveStatsUtil.SHARE_STATS_WRITE_EXTERNAL_STORAGE_PERM
                && grantResults.length != 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            BraveStatsUtil.shareStats(R.layout.brave_stats_share_layout);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Disable background ads on Android. Issue #8641.
     */
    private void setBgBraveAdsDefaultOff() {
        SharedPreferences sharedPreferences =
            ContextUtils.getAppSharedPreferences();
        boolean exists = sharedPreferences.contains(
                             BraveRewardsPreferences.PREF_ADS_SWITCH_DEFAULT_HAS_BEEN_SET);
        if (!exists) {
            SharedPreferences.Editor sharedPreferencesEditor =
                sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(
                BraveRewardsPreferences.PREF_ADS_SWITCH, false);
            sharedPreferencesEditor.putBoolean(
                BraveRewardsPreferences.PREF_ADS_SWITCH_DEFAULT_HAS_BEEN_SET, true);
            sharedPreferencesEditor.apply();
        }
    }
    @Override
    protected void onPreCreate() {
        // String dataPath = ContextUtils.getApplicationContext().getApplicationInfo().dataDir + File.separator
        //             + "app_chrome" + File.separator + "cffkpbalmllkdoenhmdmpbkajipdjfam"
        //             + File.separator + "1.0.1344";
        // File adblock_ext_dir = new File(dataPath);
        // adblock_ext_dir.mkdirs();

        // if (!fileExists(ContextUtils.getApplicationContext(), "rs-ABPFilterParserData.dat")) {
        //     loadAdblockFilter(R.raw.FilterParserData, "rs-ABPFilterParserData.dat");
        //     loadAdblockFilter(R.raw.regional_catalog_json, "regional_catalog.json");
        //     loadAdblockFilter(R.raw.resources_json,  "resources.json" );
        //     loadAdblockFilter(R.raw.manifest_json, "manifest.json");
        //     loadAdblockFilter(R.raw.manifest_fingerprint, "manifest.fingerprint");
        // }
        super.onPreCreate();
    }
    @Override
    public void performPreInflationStartup() {
        BraveDbUtil dbUtil = BraveDbUtil.getInstance();

        if (dbUtil.dbOperationRequested()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
            .setMessage(dbUtil.performDbExportOnStart() ? "Exporting database, please wait..."
                        : "Importing database, please wait...")
            .setCancelable(false)
            .create();
            dialog.setCanceledOnTouchOutside(false);
            if (dbUtil.performDbExportOnStart()) {
                dbUtil.setPerformDbExportOnStart(false);
                dbUtil.ExportRewardsDb(dialog);
            } else if (dbUtil.performDbImportOnStart() && !dbUtil.dbImportFile().isEmpty()) {
                dbUtil.setPerformDbImportOnStart(false);
                dbUtil.ImportRewardsDb(dialog, dbUtil.dbImportFile());
            }
            dbUtil.cleanUpDbOperationRequest();
        }
        super.performPreInflationStartup();
    }

    @Override
    protected @LaunchIntentDispatcher.Action int maybeDispatchLaunchIntent(
        Intent intent, Bundle savedInstanceState) {
        boolean notificationUpdate = IntentUtils.safeGetBooleanExtra(
                                         intent, BravePreferenceKeys.BRAVE_UPDATE_EXTRA_PARAM, false);
        if (notificationUpdate) {
            SetUpdatePreferences();
        }

        return super.maybeDispatchLaunchIntent(intent, savedInstanceState);
    }

    private void SetUpdatePreferences() {
        Calendar currentTime = Calendar.getInstance();
        long milliSeconds = currentTime.getTimeInMillis();

        SharedPreferences sharedPref =
            getApplicationContext().getSharedPreferences(
                BravePreferenceKeys.BRAVE_NOTIFICATION_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong(BravePreferenceKeys.BRAVE_MILLISECONDS_NAME, milliSeconds);
        editor.apply();
    }

    public ObservableSupplier<BrowserControlsManager> getBrowserControlsManagerSupplier() {
        return mBrowserControlsManagerSupplier;
    }

    public int getToolbarShadowHeight() {
        View toolbarShadow = findViewById(R.id.toolbar_shadow);
        assert toolbarShadow != null;
        if (toolbarShadow != null) {
            return toolbarShadow.getHeight();
        }
        return 0;
    }

    public int getToolbarBottom() {
        View toolbarShadow = findViewById(R.id.toolbar_shadow);
        assert toolbarShadow != null;
        if (toolbarShadow != null) {
            return toolbarShadow.getBottom();
        }
        return 0;
    }

    @NativeMethods
    interface Natives {
        void restartStatsUpdater();
    }

    @Override
    public void initializeCompositor() {
        super.initializeCompositor();

        BraveTabUiFeatureUtilities.maybeOverrideEnableTabGroupAutoCreationPreference(
                ContextUtils.getApplicationContext());
    }

    @Override
    protected boolean supportsDynamicColors() {
        // Dynamic colors cause styling issues with Brave theme.
        return false;
    }
}
