$(document).ready(function (){
    document.getElementsByClassName('page-item-no').item(0).classList.add('active');
})

const searchDrop = document.getElementsByClassName('searchInput').item(0);
const tagList = document.getElementById('tagList');

function previous(is, startPage){
    if(is){
        cntPage = startPage - 10;
        var params = jQuery('.form-data').serialize() + '&page=' + cntPage;
        refreshList(params);
    }
}

function next(is, startPage){
    if(is){
        cntPage = startPage + 10;
        var params = jQuery('.form-data').serialize() + '&page=' + cntPage;
        refreshList(params);
    }
}

function selectPageNo(pageNo){
    var params = jQuery('.form-data').serialize() + '&page=' + pageNo;
    refreshList(params, pageNo);
}

function selectSearchFilter(searchName){
    if(searchName == 'username'){
        searchDrop.name = 'user';
    }else{
        searchDrop.name = searchName;
    }
    $('.searchDrop').text(searchName);
}

function search(evt){
    evt.preventDefault();
    searchDrop.value = $('.searchInput').val();
    var params = jQuery('.form-data').serialize();
    refreshList(params);
}

function selectTagName(evt, tagName){
    evt.preventDefault();
    if(tagName == 'All'){
        tagList.name = "tag";
        tagList.value = ""
    }else{
        tagList.name = "tag";
        tagList.value = tagName;
    }
    var params = jQuery('.form-data').serialize();
    refreshList(params);
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

function selectQuestion(url){
    location.href = '/question/' + url;
}

function refreshList(params, pageNo = 1){

    $.ajax({
        url: '/api/v1/question/follow/list',
        type: 'GET',
        data : params,
        dataType: 'json',
        success: function (data){
            /* 제목 */
            let title = document.getElementsByClassName('title').item(0);
            title.innerText = '질문 목록(' + data.totalCnt + ')';
            /* 질문 목록 */
            let list = document.getElementsByClassName('list-wrapper').item(0);
            $('.list-wrapper').empty();
            for (let i = 0; i < data.dtoList.length; i++) {
                let tr = document.createElement("tr");
                list.appendChild(tr);
                tr.setAttribute('onclick', 'javascript:selectQuestion(' + data.dtoList[i].questionId + ')')
                tr.classList.add('hover-shadow');
                if(data.dtoList[i].status === 'END') {
                    tr.style = 'background-color: #efefef;';
                }
                tr.innerHTML =
                    '<td>' + (data.totalCnt - ((data.curPage - 1) * 10) - i) + '</td>';
                if(data.dtoList[i].status === 'ING'){
                    tr.innerHTML += '<td style="text-align: center"><i class="fas fa-running" style="color: #FFA900; font-size: 1.5rem"></i></td>';
                }else if(data.dtoList[i].status === 'END'){
                    tr.innerHTML += '<td style="text-align: center"><i class="fas fa-check" style="color: #00B74A; font-size: 1.5rem"></i></td>';
                }
                tr.innerHTML +=
                    '<td style="text-shadow: 1px 1px 10px ' + data.dtoList[i].tagColor + '">' + data.dtoList[i].tagName + '</td>' +
                    '<td>' + data.dtoList[i].title + '</a></td>' +
                    '<td style="text-align: center">' + data.dtoList[i].username + '</td>' +
                    '<td style="text-align: center">' + data.dtoList[i].createTime + '</td>' +
                    '<td style="text-align: center">' + data.dtoList[i].view + '</td>'
                ;
            }
            /* 페이징 */
            let pageList = document.getElementsByClassName('pageNo').item(0);
            let previous = document.getElementsByClassName('previous').item(0);
            let next = document.getElementsByClassName('next').item(0);
            $('.pageNo').empty();
            for (let i = data.startPage; i < data.endPage + 1; i++) {
                let li = document.createElement("tr");
                li.classList.add('page-item', 'page-item-no');
                pageList.appendChild(li);
                li.innerHTML =
                    '<a class="page-link" onclick="selectPageNo(' + i + ')">' + i + '</a>';
                previous.setAttribute("onclick", 'previous(' + data.previous + ',' + data.startPage + ')');
                next.setAttribute("onclick", 'next(' + data.next + ',' + data.startPage + ')');
            }
            document.getElementsByClassName('page-item-no').item((pageNo - 1) % 10).classList.add('active');
        }, error: function (xhr){

        }
    })
}