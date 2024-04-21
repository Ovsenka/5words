let allWords=[]
let secret
let curLine = 0
let curPos = 0
let curWord = ''
let triedWords = []
let complete = false
const keyboardButtons = document.querySelectorAll('.keyboard__button')
const controlButtons = [...document.querySelectorAll('.keyboard__button_big')]
const backButton = controlButtons[0]
const okButton = controlButtons[1]
const lines = document.querySelectorAll('.field__line')
const stats = document.querySelectorAll('.stats p')
initLocalStorage()
getWords().then(()=>{
  console.log('words get')
  newGame(true)
})
// обработчики экранной клавиатуры
keyboardButtons.forEach(el => el.addEventListener('click', l => letterPressed(l)))

// привязка клавиш к клавиатуре
// todo запретить пока не закрыта инструкция
const alphabet = 'йцукенгшщзхъфывапролджэячсмитьбю'
document.addEventListener('keydown', event => {
  if (alphabet.includes(event.key)){
    keyboardButtons.forEach(el => {
      if (el.innerHTML == event.key && curPos < 5)
        el.classList.add('letter_type_current-row')
    })
  }
});
document.addEventListener('keyup', event => {
  if (alphabet.includes(event.key) ){
    keyboardButtons.forEach(el => {
      if (el.innerHTML == event.key) {
        el.click()
      }
    })
  } else if ('Backspace' == event.key){
    backButton.click()
  } else if ('Enter' == event.key){
    okButton.click()
  }
});

