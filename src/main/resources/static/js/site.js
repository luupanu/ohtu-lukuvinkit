// MAIN FUNCTIONS

// Toggles the read status of this ReadingTip.
const toggleRead = (element) => {
  const id = getParentArticle(element).id
  submitForm('toggleread', { id })
}

// Toggles the visibility of parent class '.commentarea'.
const toggleComments = (element) => {
  const comments = element
    .parentNode
    .querySelectorAll(".commentarea")

  comments.forEach(comment => {
    comment.style.display === "block"
      ? comment.style.display = "none"
      : comment.style.display = "block"
  })
}

// Creates a new comment if comment is valid.
const newComment = (element) => {
  const id = getParentArticle(element).id
  const commentDescription = element.previousElementSibling

  // if not valid comment, give an error message and return
  if (!commentDescription.checkValidity()) {
    element.nextElementSibling.click()
    return
  }

  const properties = { id, commentDescription: commentDescription.value }

  submitForm('newcomment', properties)
}

// Toggles the visibility of tips based on various filters.
const filterTips = () => {
  const tips = getTips()

  tips.forEach(tip => {
    if (!(tipTypeHidden(tip))
      && someTagIncludesFilter(tip, getFilter())
        && !(checkBoxIsChecked("filter-read") && tipIsRead(tip))) {
          tip.style.display = "block"
        } else {
          tip.style.display = "none"
        }
  })
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

  if (typeField.value == "Link") {
    author.value = ""
    isbn.value = ""
    urlField.style.display = "block"
    authorField.style.display = "none"
    isbnField.style.display = "none"
  } else if (typeField.value == "Article") {
    url.value = ""
    isbn.value = ""
    urlField.style.display = "none"
    authorField.style.display = "block"
    isbnField.style.display = "none"
  } else if (typeField.value == "Book") {
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

  if (dragId !== dropId) {
    const properties = {
      id1: dragId,
      id2: dropId
    }

    submitForm('swappriorities', properties)
  }
}

// HELPER FUNCTIONS

// Submits the form with the given id and properties.
const submitForm = (id, properties) => {
  const form = document.getElementById(id)
  const array = [...form]

  // Map the given properties to the values of form inputs with the same name.
  array.forEach(input => {
    if (properties[input.name]) {
      input.value = properties[input.name]
    }
  })

  form.submit()
}

// Checks if given tip is read.
const tipIsRead = (tip) => tip.className === "tip-read"

// Checks if given checkbox is checked.
const checkBoxIsChecked = (checkbox) => document.getElementById(checkbox).checked

// Gets all tips in the document.
const getTips = () => document.getElementById("readingTipsList").querySelectorAll("article")

// Gets the value of the search box in the document.
const getFilter = () => normalizeString(document.getElementById("search").value)

// Does some string manipulations to be able to compare strings.
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

// Returns true if a tip is of a type that's being hidden by a checkbox.
const tipTypeHidden = (tip) => {
    const tipType = tip.querySelector(".tip-type").innerText
    return isTypeHidden(tipType)
}

// Checks if a type is being hidden by the checkbox associated with it.
const isTypeHidden = (type) => {
    if (type == "Link") {
      return checkBoxIsChecked("filter-links")
    } else if (type == "Article") {
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
  event.dataTransfer.effectAllowed = "move" // for a visual move effect
  event.dataTransfer.setData("text", event.target.id)
}

// Prevents default handling of HTML5 ondragover.
const allowDrop = (event) => {
  event.preventDefault()
  event.dataTransfer.dropEffect = "move" // for a visual move effect
}
