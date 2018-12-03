let readingTipsList,
  individualTips

// Submits the parent form of given element.
const submitForm = (element) => {
  const form = element.parentNode
  form.submit()
}

// Toggles the visibility of parent class '.commentarea'.
const toggleComments = (element) => {
  const comments = element
    .parentNode
    .querySelectorAll('.commentarea')

  comments.forEach(comment => {
    if (comment.style.display === 'block') {
      comment.style.display = 'none'
    } else {
      comment.style.display = 'block'
    }
  })
}

// Toggles the visibility of read tips.
const filterRead = () => {
  const hideRead = document.getElementById("filter-read")
  const readTips = [...document.getElementsByClassName("tip-read")]

  readTips.forEach(tip => {
    if (hideRead.checked) {
      tip.style.display = "none"
    } else {
      tip.style.display = "block"
    }
   })
}

const searchInput = () => {
  let input,
    filter,
    i,
    tags

  input = document.getElementById("search")
  filter = input
    .value
    .toUpperCase()

  readingTipsList = document.getElementById("readingTipsList")
  individualTips = readingTipsList.getElementsByTagName("article")

  // Goes through all tips and sees if they match search input. If not, hide them.
  for (i = 0; i < individualTips.length; i++) {
    tags = individualTips[i].getElementsByClassName("readingtips-tags")
    if ((tags.item(0).textContent.trim().toUpperCase().indexOf(filter) > -1) && (individualTips[i].dataset.marked == "false")) {
      individualTips[i].style.display = ""
    } else {
      individualTips[i].style.display = "none"
    }
  }
}