const searchDrop = document.getElementsByClassName('searchInput').item(0);

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