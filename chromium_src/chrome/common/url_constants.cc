/* Copyright 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "chrome/common/url_constants.h"

#include "build/branding_buildflags.h"
#include "build/build_config.h"
#include "chrome/common/webui_url_constants.h"

namespace chrome {

const char kAccessCodeCastLearnMoreURL[] = "https://support.presearch.io/";

const char kAccessibilityLabelsLearnMoreURL[] =
    "https://support.presearch.io/";

const char kAutomaticSettingsResetLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017903152-How-do-I-reset-Brave-settings-to-default-";

const char kAdvancedProtectionDownloadLearnMoreURL[] =
    "https://support.presearch.io";

const char kBluetoothAdapterOffHelpURL[] =
    "https://support.presearch.io/";

const char kCastCloudServicesHelpURL[] =
    "https://support.presearch.io/";

const char kCastNoDestinationFoundURL[] =
    "https://support.presearch.io/";

const char kChooserHidOverviewUrl[] =
    "https://github.com/brave/brave-browser/wiki/Web-API-Permissions";

const char kChooserSerialOverviewUrl[] =
    "https://github.com/brave/brave-browser/wiki/Web-API-Permissions";

const char kChooserUsbOverviewURL[] =
    "https://github.com/brave/brave-browser/wiki/Web-API-Permissions";

const char kChromeBetaForumURL[] =
    "https://community.brave.com/c/beta-builds";

const char kChromeFixUpdateProblems[] =
    "https://support.presearch.io/";

const char kChromeHelpViaKeyboardURL[] =
    "https://support.presearch.io/";

const char kChromeHelpViaMenuURL[] =
    "https://support.presearch.io/";

const char kChromeHelpViaWebUIURL[] =
    "https://support.presearch.io/";

const char kChromeNativeScheme[] = "chrome-native";

const char kChromeSearchLocalNtpHost[] = "local-ntp";

const char kChromeSearchMostVisitedHost[] = "most-visited";
const char kChromeSearchMostVisitedUrl[] = "chrome-search://most-visited/";

const char kChromeUIUntrustedNewTabPageBackgroundUrl[] =
    "chrome-untrusted://new-tab-page/background.jpg";
const char kChromeUIUntrustedNewTabPageBackgroundFilename[] = "background.jpg";

const char kChromeSearchRemoteNtpHost[] = "remote-ntp";

const char kChromeSearchScheme[] = "chrome-search";

const char kChromeUIUntrustedNewTabPageUrl[] =
    "chrome-untrusted://new-tab-page/";

const char kChromiumProjectURL[] = "https://github.com/brave/brave-browser/";

const char kContentSettingsExceptionsLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018205431-How-do-I-change-site-permissions-";

const char kCookiesSettingsHelpCenterURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018205431-How-do-I-change-site-permissions-";

const char kCrashReasonURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018192251-How-do-I-fix-page-crashes-and-other-page-loading-errors-";

const char kCrashReasonFeedbackDisplayedURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018192251-How-do-I-fix-page-crashes-and-other-page-loading-errors-";

const char kDoNotTrackLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017905612-How-do-I-turn-Do-Not-Track-on-or-off-";

const char kDownloadInterruptedLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018192491-How-do-I-fix-file-download-errors-";

const char kDownloadScanningLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018192491-How-do-I-fix-file-download-errors-";

const char kExtensionControlledSettingLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018185651-How-do-I-stop-extensions-from-changing-my-settings-";

const char kExtensionInvalidRequestURL[] = "chrome-extension://invalid/";

const char kFlashDeprecationLearnMoreURL[] =
    "https://blog.chromium.org/2017/07/so-long-and-thanks-for-all-flash.html";

const char kGoogleAccountActivityControlsURL[] =
    "https://support.presearch.io/";

const char kGoogleAccountActivityControlsURLInPrivacyGuide[] =
    "https://support.presearch.io/";

const char kGoogleAccountURL[] = "https://support.presearch.io/";

const char kGoogleAccountChooserURL[] = "https://support.presearch.io/";

const char kGoogleAccountDeviceActivityURL[] = "https://support.presearch.io/";

const char kGooglePasswordManagerURL[] = "https://support.presearch.io";

const char kLearnMoreReportingURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017905872-How-do-I-enable-or-disable-automatic-crash-reporting-";

const char kManagedUiLearnMoreUrl[] = "https://support.presearch.io/";

const char kMixedContentDownloadBlockingLearnMoreUrl[] =
    "https://support.presearch.io/";

const char kMyActivityUrlInClearBrowsingData[] =
    "https://support.presearch.io/";

const char kOmniboxLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017479752-How-do-I-set-my-default-search-engine-";

const char kPageInfoHelpCenterURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018185871-How-do-I-check-if-a-site-s-connection-is-secure-";

const char kPasswordCheckLearnMoreURL[] = "https://support.presearch.io/";

const char kPasswordGenerationLearnMoreURL[] = "https://support.presearch.io/";

const char kPasswordManagerLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018185951-How-do-I-use-the-built-in-password-manager-";

const char kPaymentMethodsURL[] = "https://support.presearch.io";

const char kPaymentMethodsLearnMoreURL[] =
    "https://support.presearch.io";

const char kPrivacyLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017989132-How-do-I-change-my-Privacy-Settings-";

const char kRemoveNonCWSExtensionURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017914832-Why-am-I-seeing-the-message-extensions-disabled-by-Brave-";

const char kResetProfileSettingsLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017903152-How-do-I-reset-Brave-settings-to-default-";

const char kSafeBrowsingHelpCenterURL[] =
    "https://support.presearch.io/";

const char kSafetyTipHelpCenterURL[] =
    "https://support.presearch.io/";

const char kSearchHistoryUrlInClearBrowsingData[] =
    "https://support.presearch.io/";

const char kSeeMoreSecurityTipsURL[] =
    "https://support.presearch.io/";

const char kSettingsSearchHelpURL[] =
    "https://support.presearch.io/";

const char kSyncAndGoogleServicesLearnMoreURL[] =
    "https://support.presearch.io/";

const char kSyncEncryptionHelpURL[] =
    "https://support.presearch.io/";

const char kSyncErrorsHelpURL[] =
    "https://support.presearch.io/";

const char kSyncGoogleDashboardURL[] =
    "https://support.presearch.io/";

const char kSyncLearnMoreURL[] =
    "https://support.presearch.io/";

#if !BUILDFLAG(IS_ANDROID)
const char kSyncTrustedVaultOptInURL[] = "https://support.presearch.io/";
#endif

const char kSyncTrustedVaultLearnMoreURL[] = "https://support.presearch.io/";

const char kUpgradeHelpCenterBaseURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360025390311-How-do-I-download-and-install-Brave-";

const char kWhoIsMyAdministratorHelpURL[] =
    "https://support.presearch.io/";

const char kCwsEnhancedSafeBrowsingLearnMoreURL[] =
    "https://support.presearch.io/";

#if BUILDFLAG(IS_ANDROID)
const char kEnhancedPlaybackNotificationLearnMoreURL[] =
// Keep in sync with chrome/android/java/strings/android_chrome_strings.grd
    "https://community.brave.com";
#endif

#if BUILDFLAG(IS_MAC)
const char kChromeEnterpriseSignInLearnMoreURL[] =
    "https://support.presearch.io/";

const char kMacOsObsoleteURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360025390311-How-do-I-download-and-install-Brave-";
#endif

#if BUILDFLAG(IS_WIN)
const char kChromeCleanerLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360017884152-How-do-I-remove-unwanted-ads-pop-ups-malware-";

const char kWindowsXPVistaDeprecationURL[] =
    "https://support.presearch.io/";
#endif

#if BUILDFLAG(ENABLE_ONE_CLICK_SIGNIN)
const char kChromeSyncLearnMoreURL[] =
    "https://support.presearch.io/";
#endif  // BUILDFLAG(ENABLE_ONE_CLICK_SIGNIN)

#if BUILDFLAG(ENABLE_PLUGINS)
const char kOutdatedPluginLearnMoreURL[] =
    "https://support.presearch.io/hc/en-us/articles/"
    "360018163151-How-do-I-manage-Flash-audio-video-";
#endif

#if BUILDFLAG(IS_WIN) || BUILDFLAG(IS_MAC) || BUILDFLAG(IS_LINUX)
const char kChromeAppsDeprecationLearnMoreURL[] =
    "https://support.google.com/chrome/?p=chrome_app_deprecation";
#endif

}  // namespace chrome
