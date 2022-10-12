/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import { Reducer } from 'redux'

// Constants
import { loadTimeData } from '../../common/loadTimeData'
import { types } from '../constants/welcome_types'

// Utils
import * as storage from '../storage'

const welcomeReducer: Reducer<Welcome.State | undefined> = (state: Welcome.State | undefined, action: any) => {
  if (state === undefined) {
    state = storage.load()
  }

  const payload = action.payload
  const startingState = state
  switch (action.type) {
    case types.GO_TO_TAB_REQUESTED:
      window.open(payload.url, payload.target)
      break
    case types.CLOSE_TAB_REQUESTED:
      window.close()
      break
  }

  if (state !== startingState) {
    storage.debouncedSave(state)
  }

  return state
}

export default welcomeReducer
