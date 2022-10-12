/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import ITheme from 'brave-ui/theme/theme-interface'
import IThemeWelcomePage from './welcome-theme'
import defaultTheme from 'brave-ui/theme/brave-default'
import colors from 'brave-ui/theme/colors'

const welcomeLightTheme: ITheme & IThemeWelcomePage = {
  ...defaultTheme,
  name: 'Welcome Light',
  color: {
    ...defaultTheme.color,
    text: colors.neutral900,
    panelBackground: '#EAF3FF',
    panelBackgroundSecondary: colors.neutral000,
    outlineColor: 'rgba(45, 142, 255, 0.3)'
  }
}

export default welcomeLightTheme
