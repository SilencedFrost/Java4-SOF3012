<div class="modal fade" id="videoModal-${video.videoId}" tabindex="-1" aria-labelledby="videoModalLabel-${video.videoId}" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="videoModalLabel-${video.videoId}">Send to?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <div class="mb-3">
                    <label for="email" class="form-label">Enail</label>
                    <input type="email" class="form-control shadow-sm"
                           id="email" name="email"
                           placeholder="Enter email">
                </div>
            </div>
            <!-- Modal Footer -->
            <div class="modal-footer">
                <button name="share" value="${video.videoId}" class="btn btn-warning">Share</button>
            </div>
        </div>
    </div>
</div>