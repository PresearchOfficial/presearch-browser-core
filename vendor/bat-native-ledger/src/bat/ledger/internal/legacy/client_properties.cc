/* Copyright (c) 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "bat/ledger/internal/legacy/client_properties.h"
#include "bat/ledger/internal/constants.h"

namespace ledger {

ClientProperties::ClientProperties()
    : boot_timestamp(0),
      reconcile_timestamp(0),
      fee_amount(0),
      user_changed_fee(false),
      auto_contribute(false),
      rewards_enabled(false) {}

ClientProperties::ClientProperties(
    const ClientProperties& properties) {
  wallet_info = properties.wallet_info;
  boot_timestamp = properties.boot_timestamp;
  reconcile_timestamp = properties.reconcile_timestamp;
  fee_amount = properties.fee_amount;
  user_changed_fee = properties.user_changed_fee;
  auto_contribute = properties.auto_contribute;
  rewards_enabled = properties.rewards_enabled;
  inline_tips = properties.inline_tips;
}

ClientProperties::~ClientProperties() = default;

bool ClientProperties::operator==(
    const ClientProperties& rhs) const {
  return wallet_info == rhs.wallet_info &&
      boot_timestamp == rhs.boot_timestamp &&
      reconcile_timestamp == rhs.reconcile_timestamp &&
      fee_amount == rhs.fee_amount &&
      user_changed_fee == rhs.user_changed_fee &&
      auto_contribute == rhs.auto_contribute &&
      rewards_enabled == rhs.rewards_enabled &&
      inline_tips == rhs.inline_tips;
}

bool ClientProperties::operator!=(
    const ClientProperties& rhs) const {
  return !(*this == rhs);
}

}  // namespace ledger
