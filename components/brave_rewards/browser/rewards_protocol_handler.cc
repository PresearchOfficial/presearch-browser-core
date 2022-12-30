/* Copyright (c) 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "brave/components/brave_rewards/browser/rewards_protocol_handler.h"

#include <string>
#include <utility>

#include "base/strings/strcat.h"
#include "base/strings/string_piece_forward.h"
#include "base/strings/string_util.h"
#include "base/task/post_task.h"
#include "bat/ledger/buildflags.h"
#include "brave/components/brave_rewards/common/url_constants.h"
#include "content/public/browser/browser_task_traits.h"
#include "content/public/browser/browser_thread.h"
#include "net/base/escape.h"

namespace brave_rewards {

void HandleRewardsProtocol(const GURL& url,
                           content::WebContents::OnceGetter web_contents_getter,
                           ui::PageTransition page_transition,
                           bool has_user_gesture) {}

}  // namespace brave_rewards
