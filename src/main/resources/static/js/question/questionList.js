const searchDrop = document.getElementsByClassName('searchInput').item(0);
const tagList = document.getElementById('tagList');

function previous(is, startPage){
    if(is){
        cntPage = startPage - 10;
        location.href = "/question/list?page=" + cntPage;
    }
}

function next(is, startPage){
    if(is){
        cntPage = startPage + 10;
        location.href = "/question/list?page=" + cntPage;
    }
}

function selectSearchFilter(searchName){
    if(searchName == 'username'){
        searchDrop.name = 'user';
    }else{
        searchDrop.name = searchName;
    }
    $('.searchDrop').text(searchName);
}

function search(){
    searchDrop.value = $('.searchInput').val();
}

function selectTagName(tagName){
    if(tagName == 'All'){
        tagList.name = "";
    }else{
        tagList.name = "tag";
        tagList.value = tagName;
    }
}

function tagDrop(tagDrop){
    if(tagDrop == 'on'){
        $('.tagOn').hide();
        $('.tagOff').show();
        $('.tag').hide();
    }else{
        $('.tagOn').show();
        $('.tagOff').hide();
        $('.tag').show();
    }
}