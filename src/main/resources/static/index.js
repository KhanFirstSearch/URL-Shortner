async function shortenURL() {
    const originalURL = document.getElementById('originalURL').value;
    const customAlias = document.getElementById('customAlias').value;

    //Check to see if the URL is valid
    try {
        new URL(originalURL);
    } catch (e) {
        document.getElementById('result').innerText = 'Error: Please enter a valid URL.';
        return;
    }

    try { //Post request to the Rest API to shorten a link
        const response = await fetch('http://localhost:8080/api/shorten', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                originalURL: originalURL,
                customAlias: customAlias
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText);
        }

        const shortenedURL = await response.text();
        console.log('Response from backend:', shortenedURL);

        // eck if the response is empty
        if (!shortenedURL) {
            throw new Error('The shortened URL is empty.');
        }

        const fullShortenedURL = `${window.location.origin}/api/${shortenedURL}`;
        document.getElementById('result').innerHTML = `Shortened URL: <a href="${fullShortenedURL}" target="_blank">${fullShortenedURL}</a>`;
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('result').innerText = `Error: ${error.message}`;
    }
}

window.shortenURL = shortenURL;
