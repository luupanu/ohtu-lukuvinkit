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

function searchInput() {
  let input,
    filter,
    list,
    articles,
    i,
    tags

  input = document.getElementById("search")
  filter = input
    .value
    .toUpperCase()

  list = document.getElementById("list")
  articles = list.getElementsByTagName("article")

  for (i = 0; i < articles.length; i++) {
    tags = articles[i].getElementsByClassName("readingtips-tags")
    if ((tags.item(0).textContent.trim().toUpperCase().indexOf(filter) > -1)) {
      articles[i].style.display = ""
    } else {
      articles[i].style.display = "none"
    }
  }
}