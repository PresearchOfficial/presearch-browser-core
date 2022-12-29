/* This Source Code Form is subject to the terms of the Mozilla Public
 * License. v. 2.0. If a copy of the MPL was not distributed with this file.
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

import styled, { css } from 'styled-components'

const contentMaxWidth = 580
const slideContentHeight = 540
const footerHeight = 52
const footerTopMargin = 24

const BaseGrid = styled('div')<{}>`
  box-sizing: border-box;
  display: grid;
  height: 100%;
`

const BaseColumn = styled('div')<{}>`
  box-sizing: border-box;
  position: relative;
  display: flex;
`

export const SelectGrid = styled(BaseGrid)`
  display: flex;
  height: 100%;
  padding: 0 30px;
  align-items: center;
  width: 80%;
`

export const Footer = styled(BaseGrid.withComponent('footer'))`
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: ${footerHeight}px;
  max-width: 540px;
  margin: ${footerTopMargin}px auto 0;
  height: auto;
`

export const FooterLeftColumn = styled(BaseColumn)`
  align-items: center;
  justify-content: center;
`

export const FooterMiddleColumn = styled(BaseColumn)`
  align-items: center;
  justify-content: center;
`

export const FooterRightColumn = styled(BaseColumn)`
  align-items: center;
  justify-content: center;
`

interface ContentProps {
  active: boolean
}

export const Content = styled('section')<ContentProps>`
  opacity: 0;
  will-change: transform;
  transition: opacity 600ms, transform 600ms ease-in-out;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex: 1;
  max-width: ${contentMaxWidth}px;
  padding: 24px;
  margin: auto;
  /*
    inactive elements need to be absolute positioned to prevent pilling up
    in the background making visible elements misaligned. In this case display:none
    would prevent us from seeing the ransition animation. See comment below.
  */
  position: ${p => p.active ? 'static' : 'absolute'};

  /*
    prevents focus on all content's child elements if the parent is not active.
    this is needed because due to our animation transition, the parent container
    cannot have "display: none" and is using "opacity: 0" instead, making invisible
    elements still accessible via keyboard, creating an a11y issue.
    see https://github.com/brave/brave-browser/issues/5504
  */
  > * {
    display: ${p => !p.active && 'none'};
  }

  ${p => p.active && css`
    opacity: 1;
    transform: translateX(0) scale(1);
  `}
`

export const Page = styled('div')`
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: ${p => p.theme.color.panelBackground};
  overflow-x: hidden;
  padding-top: 70px;
`

export const StyledWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
`

export const SlideContent = styled('div')<{}>`
  display: flex;
  height: ${slideContentHeight}px;
`
