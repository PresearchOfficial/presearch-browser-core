/* Copyright (c) 2020 The Presearch Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "brave/third_party/blink/renderer/modules/brave/navigator_presearch.h"

#include "third_party/blink/renderer/core/frame/navigator.h"
#include "brave/third_party/blink/renderer/modules/brave/presearch.h"

namespace blink {

NavigatorPresearch::NavigatorPresearch(Navigator& navigator)
    : Supplement<Navigator>(navigator) {}

// static
const char NavigatorPresearch::kSupplementName[] = "NavigatorPresearch";

NavigatorPresearch& NavigatorPresearch::From(Navigator& navigator) {
  NavigatorPresearch* supplement =
      Supplement<Navigator>::From<NavigatorPresearch>(navigator);
  if (!supplement) {
    supplement = MakeGarbageCollected<NavigatorPresearch>(navigator);
    ProvideTo(navigator, supplement);
  }
  return *supplement;
}

Presearch* NavigatorPresearch::presearch(Navigator& navigator) {
  return NavigatorPresearch::From(navigator).presearch();
}

Presearch* NavigatorPresearch::presearch() {
  if (!presearch_) {
    presearch_ = MakeGarbageCollected<Presearch>();
  }
  return presearch_;
}

void NavigatorPresearch::Trace(blink::Visitor* visitor) const {
  visitor->Trace(presearch_);
  Supplement<Navigator>::Trace(visitor);
}

}  // namespace blink
