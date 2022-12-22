/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import * as React from 'react'
import { bindActionCreators, Dispatch } from 'redux'
import { connect } from 'react-redux'

// Feature-specific components
import { Page } from '../components'

// Component groups
import WelcomeBox from './screens/welcomeBox'

// Utils
import * as welcomeActions from '../actions/welcome_actions'

// Assets
import '../../../ui/webui/resources/fonts/muli.css'
import '../../../ui/webui/resources/fonts/poppins.css'
import 'emptykit.css'

interface Props {
  welcomeData: Welcome.State
  actions: any
}

export interface State {
  finished: boolean
  skipped: boolean
}

export class WelcomePage extends React.Component<Props, State> {
  constructor (props: Props) {
    super(props)
    this.state = {
      finished: false,
      skipped: false,
    }
  }

  onClickLetsGo = () => {
    this.setState({
      finished: true
    })
    this.props.actions.goToTabRequested('chrome://newtab', '_self')
  }

  render () {

    return (
        <Page id='welcomePage'>
          <WelcomeBox onClick={this.onClickLetsGo} />            
        </Page>
    )
  }
}

export const mapStateToProps = (state: Welcome.ApplicationState) => ({
  welcomeData: state.welcomeData
})

export const mapDispatchToProps = (dispatch: Dispatch) => ({
  actions: bindActionCreators(welcomeActions, dispatch)
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WelcomePage)
