/* Copyright (c) 2021 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#ifndef BRAVE_BROWSER_UI_VIEWS_CRASH_REPORT_PERMISSION_ASK_DIALOG_VIEW_H_
#define BRAVE_BROWSER_UI_VIEWS_CRASH_REPORT_PERMISSION_ASK_DIALOG_VIEW_H_

#include <memory>

#include "ui/views/window/dialog_delegate.h"

class Browser;

class CrashReportPermissionAskDialogView {
 public:
  static void Show(Browser* browser);
};

#endif  // BRAVE_BROWSER_UI_VIEWS_CRASH_REPORT_PERMISSION_ASK_DIALOG_VIEW_H_
