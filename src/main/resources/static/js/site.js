const toggleComments = (link) => {
  const comments = link.parentNode.querySelectorAll('.comment')

  comments.forEach(comment => {
    if (comment.style.display === 'block') {
      comment.style.display = 'none'
    } else {
      comment.style.display = 'block'
    }
  })
}