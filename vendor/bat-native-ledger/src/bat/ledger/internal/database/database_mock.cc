/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "bat/ledger/internal/database/database_mock.h"

namespace ledger {
namespace database {

MockDatabase::MockDatabase(LedgerImpl* ledger) :
    Database(ledger)  {
}

MockDatabase::~MockDatabase() = default;

}  // namespace database
}  // namespace ledger
