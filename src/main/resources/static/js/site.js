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

function filterRead() {
  let list, articles, i, read

  list = document.getElementById("list")
  articles = list.getElementsByTagName("article")

  /*Bug: 1. read tips are hidden 2. another tip is marked as read
  3. tips hidden by this function are shown again in the view
  this is because POST-call to index and page refresh?*/

  for (i = 0; i < articles.length; i++) {
    read = articles[i].getElementsByClassName("readingtips-read")
    if (articles[i].style.display == "none") {
      articles[i].style.display = ""
    } else if (read.item(0).textContent.trim().indexOf("Mark as unread") > -1) {
      articles[i].style.display = "none"
    }
  }
}

function searchInput() {
  let input, filter, list, articles, i, tags

  input = document.getElementById("search")
  filter = input
    .value
    .toUpperCase()

  list = document.getElementById("list")
  articles = list.getElementsByTagName("article")
  
  for (i = 0; i < articles.length; i++) {
    tags = articles[i].getElementsByClassName("readingtips-tags")
    tags.item(0).textContent.trim().toUpperCase().indexOf(filter) > -1
      ? articles[i].style.display = ""
      : articles[i].style.display = "none"
  }
}