$(document).ready(function (){
    isQuestionLike = $('#isQuestionLike').text();
    if(isQuestionLike === 'true'){
        $('#like').hide();
        $('#no-like').show();
    }else{
        $('#like').show();
        $('#no-like').hide();
    }
})

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
    if ($('.answerBtn').hasClass('active')){
        commentBtn.classList.remove('active');
        comments.classList.remove('active');
    }else{
        commentBtn.classList.add('active');
        comments.classList.add('active');
    }
}

function clickAnswerProfile(obj){
    //location.href = '/user/info?username=';
}

function clickUpdateAnswer(id){
    $('#' + id).children().remove();
    $('#' + id).html(
        "<div class=\"text-end mt-4\">\n" +
        "    <div class=\"form-outline\">\n" +
        "        <textarea class=\"form-control\" id=\"answerUpdateContent\" rows=\"4\" name=\"content\"></textarea>\n" +
        "        <label class=\"form-label\" for=\"answerUpdateContent\">답변 내용</label>\n" +
        "    </div>\n" +
        "    <button type=\"button\" class=\"mt-2 mb-2 btn btn-outline-primary\" onclick=\"clickUpdateAnswerBtn(" + id + ")\">작성</button>\n" +
        "</div>"
    )
    document.querySelectorAll('.form-outline').forEach((formOutline) => {
        new mdb.Input(formOutline).init();
    });
}

function clickDeleteAnswer(id){
    $.ajax({
        url: '/answer',
        type: 'DELETE',
        data: {'answerId' : id},
        dataType: 'text',
        success: function (data) {
            $('#' + id).remove();
        }, error: function (xhr) {
        }
    })
}

function clickAnswerBtn(){

    let question = $('#question').text();
    let user = $('#user').text();
    let content = $('#answerContent');
    let answerWrapper = document.getElementsByClassName('answer-wrapper').item(0);

    $.ajax({
        url: '/answer',
        type: 'POST',
        data: {'questionId': question,  'username': user, 'content': content.val()},
        dataType: 'json',
        success: function (data) {
                answerWrapper.innerHTML +=
                    "<div class='scroll' id= " + data.answerId + ">\n" +
                    "    <hr>\n" +
                    "    <div style='background-color: #efefef' class=\"d-flex align-items-center\">\n" +
                    "        <div class=\"d-flex flex-column ps-2\">\n" +
                    "            <img src =" + data.profileImg + "\n" +
                    "                 class=\"rounded-circle\"\n" +
                    "                 height=\"35px\"\n" +
                    "                 width=\"35px\">\n" +
                    "            <div class=\"text-center\"> " + data.username + "</div>\n" +
                    "        </div>\n" +
                    "        <div class=\"d-flex flex-column\">" +
                    "            <div class=\"ps-3\" style=\"word-break:break-all\" >" + data.content + "</div>\n" +
                    "            <div class=\"ps-3\" style=\"font-size: 10px\">" + data.createTime + "</div>" +
                    "        </div>\n" +
                    "        <div class=\"btn-group shadow-0 ms-auto\">\n" +
                    "            <button\n" +
                    "                    type=\"button\"\n" +
                    "                    class=\"btn btn-link dropdown-toggle\"\n" +
                    "                    data-mdb-toggle=\"dropdown\"\n" +
                    "                    aria-expanded=\"false\"\n" +
                    "                    style=\"color: lightgray\"\n" +
                    "            >\n" +
                    "                <i class=\"fas fa-ellipsis-v\"></i>\n" +
                    "            </button>\n" +
                    "            <ul class=\"dropdown-menu\">\n" +
                    "                <li><a class=\"dropdown-item\" href=\"#\" onclick=clickUpdateAnswer(" + data.answerId + ")>수정</a></li>\n" +
                    "                <li><a class=\"dropdown-item\" href=\"#\" onclick=clickDeleteAnswer(" + data.answerId + ")>삭제</a></li>\n" +
                    "            </ul>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n";
                let offset = $('.scroll').offset();
                $('html').animate({scrollTop : offset.top}, 10);
        }, error: function (xhr) {
        }
    })

    content.val('');

    if(!$('.answerBtn').hasClass('active')) {

        let commentBtn = document.getElementsByClassName('answerBtn').item(0);
        let comments = document.getElementsByClassName('answer-wrapper').item(0);

        commentBtn.classList.add('active');
        comments.classList.add('active');
    }

}

function clickUpdateAnswerBtn(id){
    $.ajax({
        url: '/answer',
        type: 'PUT',
        data: {'answerId': id,  'content': $('#answerUpdateContent').val()},
        dataType: 'json',
        success: function (data) {
            $('#' + id).children().remove();
            $('#' + id).html(
                "    <hr>\n" +
                "    <div style='background-color: #efefef' class=\"d-flex align-items-center\">\n" +
                "        <div class=\"d-flex flex-column ps-2\">\n" +
                "            <img src =" + data.profileImg + "\n" +
                "                 class=\"rounded-circle\"\n" +
                "                 height=\"35px\"\n" +
                "                 width=\"35px\">\n" +
                "            <div class=\"text-center\"> " + data.username + "</div>\n" +
                "        </div>\n" +
                "        <div class=\"d-flex flex-column\">" +
                "            <div class=\"ps-3\" style=\"word-break:break-all\" >" + data.content + "</div>\n" +
                "            <div class=\"ps-3\" style=\"font-size: 10px\">" + data.createTime + "</div>" +
                "        </div>\n" +
                "        <div class=\"btn-group shadow-0 ms-auto\">\n" +
                "            <button\n" +
                "                    type=\"button\"\n" +
                "                    class=\"btn btn-link dropdown-toggle\"\n" +
                "                    data-mdb-toggle=\"dropdown\"\n" +
                "                    aria-expanded=\"false\"\n" +
                "                    style=\"color: lightgray\"\n" +
                "            >\n" +
                "                <i class=\"fas fa-ellipsis-v\"></i>\n" +
                "            </button>\n" +
                "            <ul class=\"dropdown-menu\">\n" +
                "                <li><a class=\"dropdown-item\" href=\"#\" onclick=clickUpdateAnswer(" + data.answerId + ")>수정</a></li>\n" +
                "                <li><a class=\"dropdown-item\" href=\"#\" onclick=clickDeleteAnswer(" + data.answerId + ")>삭제</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n"
        )
        }, error: function (xhr) {
        }
    })
}