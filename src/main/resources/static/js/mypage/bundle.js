const endPoint = 'http://localhost:8080/'
const questionUri = 'question/'

function questionLink(url) {
    location.href = endPoint + questionUri +  url;
}