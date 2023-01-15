const config = { attributes: true, childList: true, subtree: true }

const callback = (mutationsList: MutationRecord[], observer: MutationObserver) => {
  const buttons: NodeListOf<Element> = document.querySelectorAll('div.webstore-test-button-label')

  buttons.forEach((button: Element) => {
    button.parentElement?.remove()
  })
}

const observer: MutationObserver = new MutationObserver(callback)

window.onload = () => {
  observer.observe(document, config)
}
