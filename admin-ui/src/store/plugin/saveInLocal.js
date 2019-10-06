// import CircularJSON from 'circular-json'
export default store => {
  // if (localStorage.state) store.replaceState(CircularJSON.parse(localStorage.state))
  if (localStorage.state) store.replaceState(JSON.parse(localStorage.state))
  store.subscribe((mutation, state) => {
    // localStorage.state = CircularJSON.stringify(state)
    localStorage.state = JSON.stringify(state)
  })
}
