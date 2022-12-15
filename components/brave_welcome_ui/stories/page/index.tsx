/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import * as React from 'react'

// Feature-specific components
import { Page } from '../../components'

// Component groups
import WelcomeBox from './screens/welcomeBox'

// Images
import { Background } from '../../components/images'

export interface Props {
  isDefaultSearchGoogle: boolean
}

export default class WelcomePage extends React.PureComponent<Props, State> {
  constructor (props: Props) {
    super(props)
    this.state = {}
  }

  get totalScreensSize () {
    return 5
  }

  onClickLetsGo = () => {
    // fades out
    // fades in to new tab page
  }  

  render () {
    return (
      <>
        <Page>
          <WelcomeBox onClick={this.onClickLetsGo} />
          <Background />
        </Page>
      </>
    )
  }
}
