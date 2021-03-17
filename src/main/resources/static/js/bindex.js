$(document).ready(function (){
    for (let i = 1; i < 5; i++) {
        countNum('center_bottom_txt_' + i);
    }
});
function selectQuestion(url){
    location.href = '/question/' + url;
}
function countNum(tagName){
    let num = document.getElementById(tagName + '_hidden').innerText;
    $({ val : 0 }).animate({ val : num }, {
        duration: 1000,
        step: function() {
            var num = numberWithCommas(Math.floor(this.val));
            $('#' + tagName).text(num);
        },
        complete: function() {
            var num = numberWithCommas(Math.floor(this.val));
            $('#' + tagName).text(num);
        }
    });

    function numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
}