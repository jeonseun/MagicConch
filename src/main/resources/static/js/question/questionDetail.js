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

function clickAnswer(){
    let commentBtn = document.getElementsByClassName('answerBtn').item(0);
    let comments = document.getElementsByClassName('answer-wrapper').item(0);
    if ($('.commentBtn').hasClass('active')){
        commentBtn.classList.remove('active');
        comments.classList.remove('active');
    }else{
        commentBtn.classList.add('active');
        comments.classList.add('active');
    }
}

function clickAnswerBtn(){

    let question = $('#question').text();
    let user = $('#user').text();
    let content = $('#answerContent').val();

    $.ajax({
        url: '/answer',
        type: 'POST',
        data: {'questionId': question,  'username': user, 'content': content},
        dataType: 'text',
        success: function (data) {
            if(data === 'success') {
                $('.answer-wrapper').innerHTML =
                    "<div class=\"d-flex align-items-center\">\n" +
                    "    <div class=\"d-flex flex-column ps-2\">\n" +
                    "        <i class=\"fas fa-user-circle p-2\" style=\"font-size: 35px\"></i>\n" +
                    "        <div class=\"text-center\" th:text=\"q\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"ps-3\" th:utext=\"\"></div>\n" +
                    "</div>";
            }
        }, error: function (xhr) {
        }
    })
}