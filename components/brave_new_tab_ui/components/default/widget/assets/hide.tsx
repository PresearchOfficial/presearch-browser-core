/* This Source Code Form is subject to the terms of the Mozilla Public
* License. v. 2.0. If a copy of the MPL was not distributed with this file.
* You can obtain one at http://mozilla.org/MPL/2.0/. */

import * as React from 'react'

export default class HideIcon extends React.Component {
  render () {
    return (
      <svg xmlns={'http://www.w3.org/2000/svg'} width={'24'} height={'24'}>
        <path d={'M5.812 19.138a.663.663 0 01-.943 0 .666.666 0 010-.943l2.054-2.054C4.744 14.741 4 12.805 4 12c0-1.741 2.803-6 8-6 2.001 0 3.263.607 3.961 1.103l2.242-2.241a.666.666 0 11.942.942L5.812 19.138zm4.396-6.282l2.653-2.652A1.994 1.994 0 0012 10c-1.103 0-2 .898-2 2 0 .3.08.589.208.856zM12 7.333c-4.523 0-6.667 3.744-6.667 4.667 0 .297.476 1.954 2.562 3.168l1.359-1.358c-.047-.043-.101-.075-.135-.133A3.337 3.337 0 0112 8.667c.66 0 1.293.202 1.835.562l1.162-1.161c-.55-.34-1.502-.735-2.997-.735zm0 8A.667.667 0 0111.956 14 2.198 2.198 0 0014 11.956a.666.666 0 111.33.087 3.537 3.537 0 01-3.288 3.289H12zm-1.84 1.113c.582.146 1.201.22 1.84.22 4.523 0 6.667-3.743 6.667-4.666 0-.279-.289-1.185-1.182-2.204a.667.667 0 111.004-.878C19.407 9.966 20 11.176 20 12c0 1.74-2.803 6-8 6a8.871 8.871 0 01-2.165-.262.667.667 0 11.326-1.292z'} fillRule={'evenodd'} />
      </svg>
    )
  }
}
