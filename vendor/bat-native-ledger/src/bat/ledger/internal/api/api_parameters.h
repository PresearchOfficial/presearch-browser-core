/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

#ifndef BRAVELEDGER_API_API_PARAMETERS_H_
#define BRAVELEDGER_API_API_PARAMETERS_H_

#include <memory>
#include <vector>

#include "base/timer/timer.h"
#include "bat/ledger/ledger.h"
#include "bat/ledger/internal/endpoint/api/api_server.h"

namespace ledger {
class LedgerImpl;

namespace api {

class APIParameters {
 public:
  explicit APIParameters(LedgerImpl* ledger);
  ~APIParameters();

  void Initialize();

  void Fetch(ledger::GetRewardsParametersCallback callback);

 private:
  void OnFetch(
      const type::Result result,
      const type::RewardsParameters& parameters);

  void RunCallbacks();

  void SetRefreshTimer(
      base::TimeDelta delay,
      base::TimeDelta base_delay = base::TimeDelta());

  LedgerImpl* ledger_;  // NOT OWNED
  base::OneShotTimer refresh_timer_;
  std::vector<ledger::GetRewardsParametersCallback> callbacks_;
  std::unique_ptr<endpoint::APIServer> api_server_;
};

}  // namespace api
}  // namespace ledger

#endif  // BRAVELEDGER_API_API_PARAMETERS_H_
