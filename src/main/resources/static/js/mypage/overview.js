function follow(username) {

    $.ajax({
        url: '/follow',
        type: 'POST',
        data: {user : username},
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
        data: {user : username},
        dataType: 'json',
        success: function (data) {
        }, error: function (xhr) {
        }
    })
}