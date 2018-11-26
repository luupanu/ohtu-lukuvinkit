const submitForm = (checkbox) => {
  const form = checkbox.parentNode;
  form.submit()
}

const toggleComments = (link) => {
  const comments = link.parentNode.querySelectorAll('.commentarea')

  comments.forEach(comment => {
    if (comment.style.display === 'block') {
      comment.style.display = 'none'
    } else {
      comment.style.display = 'block'
    }
  })
}
