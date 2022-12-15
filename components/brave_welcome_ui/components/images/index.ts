/* This Source Code Form is subject to the terms of the Mozilla Public
 * License. v. 2.0. If a copy of the MPL was not distributed with this file.
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import styled, { keyframes } from 'styled-components'

import LionImage from './lion_logo.svg'

const BaseImage = styled('img')<{}>`
  box-sizing: border-box;
  display: block;
  max-width: 100%;
`

export const WelcomeLionImage = styled(BaseImage).attrs({ src: LionImage })`
  height: 140px;
`

export const topToBottom = keyframes`
  from {
    transform: translateY(100%);
  }

  to {
    transform: translateY(0);
  }
`

export const backgroundHeight = 100

export const Background = styled('div')<{}>`
  box-sizing: border-box;
  background-color: rgba(45, 142, 255, 1);
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position-x: center;
`
