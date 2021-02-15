$(document).ready(function (){
    questionInfo();
});

function clickAnswerBtn(questionId, username){
    $.ajax({
        url: '/answer/info',
        type: 'GET',
        data: {'question' : questionId},
        dataType: 'json',
        success: function (data) {

            let question = document.getElementById('answer-wrapper-' + questionId);

            question.innerHTML =
                "<div class=\"text-end mt-4\">\n" +
                "    <div class=\"form-outline\">\n" +
                "        <div style=\"display: none\" id=\"question\">" + questionId +"</div>\n" +
                "        <div style=\"display: none\" id=\"user\"> + username +</div>\n" +
                "        <textarea class=\"form-control\" id=\"answerContent\" rows=\"4\" name=\"content\"></textarea>\n" +
                "        <label class=\"form-label\" for=\"answerContent\">답변 내용</label>\n" +
                "    </div>\n" +
                "    <button type=\"button\" class=\"mt-2 mb-2 btn btn-outline-primary\" onclick=\"clickAnswerBtn()\">작성</button>\n" +
                "</div>";

            for (let i = 0; i < data.length; i++) {
                question.innerHTML +=
                    "<div class=\"answer\">\n" +
                    "    <!-- 답변 보기 -->\n" +
                    "    <div" +
                    "        <hr>\n" +
                    "        <div class=\"d-flex align-items-center\ value=" + data[i].username + " onclick=\"clickAnswerProfile(this)\"s>\n" +
                    "            <div class=\"d-flex flex-column ps-2\">\n" +
                    "                <img src=" + data[i].profileImg + " \n" +
                    "                     class=\"rounded-circle\"\n" +
                    "                     height=\"35px\"\n" +
                    "                     width=\"35px\">\n" +
                    "                <div class=\"text-center\" style=\"font-size: 20px\">" + data[i].username + "</div>\n" +
                    "            </div>\n" +
                    "            <div class=\"ps-3\" style=\"word-break:break-all\">" + data[i].content + "</div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>";
            }
        }, error: function (xhr) {
        }
    })
}

function questionInfo() {

    var params = jQuery('.form-data').serialize();

    $.ajax({
        url: '/api/v1/index/question/follow/list',
        type: 'GET',
        data: params,
        dataType: 'json',
        success: function (data) {
            let question = document.getElementById('index-center');
            for (let i = 0; i < 4; i++) {
                if(i % 2 === 0){
                    question.innerHTML +=
                        "<div class=\"d-flex flex-row\">\n" +
                        "  <div onclick=\"location.href='/user/info?username=" + data.dtoList[i].username + "'\">\n" +
                        "    <img src=\" " + data.dtoList[i].profileImg + "\" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                        "    <div class = \"text-center\"style=\"font-size: x-large;\">" + data.dtoList[i].username + "</div>\n" +
                        "  </div>\n" +
                        "  <div class='d-flex'>\n" +
                        "    <div onclick=\"location.href='/question/" + data.dtoList[i].questionId + "'\" class=\"balloon\" style='padding: 10px; word-break: break-all; overflow: auto'>\n" +
                        "      <div class=\"d-flex justify-content-between\">\n" +
                        "        <strong " + "style=\"font-size: large\">" + data.dtoList[i].title +"</strong>\n" +
                        "        <strong style=\"font-size: large\">" + data.dtoList[i].beforeTime + "분 전" + "</strong>\n" +
                        "      </div>\n" +
                        "      <hr>\n" +
                        "      <div>" + data.dtoList[i].content +"</div>\n" +
                        "    </div>\n" +
                        "    <div class='mt-5'>" +
                        "      <button type=\"button\" class=\"answerBtn btn btn-outline-primary\" onclick=clickAnswerBtn(" +data.dtoList[i].questionId + ",\"" + data.dtoList[i].username + "\")>댓글 보기</button>" +
                        "    </div>" +
                        "  </div>\n" +
                        "</div>" +
                        "<div id=answer-wrapper-" + data.dtoList[i].questionId + ">" +
                        "</div>";
                }
                else{
                    question.innerHTML +=
                        "<div class=\"d-flex flex-row-reverse\">\n" +
                        "  <div onclick=\"location.href='/user/info?username=" + data.dtoList[i].username + "'\">\n" +
                        "    <img src=\" " + data.dtoList[i].profileImg + " \" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                        "    <div class = \"text-center\" style=\"font-size: x-large;\">" + data.dtoList[i].username + "</div>\n" +
                        "  </div>\n" +
                        "  <div class='d-flex'>\n" +
                        "    <div class='mt-5'>" +
                        "      <button type=\"button\" class=\"answerBtn btn btn-outline-primary\" onclick=clickAnswerBtn(" +data.dtoList[i].questionId + ",\"" + data.dtoList[i].username + "\")>댓글 보기</button>" +
                        "    </div>" +
                        "    <div onclick=\"location.href='/question/" + data.dtoList[i].questionId + "'\" class=\"balloon2\" style='padding: 10px; word-break: break-all; overflow: auto'>\n" +
                        "      <div class=\"d-flex justify-content-between\">\n" +
                        "        <strong style=\"font-size: large\">" + data.dtoList[i].title +"</strong>\n" +
                        "        <strong style=\"font-size: large\">" + data.dtoList[i].beforeTime + "분 전" + "</strong>\n" +
                        "      </div>\n" +
                        "      <hr>\n" +
                        "      <div>" + data.dtoList[i].content +"</div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>" +
                        "<div id=answer-wrapper-" + data.dtoList[i].questionId + ">" +
                        "</div>";
                }
            }
            question.innerHTML +=
                "<div class=\"text-center\">\n" +
                "  <button onclick=\"location.href = '/question/follow/list'\" type=\"button\" class=\"btn btn-primary btn-rounded\" style=\"width: 80%\">더보기</button>\n" +
                "</div>"
        }, error: function (xhr) {

        }
    })
}