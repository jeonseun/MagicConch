const tagDrop = document.getElementById('tagDrop');
const tagBtn = document.getElementById('tagBtn');
// title
const titleTxt = document.getElementById('title');
// content
const content = document.getElementById('summernote');
// tagName
const tagTxt = document.getElementById('tagTxt');
// bundleId
const bundleTxtHdn = document.getElementById('bundleTxtHdn');

const bundleTxt = document.getElementById('bundleTxt');

function disableBundle(bundleName, tagName, bundleId){
    tagDrop.classList.add('disabled');
    tagTxt.value = tagName;
    bundleTxtHdn.value = bundleId
    bundleTxt.placeholder = bundleName;
}

function enableBundle(bundleName, tagName, bundleId){
    tagDrop.classList.remove('disabled');
    tagTxt.value = tagName;
    bundleTxtHdn.value = bundleId
    bundleTxt.placeholder = bundleName;
}

function selectTag(tagName){
    tagTxt.value = tagName;
}

function clickCreateQuestionBtn(){
    $.ajax({
        url: '/question',
        type: 'POST',
        data: {'title' : titleTxt.value, 'content': content.value, 'tagName': tagTxt.value, 'bundleId': bundleTxtHdn.value},
        dataType: 'text',
        success: function (data) {
            if(data.indexOf('success') !== -1){
                window.location.href = 'question/' + data.split(' ')[1];
            }
            else{
                alert(data);
            }
        }, error: function (xhr) {
        }
    })
}