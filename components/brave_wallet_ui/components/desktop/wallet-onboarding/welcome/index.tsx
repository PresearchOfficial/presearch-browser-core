import * as React from 'react'

import {
  StyledWrapper,
  Title,
  Description,
  PageIcon,
  RestoreButton,
  Divider,
  ImportButton,
  SettingsButton,
  ImportButtonText,
  MetaMaskIcon,
  CryptoWalletsAlertBox,
  CryptoWalletsAlertTitle,
  CryptoWalletsAlertDescription
} from './style'
import { NavButton } from '../../../extension'
import { getLocale, splitStringForTag } from '../../../../../common/locale'

export interface Props {
  onSetup: () => void
  onRestore: () => void
  onClickImportMetaMask: () => void
  isMetaMaskInitialized: boolean
  isCryptoWalletsInitialized: boolean
}

function OnboardingWelcome (props: Props) {
  const { onRestore, onSetup, onClickImportMetaMask, isMetaMaskInitialized, isCryptoWalletsInitialized } = props

  const onClickSettings = () => {}

  const walletAlertText = getLocale('braveWalletCryptoWalletsDescriptionTwo')
  const { beforeTag, duringTag, afterTag } = splitStringForTag(walletAlertText)

  return (
    <StyledWrapper>
      <PageIcon />
      <Title>{getLocale('braveWalletWelcomeTitle')}</Title>
      <Description>{getLocale('braveWalletWelcomeDescription')}</Description>
      <NavButton buttonType='primary' text={getLocale('braveWalletWelcomeButton')} onSubmit={onSetup} />
      <RestoreButton onClick={onRestore}>{getLocale('braveWalletWelcomeRestoreButton')}</RestoreButton>
      {isMetaMaskInitialized &&
        <>
          <Divider />
          <ImportButton onClick={onClickImportMetaMask}>
            <MetaMaskIcon />
            <ImportButtonText>{getLocale('braveWalletImportTitle').replace('$1', getLocale('braveWalletImportMetaMaskTitle'))}</ImportButtonText>
          </ImportButton>
        </>
      }
      {isCryptoWalletsInitialized &&
        <CryptoWalletsAlertBox>
          <CryptoWalletsAlertTitle>{getLocale('braveWalletCryptoWalletsDetected')}</CryptoWalletsAlertTitle>
          <CryptoWalletsAlertDescription>{getLocale('braveWalletCryptoWalletsDescriptionOne')}</CryptoWalletsAlertDescription>
          <CryptoWalletsAlertDescription>
            {beforeTag}
            <SettingsButton onClick={onClickSettings}>{duringTag}</SettingsButton>
            {afterTag}
          </CryptoWalletsAlertDescription>
        </CryptoWalletsAlertBox>
      }
    </StyledWrapper>
  )
}

export default OnboardingWelcome
