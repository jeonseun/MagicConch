function clickLike(click){
    if(click == 'like'){
        $('#like').hide();
        $('#no-like').show();
    }
    else if(click == 'noLike'){
        $('#like').show();
        $('#no-like').hide();
    }
}

function clickUpdate(){
    location.href = '/question/' + window.location.href.split('/')[4] + '/modify';
}

function clickDelete(){
    $.ajax({
        url: '/question/' + window.location.href.split('/')[4],
        type: 'DELETE',
        dataType: 'text',
        success: function (data) {
            if(data === 'success') {
                location.href = '/question/list';
            }
        }, error: function (xhr) {
        }
    })
}