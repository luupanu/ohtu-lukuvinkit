let readingTipsList,
  individualTips

const submitForm = (checkbox) => {
  const form = checkbox.parentNode
  form.submit()
};

const toggleComments = (link) => {
  const comments = link
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

let filterRead = () => {
  let i,
    read,
    hideCheck

  hideCheck = document.getElementById("hideCheck")
  readingTipsList = document.getElementById("readingTipsList")
  individualTips = readingTipsList.getElementsByTagName("article")

  /*Bug: 1. read tips are hidden 2. another tip is marked as read
  3. tips hidden by this function are shown again in the view
  this is because POST-call to index and page refresh?*/
  // fix: make READ hide function call on page load? Goes through all tips and
  // checks if they've been read. If so, hide them.
  for (i = 0; i < individualTips.length; i++) {
    read = individualTips[i].getElementsByClassName("readingtips-read")
    if (read.item(0).textContent.trim().indexOf("Mark as unread") > -1) {
      individualTips[i].style.display = "none"
      individualTips[i].dataset.marked = "true"
    }
  }

  // If tips have been hidden, make next click reload page.
  if (hideCheck.textContent == "Hide read") {
    hideCheck.textContent = "Show all"
  } else {
    location.reload()
  }
}

let searchInput = () => {
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