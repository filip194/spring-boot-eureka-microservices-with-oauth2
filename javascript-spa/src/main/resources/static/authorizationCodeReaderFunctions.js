// read authorization code from authorization server response
function readCodeFromResponse() {
    var urlParams = new URLSearchParams(window.location.search);
    var authCode = urlParams.get("code"),
         state = urlParams.get("state"),
         error = urlParams.get("error"),
         errorDescription = urlParams.get('error_description');

    if (error) {
        window.alert("Error occurred: " + error + ". Description: " + errorDescription);
    } else {
        window.opener.postAuthorize(state, authCode);
    }
    window.close();
}