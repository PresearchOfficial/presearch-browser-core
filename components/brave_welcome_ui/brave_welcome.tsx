/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import * as React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'

import welcomeDarkTheme from './theme/welcome-dark'
import welcomeLightTheme from './theme/welcome-light'
import BraveCoreThemeProvider from '../common/BraveCoreThemeProvider'

// Components
import App from './containers/app'

// Utils
import store from './store'

function initialize () {
  new Promise(resolve => chrome.braveTheme.getBraveThemeType(resolve))
  .then((themeType: chrome.braveTheme.ThemeType) => {
    render(
      <Provider store={store}>
        <BraveCoreThemeProvider
          initialThemeType={themeType}
          dark={welcomeDarkTheme}
          light={welcomeLightTheme}
        >
          <App />
        </BraveCoreThemeProvider>
      </Provider>,
      document.getElementById('root'))
  })
  .catch((error) => {
    console.error('Problem mounting brave welcome', error)
  })
}

document.addEventListener('DOMContentLoaded', initialize)
