function follow(username) {

    $.ajax({
        url: '/follow',
        type: 'POST',
        data: {user: username},
        dataType: 'json',
        success: function (data) {
        }, error: function (xhr) {
        }
    })
}

function unFollow(username) {

    $.ajax({
        url: '/follow',
        type: 'DELETE',
        data: {user: username},
        dataType: 'json',
        success: function (data) {
        }, error: function (xhr) {
        }
    })
}

const btnFollow = document.querySelector("#btnFollow");

function followClick(username) {
    if (btnFollow.getElementsByTagName("span")[0].innerText === '언팔로우') {
        unFollow(username);
        btnFollow.getElementsByTagName("span")[0].innerText = '팔로우';
    } else {
        follow(username);
        btnFollow.getElementsByTagName("span")[0].innerText = '언팔로우';
    }
}
