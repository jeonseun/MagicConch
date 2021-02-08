$(document).ready(function (){
    friendMeInfo();
});

function friendMeInfo() {

    $.ajax({
        url: '/api/v1/follow/me',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let friend = document.getElementById('friend-wrapper');
            $('#friend-wrapper').empty();
            for (let i = 0; i < data.length; i++) {
                friend.innerHTML +=
                    "  <div style='padding: 20px' onclick=\"location.href='/mypage/overview'\">\n" +
                    "    <img src=\" " + data[i].profileImg + "\" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                    "    <div class = \"text-center\"style=\"font-size: x-large;\">" + data[i].username + "</div>\n" +
                    "  </div>\n";
            }
        }, error: function (xhr) {

        }
    })
}

function friendYouInfo() {

    $.ajax({
        url: '/api/v1/follow/you',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let friend = document.getElementById('friend-wrapper');
            $('#friend-wrapper').empty();
            for (let i = 0; i < data.length; i++) {
                friend.innerHTML +=
                    "  <div style='padding: 20px' onclick=\"location.href='/mypage/overview'\">\n" +
                    "    <img src=\" " + data[i].profileImg + "\" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                    "    <div class = \"text-center\"style=\"font-size: x-large;\">" + data[i].username + "</div>\n" +
                    "  </div>\n";
            }
        }, error: function (xhr) {

        }
    })
}