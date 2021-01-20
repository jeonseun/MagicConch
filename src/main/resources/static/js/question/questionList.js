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