function clickLike(click, questionId){
    if(click == 'like'){
        $('#like').hide();
        $('#no-like').show();
        $.ajax({
            url: '/questionLike',
            type: 'DELETE',
            data: {'questionId' : questionId},
            dataType: 'json',
            success: function (data) {
                console.log('성공');
            }, error: function (xhr) {
            }
        })

    }
    else if(click == 'noLike'){
        $('#like').show();
        $('#no-like').hide();
        $.ajax({
            url: '/questionLike',
            type: 'POST',
            data: {'questionId' : questionId},
            dataType: 'json',
            success: function (data) {
                console.log('성공');
            }, error: function (xhr) {
            }
        })
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

function clickComment(){
    let commentBtn = document.getElementsByClassName('commentBtn').item(0);
    let comments = document.getElementsByClassName('comment-wrapper').item(0);
    if ($('.commentBtn').hasClass('active')){
        commentBtn.classList.remove('active');
        comments.classList.remove('active');
    }else{
        commentBtn.classList.add('active');
        comments.classList.add('active');
    }
}