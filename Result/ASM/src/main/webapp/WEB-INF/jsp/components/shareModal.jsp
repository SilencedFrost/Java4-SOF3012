<div class="modal fade" id="videoModal-${video.videoId}" tabindex="-1" aria-labelledby="videoModalLabel-${video.videoId}" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title">Send to?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="d-flex flex-column h-100">
                <!-- Modal Body -->
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="email-${video.videoId}" class="form-label">Email</label>
                        <input type="email" class="form-control shadow-sm"
                               id="email-${video.videoId}" name="email"
                               placeholder="Enter email">
                        <!-- Error div for displaying validation errors -->
                        <div id="recipientError-${video.videoId}" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                    </div>
                </div>
                <!-- Modal Footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" onclick="shareVideo('${video.videoId}')">Share</button>
                </div>
            </div>
        </div>
    </div>
</div>