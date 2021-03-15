'use strict'

const uriUserSearch = "/user/search"

const userSearchInput = document.querySelector("#userInput");
const userSearchContainer = document.querySelector("#userSearchResult");
function handleUserSearchInput() {
    userSearchInput.addEventListener("keyup", () => {
        if (userSearchInput.value !== '') {
            searchUser(host, uriUserSearch, "username=" + userSearchInput.value)
                .then(function (data) {
                    if (data !== 'no user') {
                        while (userSearchContainer.hasChildNodes()) {
                            userSearchContainer.removeChild(userSearchContainer.firstChild);
                        }
                        for (let i = 0; i < data.length; i++) {
                            let userSearchResult = createUserSearchResult(data[i].username, data[i].profileImg);
                            userSearchContainer.appendChild(userSearchResult)
                        }
                    } else {
                        userSearchContainer.innerHTML = '<h3>No Result</h3>'
                    }
                });
        }
    });
}

async function searchUser(host, uri, params) {
    const url = host + uri + '?' + params;
    const response = await fetch(url);
    if (response.status === 200) {
        const data = await response.json();
        return data;
    } else if (response.status === 204) {
        return "no user";
    } else {
        throw Error(data)
    }
}

function createUserSearchResult(name, profile) {
    const item = `
           <div class="search__result--user">
             <div class="user__info">
               <span class="user__name">${name}</span>
               <img class="profile" src="${profile}" alt="">
             </div>
             <a href="/team/user?username=${name}&teamId=${teamId}" class="btn btn-info">멤버 추가</a>
           </div>
           `
    const container = document.createElement('div');
    container.innerHTML = item;
    container.appendChild(document.createElement('hr'));
    return container;
}

(function init() {
    handleUserSearchInput();
})();