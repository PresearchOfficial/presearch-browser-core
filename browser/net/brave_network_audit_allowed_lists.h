/* Copyright (c) 2021 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#ifndef BRAVE_BROWSER_NET_BRAVE_NETWORK_AUDIT_ALLOWED_LISTS_H_
#define BRAVE_BROWSER_NET_BRAVE_NETWORK_AUDIT_ALLOWED_LISTS_H_

#include <string>

namespace brave {

// Before adding to this list, get approval from the security team.
constexpr const char* kAllowedUrlProtocols[] = {
    "chrome-extension", "chrome", "presearch", "file", "data", "blob",
};

// Before adding to this list, get approval from the security team.
constexpr const char* kAllowedUrlPrefixes[] = {
    // allowed because it 307's to https://componentupdater.brave.com
    "https://componentupdater.brave.com/service/update2",
    "https://crxdownload.brave.com/crx/blobs/",

    // Omaha/Sparkle
    "https://updates.bravesoftware.com/",

    // stats/referrals
    "https://laptop-updates.brave.com/",
    "https://laptop-updates-staging.brave.com/",

    // needed for DoH on Mac build machines
    "https://dns.google/dns-query",

    // needed for DoH on Mac build machines
    "https://chrome.cloudflare-dns.com/dns-query",

    // Other
    "https://brave-core-ext.s3.brave.com/",
    "https://presearchbucket.s3.amazonaws.com/", // replace https://brave-core-ext.s3.brave.com/

    "https://dict.brave.com/",
    // "https://go-updater.brave.com/",
    "https://xmamy.info", // replace https://go-updater.brave.com/

    "https://redirector.brave.com/",
    "https://safebrowsing.brave.com/",
    "https://static.brave.com/",
    "https://static1.brave.com/",
};

// Before adding to this list, get approval from the security team.
constexpr const char* kAllowedUrlPatterns[] = {
    // allowed because it's url for fetching super referral's mapping table
    "https://mobile-data.s3.brave.com/superreferrer/map-table.json",
    "https://mobile-data-dev.s3.brave.software/superreferrer/map-table.json",
};

}  // namespace brave

#endif  // BRAVE_BROWSER_NET_BRAVE_NETWORK_AUDIT_ALLOWED_LISTS_H_
