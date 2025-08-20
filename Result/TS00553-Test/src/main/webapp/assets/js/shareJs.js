async function shareVideo(videoId) {
    const emailInput = document.getElementById('email-' + videoId);
    const shareButton = document.querySelector('button[onclick="shareVideo(\'' + videoId + '\')"]');
    const email = emailInput.value.trim();

    const originalDisabled = shareButton.disabled;
    const originalInnerHTML = shareButton.innerHTML;

    shareButton.disabled = true;
    shareButton.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Sharing...';

    try {
        const response = await fetch('/api/share', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                videoId: videoId,
                receiverEmail: email
            })
        });

        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            const result = await response.json();
            console.log('Status code:', response.status);

            switch (response.status) {
                case 303:
                    console.log('Redirecting to:', result.redirect);
                    if (result.redirect?.trim()) {
                        window.location.href = result.redirect;
                    } else {
                        window.location.reload();
                    }
                    break;
                case 400:
                    console.log('Validation error:', result.errors);
                    document.querySelectorAll('.form-error[id^="recipientError-' + videoId + '"]').forEach(function(div) {
                        div.classList.remove('d-none');
                        div.textContent = result.errors?.receiverEmailError || 'Invalid input';
                    });
                    break;
                default:
                    console.log('Other status:', response.status);
                    break;
            }
        } else {
            console.warn('Non-JSON response');
            const text = await response.text();
            console.log('Response text:', text);
        }
    } catch (err) {
        console.error('Fetch error:', err);
    } finally {
        shareButton.disabled = originalDisabled;
        shareButton.innerHTML = originalInnerHTML;
    }
}
