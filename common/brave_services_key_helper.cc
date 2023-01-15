/* Copyright (c) 2022 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "brave/common/brave_services_key_helper.h"

#include "brave/common/network_constants.h"
#include "extensions/common/url_pattern.h"
#include "url/gurl.h"

namespace brave {

bool ShouldAddBraveServicesKeyHeader(const GURL& url) {
  return false;
}

}  // namespace brave
