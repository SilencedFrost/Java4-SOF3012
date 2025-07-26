<div class="col-lg-3 col-md-4 col-sm-6">
    <div class="card shadow-sm p-2 h-100">
        <a href="video/watch?id=${video.videoId}">
            <img src="/assets/images/${video.poster}" alt="Video Thumbnail" class="img-fluid rounded-2" />
        </a>
        <h5 class="mt-2">${video.title}</h5>
        <div class="d-flex mt-auto">
            <button class="btn btn-success ms-auto m-1">Like</button>
            <button type="button" class="btn btn-warning m-1" data-bs-toggle="modal" data-bs-target="#myModal">
                Share
            </button>
        </div>
    </div>
</div>