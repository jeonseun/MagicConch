$(document).ready(function (){
    questionInfo();
});

function questionInfo() {

    var params = jQuery('.form-data').serialize();

    $.ajax({
        url: '/api/v1/question/follow/list',
        type: 'GET',
        data: params,
        dataType: 'json',
        success: function (data) {
            let question = document.getElementById('index-center');
            for (let i = 0; i < data.dtoList.length; i++) {
                if(i % 2 == 0){
                    question.innerHTML +=
                        "<div class=\"d-flex flex-row\">\n" +
                        "  <div>\n" +
                        "    <img src=\"/image/default_profile_image.png\" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                        "    <div class = \"text-center\"style=\"font-size: x-large;\">" + data.dtoList[i].username + "</div>\n" +
                        "  </div>\n" +
                        "  <div class=\"balloon\" style=\"padding: 10px\">\n" +
                        "    <div class=\"d-flex justify-content-between\">\n" +
                        "      <a " + "href=\"/question/" + data.dtoList[i].questionId +"\"" + "style=\"font-size: large\">" + data.dtoList[i].title +"</a>\n" +
                        "      <div style=\"font-size: large\">" + data.dtoList[i].beforeTime + "분 전" + "</div>\n" +
                        "    </div>\n" +
                        "    <hr>\n" +
                        "    <div style=\"position: relative\">" + data.dtoList[i].content +"</div>\n" +
                        "  </div>\n" +
                        "</div>"
                }
                else{
                    question.innerHTML +=
                        "<div class=\"d-flex flex-row-reverse\">\n" +
                        "  <div>\n" +
                        "    <img src=\"/image/default_profile_image.png\" class=\"rounded-circle\" height=\"60px\" width=\"60px\">\n" +
                        "    <div class = \"text-center\" style=\"font-size: x-large;\">" + data.dtoList[i].username + "</div>\n" +
                        "  </div>\n" +
                        "  <div class=\"balloon2\" style=\"padding: 10px\">\n" +
                        "    <div class=\"d-flex justify-content-between\">\n" +
                        "      <a" + " href=\"/question/" + data.dtoList[i].questionId +"\"" + "style=\"font-size: large\">" + data.dtoList[i].title +"</a>\n" +
                        "      <div style=\"font-size: large\">" + data.dtoList[i].beforeTime + "분 전" + "</div>\n" +
                        "    </div>\n" +
                        "    <hr>\n" +
                        "    <div style=\"position: relative\">" + data.dtoList[i].content +"</div>\n" +
                        "  </div>\n" +
                        "</div>"
                }
            }
        }, error: function (xhr) {

        }
    })
}