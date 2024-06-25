async function shortenURL() {
    const originalURL = document.getElementById('originalURL').value;
    const customAlias = document.getElementById('customAlias').value;

    //debugging
    console.log('Original URL:', originalURL);
    console.log('Custom Alias:', customAlias);

    try {
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

        //more debugging
        console.log('Response status:', response.status);

        if (!response.ok) {
            throw new Error('Network response is not working ' + response.statusText);
        }

        const shortenedURL = await response.text();
        const fullShortenedURL = `${window.location.origin}/api/${shortenedURL}`;
        console.log('Shortened URL:', fullShortenedURL);

        document.getElementById('result').innerHTML = `Shortened URL: <a href="${fullShortenedURL}" target="_blank">${fullShortenedURL}</a>`;
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('result').innerText = `Error: ${error.message}`;
    }
}

window.shortenURL = shortenURL;