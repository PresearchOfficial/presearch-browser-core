/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "brave/third_party/blink/renderer/modules/brave/navigator_brave.h"

#include "third_party/blink/renderer/core/frame/navigator.h"
#include "brave/third_party/blink/renderer/modules/brave/brave.h"

namespace blink {

NavigatorBrave::NavigatorBrave(Navigator& navigator)
    : Supplement<Navigator>(navigator) {}

// static
const char NavigatorBrave::kSupplementName[] = "NavigatorBrave";

NavigatorBrave& NavigatorBrave::From(Navigator& navigator) {
  NavigatorBrave* supplement =
      Supplement<Navigator>::From<NavigatorBrave>(navigator);
  if (!supplement) {
    supplement = MakeGarbageCollected<NavigatorBrave>(navigator);
    ProvideTo(navigator, supplement);
  }
  return *supplement;
}

Brave* NavigatorBrave::presearch(Navigator& navigator) {
  return NavigatorBrave::From(navigator).presearch();
}

Brave* NavigatorBrave::presearch() {
  if (!presearch_) {
    presearch_ = MakeGarbageCollected<Brave>();
  }
  return presearch_;
}

void NavigatorBrave::Trace(blink::Visitor* visitor) const {
  visitor->Trace(presearch_);
  Supplement<Navigator>::Trace(visitor);
}

}  // namespace blink
