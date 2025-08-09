<div class="row p-1">
    <div class="col-lg-5">
        <a href="/video/watch?id=${video.videoId}">
            <img src="/assets/images/video_poster/${video.poster}" alt="Video Thumbnail" class="img-fluid rounded-2" />
        </a>
    </div>
    <div class="col-lg-7 d-lg d-none d-lg-block">
        <h6 class="mt-2">${video.title}</h6>
        <div class="d-flex gap-2">
            <small>Views: ${video.views}</small>
            <small>Favourites: ${video.favourites}</small>
        </div>
    </div>
    <div class="d-block d-lg-none">
        <h4 class="mt-2">${video.title}</h4>
        <div class="d-flex gap-2">
            <small>Views: ${video.views}</small>
            <small>Favourites: ${video.favourites}</small>
        </div>
    </div>
    
</div>