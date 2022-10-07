import { addons } from '@storybook/addons'
import { create } from '@storybook/theming'

const braveTheme = create({
  base: 'dark',
  brandTitle: 'Presearch Browser UI',
  brandUrl: 'https://github.com/PresearchOfficial/presearch-browser-core'
})

addons.setConfig({
  isFullscreen: false,
  showNav: true,
  showPanel: true,
  panelPosition: 'right',
  theme: braveTheme
})
