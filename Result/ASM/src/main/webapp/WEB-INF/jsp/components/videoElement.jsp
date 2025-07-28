<div class="col-lg-3 col-md-4 col-sm-6">
    <div class="card shadow-sm p-2 h-100">
        <a href="/video/watch?id=${video.videoId}">
            <img src="/assets/images/${video.poster}" alt="Video Thumbnail" class="img-fluid rounded-2" />
        </a>
        <h5 class="mt-2">${video.title}</h5>
        <small class="text-secondary">Views: ${video.views}</small>
        <form action="" method="post" class="d-flex flex-column h-100">
            <div class="mt-auto d-flex justify-content-end">
                <button name="favourite" value="${video.videoId}" class="btn btn-success m-1 ${video.isFavourited != null ? 'd-none' : 'd-block'}">Favourite</button>
                <button name="unfavourite" value="${video.videoId}" class="btn btn-danger m-1 ${video.isFavourited != null ? 'd-block' : 'd-none'}">Unfavourite</button>
                <button type="button" class="btn btn-warning m-1" data-bs-toggle="modal" data-bs-target="#myModal">Share</button>
            </div>
        </form>
    </div>
</div>