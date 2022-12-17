/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import * as React from 'react'

// Feature-specific components
import { Title, Paragraph, PrimaryButton } from '../../components'

// Shared components
import { ArrowRightIcon } from 'brave-ui/components/icons'

// Images
import { WelcomeLionImage } from '../../components/images'

// Utils
import { getLocale } from '../../../common/locale'
import { Panel } from 'components/brave_welcome_ui/components/wrapper'

interface Props {
  onClick: () => void
}

export default class ThemingBox extends React.PureComponent<Props, {}> {
  render () {
    const { onClick } = this.props
    return (
      <Panel>
        <WelcomeLionImage />
        <Title>{getLocale('welcome')}</Title>
        <Paragraph>{getLocale('whatIsBrave')}</Paragraph>
        <PrimaryButton
          level='primary'
          type='accent'
          size='large'
          text={getLocale('letsGo')}
          onClick={onClick}
          icon={{ position: 'after', image: <ArrowRightIcon /> }}
        />
      </Panel>
    )
  }
}
