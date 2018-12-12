// MAIN FUNCTIONS

// Toggles the read status of this ReadingTip.
const toggleRead = (element) => {
  const id = getParentArticle(element).id
  submitForm("toggleread", { id })
}

// Toggles the visibility of parent class '.commentarea'.
const toggleComments = (element) => {
  const comments = element
    .parentNode
    .querySelector(".commentarea")

  comments.style.display === "block"
    ? comments.style.display = "none"
    : comments.style.display = "block"
}

// Creates a new comment if comment is valid.
const newComment = (element) => {
  const id = getParentArticle(element).id
  const commentDescription = element.previousElementSibling

  // If not a valid comment, give an error message and return.
  if (!commentDescription.checkValidity()) {
    element.nextElementSibling.click()
    return
  }

  const properties = { id, commentDescription: commentDescription.value }

  submitForm("newcomment", properties)
}

// Removes all filters and shows every tip again.
const showAllTips = () => {
  filterCheckboxes.forEach(item => {
    document.getElementById(item).checked = false
  })
  document.getElementById("search").value = ""

  setTimeout(() => {
    document.getElementById("filter-none").checked = false
  }, 100)

  filterTips()
}

// Toggles the visibility of tips based on various filters.
const filterTips = () => {
  const tips = getTips()

  tips.forEach(tip => {
    tip.type = getTipType(tip)
    tip.read = tipIsRead(tip)

    if (!(typeIsHidden(tip.type))
      && someTagIncludesFilter(tip, normalizeString(getFilter()))
        && !(checkBoxIsChecked("filter-read") && tip.read)) {
          tip.style.display = "block"
        } else {
          tip.style.display = "none"
        }
  })

  // Update local storage so that filters stay even with page refresh.
  updateLocalStorage()
}

// Toggles the visibility of form elements based on type.
const newReadingTipFormRefresh = () => {
  const typeField = document.getElementById("typeField")

  const urlField = document.getElementById("urlField")
  const authorField = document.getElementById("authorField")
  const isbnField = document.getElementById("isbnField")
  
  const url = document.getElementById("urlInput")
  const author = document.getElementById("authorInput")
  const isbn = document.getElementById("isbnInput")

  if (typeField.value == "link") {
    author.value = ""
    isbn.value = ""
    urlField.style.display = "block"
    authorField.style.display = "none"
    isbnField.style.display = "none"
  } else if (typeField.value == "article") {
    url.value = ""
    isbn.value = ""
    urlField.style.display = "none"
    authorField.style.display = "block"
    isbnField.style.display = "none"
  } else if (typeField.value == "book") {
    url.value = ""
    urlField.style.display = "none"
    authorField.style.display = "block"
    isbnField.style.display = "block"
  }
}

// Swaps priorities of the dragged ReadingTip with the ReadingTip it was dropped into.
const swapPriorities = (event) => {
  event.preventDefault()

  const dragId = event.dataTransfer.getData("text")
  const dropId = getParentArticle(event.target).id || dragId

  if (dragId === dropId) {
    return
  }

  const properties = {
    id1: dragId,
    id2: dropId
  }

  submitForm("swappriorities", properties)
}

// Updates local storage with things we want to keep between page reloads.
const updateLocalStorage = () => {
  filterCheckboxes.forEach(item => {
    localStorage.setItem(item, checkBoxIsChecked(item))
  })
  localStorage.setItem("search", getFilter())
}

// HELPER FUNCTIONS

// Submits the form with the given id and properties.
const submitForm = (id, properties) => {
  const form = document.getElementById(id)
  const formInputs = [...form]

  // Map the given properties to the values of form inputs with the same name.
  formInputs.forEach(input => {
    if (properties[input.name]) {
      input.value = properties[input.name]
    }
  })

  form.submit()
}

// Checks if given tip is read.
const tipIsRead = (tip) => tip.classList[0] === "read"

// Gets the type of this tip.
const getTipType = (tip) => tip.classList[1]

// Checks if given checkbox is checked.
const checkBoxIsChecked = (checkbox) => document.getElementById(checkbox).checked

// Gets all tips in the document.
const getTips = () => document.getElementById("readingTipsList").querySelectorAll("article")

// Gets the value of the search box in the document.
const getFilter = () => document.getElementById("search").value

// Does some string manipulations in order to be able to compare strings.
const normalizeString = _ => _.trim().toUpperCase()

// Returns true if some tag in the tip includes the filter phrase.
const someTagIncludesFilter = (tip, filter) => {
  if (!filter) {
    return true
  }

  const tags = [...tip.querySelectorAll(".tag")]

  return tags.some(tag => {
    const tagValue = normalizeString(tag.innerText)
    return tagValue.includes(filter)
  })
}

// Checks if a type is being hidden by the checkbox associated with it.
const typeIsHidden = (type) => {
  if (type == "link") {
    return checkBoxIsChecked("filter-links")
  } else if (type == "article") {
    return checkBoxIsChecked("filter-articles")
  }
  return checkBoxIsChecked("filter-books")
}

// Gets parent node of type <article>. Returns null if not found.
const getParentArticle = (element) => {
  return element.nodeName === "ARTICLE" ? element : getParentArticle(element.parentNode)
}

// On drag, transfer this object id.
const onDrag = (event) => {
  event.dataTransfer.effectAllowed = "move" // for a visual 'move' effect
  event.dataTransfer.setData("text", event.target.id)
}

// Prevents default handling of HTML5 ondragover.
const allowDrop = (event) => {
  event.preventDefault()
  event.dataTransfer.dropEffect = "move" // for a visual 'move' effect
}

// EVENT LISTENERS

// Get and set filter status from localStorage before loading the page.
document.addEventListener("DOMContentLoaded", () => {
  filterCheckboxes.forEach(item => {
    const checked = localStorage.getItem(item) == "true"
    document.getElementById(item).checked = checked
  })

  document.getElementById("search").value = localStorage.getItem("search")

  filterTips()
})

// CONSTANTS

const filterCheckboxes = [
  "filter-read",
  "filter-links",
  "filter-articles",
  "filter-books"
]