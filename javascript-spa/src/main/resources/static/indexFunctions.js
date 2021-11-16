// state value
function generateState(length) {
    var stateValue = "";
    var alphaNumericCharacters = 'ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvxyz0123456789';
    var alphaNumericCharactersLength = alphaNumericCharacters.length;

    for (var i = 0; i < length; i++) {
        stateValue += alphaNumericCharacters.charAt(Math.floor(Math.random() * alphaNumericCharactersLength));
    }

    document.getElementById("stateValue").innerHTML = stateValue;
}

// code verifier value
function generateCodeVerifier() {
    var returnValue = "";
    var randomByteArray = new Uint8Array(32);
    window.crypto.getRandomValues(randomByteArray);

    returnValue = base64UrlEncode(randomByteArray);

    document.getElementById("codeVerifierValue").innerHTML = returnValue;
}

function base64UrlEncode(sourceValue) {
    var stringValue  = String.fromCharCode.apply(null, sourceValue);
    var base64Encoded = btoa(stringValue);
    var base64UrlEncoded = base64Encoded.replace(/\+/g, '-').replace(/\//g, '-').replace(/=/g, '');
    return base64UrlEncoded;
}

// code challenge value
async function generateCodeChallenge() {
    var codeChallengeValue = "";

    var codeVerifier = document.getElementById("codeVerifierValue").innerHTML;

    var textEncoder = new TextEncoder('US-ASCII');
    var encodedValue = textEncoder.encode(codeVerifier);
    var digest = await window.crypto.subtle.digest("SHA-256", encodedValue);

    codeChallengeValue = base64UrlEncode(Array.from(new Uint8Array(digest)));

    document.getElementById("codeChallengeValue").innerHTML = codeChallengeValue;
}

// PKCE-enhanced authorization code
function getAuthorizationCode() {
    var state = document.getElementById("stateValue").innerHTML;
    var codeChallenge = document.getElementById("codeChallengeValue").innerHTML;

    var realm = "iot-demo";
    var clientId= "iot-demo-PKCE-client";
    var redirectUri = "http://localhost:9191/authorizationCodeReader.html";
    var codeChallengeMethod = "S256";

    var authorizationURL = "http://localhost:8080/auth/realms/" + realm + "/protocol/openid-connect/auth";
    authorizationURL += "?client_id=" + clientId;
    authorizationURL += "&response_type=code";
    authorizationURL += "&scope=openid";
    authorizationURL += "&redirect_uri=" + redirectUri;
    authorizationURL += "&state=" + state;
    authorizationURL += "&code_challenge=" + codeChallenge;
    authorizationURL += "&code_challenge_method=" + codeChallengeMethod + "";

    window.open(authorizationURL, 'authorizationRequestWindow', 'width=1024,height=768,left=100,top=100');
}

// post authorize
function postAuthorize(state, authCode) {
    var originalStateValue = document.getElementById("stateValue").innerHTML;

    if (state === originalStateValue) {
        requestTokens(authCode);
    } else {
        alert("Invalid state received!")
    }
}

// request access token
function requestTokens(authCode) {
    var codeVerifier = document.getElementById("codeVerifierValue").innerHTML;

    var data = {
        "grant_type": "authorization_code",
        "client_id": "iot-demo-PKCE-client",
        "code": authCode,
        "code_verifier": codeVerifier,
        "redirect_uri": "http://localhost:9191/authorizationCodeReader.html"
    };

    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        },
        type: "POST",
        url: "http://localhost:8080/auth/realms/iot-demo/protocol/openid-connect/token",
        data: data,
        success: postRequestAccessToken,
        dataType: "json"
    });
}

// on success function
function postRequestAccessToken(data, status, jqXHR) {
    // data     - data object, in the body of HTTP response, contains access token
    // status   - response status
    // jqXHr    - response object itself
    document.getElementById("accessToken").innerHTML = data["access_token"];
}

// get info from smart-homes resource server
function getInfoFromResourceServer() {
    var accessToken = document.getElementById("accessToken").innerHTML;

    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            request.setRequestHeader("Authorization", "Bearer " + accessToken);
        },
        type: "GET",
        url: "http://localhost:9092/smart-homes/port", // sending via API gateway
        success: postInfoFromAccessToken,
        dataType: "text"
    });
}

// on success function
function postInfoFromAccessToken(data, status, jqXHR) {
    alert(data);
}
