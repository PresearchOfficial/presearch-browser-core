/* Copyright (c) 2022 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "ios/chrome/browser/net/ios_chrome_network_delegate.h"

#define IOSChromeNetworkDelegate IOSChromeNetworkDelegate_ChromiumImpl
#include "src/ios/chrome/browser/net/ios_chrome_network_delegate.cc"
#undef IOSChromeNetworkDelegate

#include "brave/common/brave_services_key.h"
#include "brave/common/brave_services_key_helper.h"
#include "brave/common/network_constants.h"

IOSChromeNetworkDelegate::~IOSChromeNetworkDelegate() = default;

int IOSChromeNetworkDelegate::OnBeforeURLRequest(
    net::URLRequest* request,
    net::CompletionOnceCallback callback,
    GURL* new_url) {
  const auto result = IOSChromeNetworkDelegate_ChromiumImpl::OnBeforeURLRequest(
      request, std::move(callback), new_url);
  return result;
}
