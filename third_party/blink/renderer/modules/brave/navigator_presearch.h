// Copyright (c) 2020 The Presearch Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this file,
// you can obtain one at http://mozilla.org/MPL/2.0/.

#ifndef BRAVE_THIRD_PARTY_BLINK_RENDERER_MODULES_BRAVE_NAVIGATOR_PRESEARCH_H_
#define BRAVE_THIRD_PARTY_BLINK_RENDERER_MODULES_BRAVE_NAVIGATOR_PRESEARCH_H_

#include "third_party/blink/renderer/core/frame/navigator.h"
#include "third_party/blink/renderer/platform/bindings/name_client.h"
#include "third_party/blink/renderer/platform/heap/garbage_collected.h"
#include "third_party/blink/renderer/platform/supplementable.h"

namespace blink {

class Presearch;
class Navigator;

class NavigatorPresearch final
    : public GarbageCollected<NavigatorPresearch>,
      public Supplement<Navigator>,
      public NameClient {

 public:
  static const char kSupplementName[];

  static NavigatorPresearch& From(Navigator&);
  static Presearch* presearch(Navigator&);
  Presearch* presearch();

  explicit NavigatorPresearch(Navigator&);

  void Trace(blink::Visitor*) const override;
  const char* NameInHeapSnapshot() const override {
    return "NavigatorPresearch";
  }

 private:
  Member<Presearch> presearch_;
};

}  // namespace blink

#endif  // BRAVE_THIRD_PARTY_BLINK_RENDERER_MODULES_BRAVE_NAVIGATOR_PRESEARCH_H_
