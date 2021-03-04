$(document).ready(function (){
    questionInfo();
});

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
                        "<div class=\"d-flex flex-row mb-4 \">\n" +
                        "  <div onclick=\"location.href='/user/overview?username=" + data.dtoList[i].username + "'\">\n" +
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
                        "  </div>\n" +
                        "</div>";
                }
                else{
                    question.innerHTML +=
                        "<div class=\"d-flex flex-row-reverse mb-4 \">\n" +
                        "  <div onclick=\"location.href='/user/info?username=" + data.dtoList[i].username + "'\">\n" +
                        "    <img src=\" " + data.dtoList[i].profileImg + " \" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                        "    <div class = \"text-center\" style=\"font-size: x-large;\">" + data.dtoList[i].username + "</div>\n" +
                        "  </div>\n" +
                        "  <div class='d-flex'>\n" +
                        "    <div onclick=\"location.href='/question/" + data.dtoList[i].questionId + "'\" class=\"balloon2\" style='padding: 10px; word-break: break-all; overflow: auto'>\n" +
                        "      <div class=\"d-flex justify-content-between\">\n" +
                        "        <strong style=\"font-size: large\">" + data.dtoList[i].title +"</strong>\n" +
                        "        <strong style=\"font-size: large\">" + data.dtoList[i].beforeTime + "분 전" + "</strong>\n" +
                        "      </div>\n" +
                        "      <hr>\n" +
                        "      <div>" + data.dtoList[i].content +"</div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
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