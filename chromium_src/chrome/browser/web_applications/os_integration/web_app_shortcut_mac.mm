/* Copyright (c) 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

namespace base {
class FilePath;
}  // namespace base

namespace {
base::FilePath GetLocalizableBraveAppShortcutsSubdirName();
}

#include "src/chrome/browser/web_applications/os_integration/web_app_shortcut_mac.mm"

namespace {
base::FilePath GetLocalizableBraveAppShortcutsSubdirName() {
  // clang-format off
  static const char kBraveBrowserDevelopmentAppDirName[] =
      "Presearch Browser Development Apps.localized";
  static const char kBraveBrowserAppDirName[] =
      "Presearch Browser Apps.localized";
  static const char kBraveBrowserBetaAppDirName[] =
      "Presearch Browser Beta Apps.localized";
  static const char kBraveBrowserDevAppDirName[] =
      "Presearch Browser Dev Apps.localized";
  static const char kBraveBrowserNightlyAppDirName[] =
      "Presearch Browser Nightly Apps.localized";
  // clang-format on

  switch (chrome::GetChannel()) {
    case version_info::Channel::STABLE:
      return base::FilePath(kBraveBrowserAppDirName);
    case version_info::Channel::BETA:
      return base::FilePath(kBraveBrowserBetaAppDirName);
    case version_info::Channel::DEV:
      return base::FilePath(kBraveBrowserDevAppDirName);
    case version_info::Channel::CANARY:
      return base::FilePath(kBraveBrowserNightlyAppDirName);
    case version_info::Channel::UNKNOWN:
      return base::FilePath(kBraveBrowserDevelopmentAppDirName);
    default:
      NOTREACHED();
      return base::FilePath(kBraveBrowserDevelopmentAppDirName);
  }
}
}  // namespace
