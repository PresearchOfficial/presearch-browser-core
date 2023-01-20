#!/usr/bin/env python3
#
# Copyright (c) 2022 The Brave Authors. All rights reserved.
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this file,
# You can obtain one at http://mozilla.org/MPL/2.0/. */


# Strings we want to replace but that we also replace automatically
# for XTB files
branding_replacements = [
    (r'The Chromium Authors. All rights reserved.',
     r'The Presearch Authors. All rights reserved.'),
    (r'Google LLC. All rights reserved.',
     r'The Presearch Authors. All rights reserved.'),
    (r'The Chromium Authors', r'Presearch Software Inc'),
    (r'Google Chrome', r'Presearch'),
    (r'(Google)(?! Play)', r'Presearch'),
    (r'Chromium', r'Presearch'),
    (r'Chrome', r'Presearch'),
]


# Strings we want to replace but that we need to use Transifex for
# to translate the XTB files
default_replacements = [
    (r'Presearch Web Store', r'Web Store'),
    (r'You\'re incognito', r'This is a private window'),
    (r'an incognito', r'a private'),
    (r'an Incognito', r'a Private'),
    (r'incognito', r'private'),
    (r'Incognito', r'Private'),
    (r'inco&gnito', r'&private'),
    (r'Inco&gnito', r'&Private'),
    (r'People', r'Profiles'),
    # 'people' but only in the context of profiles, not humans.
    (r'(?<!authenticate )people', r'profiles'),
    (r'(Person)(?!\w)', r'Profile'),
    (r'(person)(?!\w)', r'profile'),
    (r'Bookmarks Bar\n', r'Bookmarks\n'),
    (r'Bookmarks bar\n', r'Bookmarks\n'),
    (r'bookmarks bar\n', r'bookmarks\n'),
]


# Fix up some strings after aggressive first round replacement.
fixup_replacements = [
    (r'Presearch Cloud Print', r'Google Cloud Print'),
    (r'Presearch Docs', r'Google Docs'),
    (r'Presearch Drive', r'Google Drive'),
    (r'Presearch OS', r'Chrome OS'),
    (r'PresearchOS', r'ChromeOS'),
    (r'Presearch Safe Browsing', r'Google Safe Browsing'),
    (r'Safe Browsing \(protects you and your device from dangerous sites\)',
     r'Google Safe Browsing (protects you and your device from dangerous sites)'
    ),
    (r'Sends URLs of some pages you visit to Presearch',
     r'Sends URLs of some pages you visit to Google'),
    (r'Google Google', r'Google'),
    (r'Presearch Account', r'Presearch sync chain'),
    (r'Presearch Lens', r'Google Lens'),
    (r'Presearchbook', r'Chromebook'),
    (r'Presearchcast', r'Chromecast'),
    (r'Presearch Cloud', r'Google Cloud'),
    (r'Presearch Pay', r'Google Pay'),
    (r'Presearch Photos', r'Google Photos'),
    (r'Presearch Projects', r'Chromium Projects'),
]


# Replacements for text nodes and neither for inside descriptions nor comments
main_text_only_replacements = [
    # By converting it back first, it makes this idempotent
    ('Copyright \xa9', 'Copyright'),
    ('Copyright', 'Copyright \xa9'),
]
