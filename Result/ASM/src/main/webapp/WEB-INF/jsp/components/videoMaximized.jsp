<div class="ratio ratio-16x9">
  <iframe 
    src="https://www.youtube.com/embed/${video.videoId}?autoplay=1&rel=0&modestbranding=1" 
    title="${video.title}"
    allow="autoplay; encrypted-media" 
    allowfullscreen>
  </iframe>
</div>
<h3 class="mt-2">${video.title}</h3>
<div class="d-flex">
  <div class="text-secondary">Views: ${video.views}</div>
  <form action="" method="post" class="d-flex flex-row ms-auto">
    <button name="favourite" value="${video.videoId}" class="btn btn-success m-1 ${video.isFavourited != null ? 'd-none' : 'd-block'}">Favourite</button>
    <button name="unfavourite" value="${video.videoId}" class="btn btn-danger m-1 ${video.isFavourited != null ? 'd-block' : 'd-none'}">Unfavourite</button>
    <button type="button" class="btn btn-warning m-1" data-bs-toggle="modal" data-bs-target="#myModal">Share</button>
  </form>
</div>
<hr class="p-2">
<p>${video.description}</p>