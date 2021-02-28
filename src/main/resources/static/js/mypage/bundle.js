const endPoint = "http://localhost:8080";

const bundleList = document.getElementById('bundleList');

function createBundleSimpleCard(id, name, tagName, tagColor, visibility) {

    // 번들 심플 카드 내용을 담는 컨테이너
    const card = document.createElement('a');
    card.href = endPoint + "/bundle" + "/" + id;
    card.classList.add('card');

    const cardHeader = document.createElement('header');
    cardHeader.classList.add('card-header', 'd-flex', 'justify-content-between');
    cardHeader.style.backgroundColor = tagColor;

    const cardTagName = document.createElement('span');
    cardTagName.innerText = tagName;

    const cardVisibility = document.createElement('strong');
    cardVisibility.innerText = visibility;
    cardHeader.appendChild(cardTagName);
    cardHeader.appendChild(cardTagName);

    const cardBody = document.createElement('div');
    const cardBundleName = document.createElement('p');
    cardBundleName.innerText = name;
    cardBundleName.classList.add('card-text');
    cardBody.appendChild(cardBundleName);

    card.appendChild(cardHeader);
    card.appendChild(cardBody);
    return card;
}

function createForbiddenBundleCard() {
    const card = document.createElement('div');
    card.innerText = '접근 권한 필요';
    return card
}

function getBundles(username) {
    fetch(endPoint + "/bundle?" + `username=${username}`, {
        method: "GET"
    }).then(response => response.json())
        .then(data => {
            for (let i = 0; i < data.dtoList.length; i++) {
                if (data.dtoList[i] == null) {
                    bundleList.appendChild(createForbiddenBundleCard());
                } else {
                    bundleList.appendChild(createBundleSimpleCard(
                        data.dtoList.bundleId,
                        data.dtoList.name,
                        data.dtoList.tagName,
                        data.dtoList.tagColor,
                        data.dtoList.visibility
                    ));
                }
            }
        });
}