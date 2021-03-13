'use strict'

const host = "http://localhost:8080";
const uri = "/bundle/search"

const bundleSearchInput = document.querySelector("#bundleInput");
const searchContainer = document.querySelector(".search");

function handleBundleInputEvent() {
    bundleSearchInput.addEventListener("keyup", () => {
        if (bundleSearchInput.value !== '') {
            searchBundle(host, uri, 'bundleName=' + bundleSearchInput.value)
                .then(function (data) {
                    searchContainer.removeChild(bundleSearchInput.nextSibling);

                    const bundleSearchResult = document.createElement("div");
                    bundleSearchResult.classList.add('search__result');
                    searchContainer.appendChild(bundleSearchResult);

                    if (data !== 'no data') {
                        for (let i = 0; i < data.length; i++) {
                            let resultItem = createBundleSearchResult(data[i].name, data[i].tagName, data[i].tagImage, data[i].tagColor, data[i].accessLevel);
                            bundleSearchResult.appendChild(resultItem);
                        }
                    } else {
                        bundleSearchResult.innerHTML = '<h3>No Result</h3>'
                    }
                });

        }
    })
}

async function searchBundle(host, uri, params) {
    const url = host + uri + '?' + params;
    const response = await fetch(url);
    if (response.status === 200) {

        const data = await response.json();
        return data;
    } else if (response.status === 204) {
        return "no data";
    } else {
        throw Error(data)
    }
}

function createBundleSearchResult(name, tag, image, color, accessLevel) {
    const itemContainer = document.createElement("div");
    itemContainer.classList.add("search__result--bundle");

    const bundleTag = document.createElement("div");
    bundleTag.classList.add("bundle__tag");

    const tagName = document.createElement("h3");
    tagName.classList.add("bundle__tag-name");
    tagName.innerText = tag;
    tagName.style.textShadow = '1px 1px 3px ' + color;

    const tagImage = document.createElement("img");
    tagImage.classList.add("bundle__tag-image");
    tagImage.src = image;

    bundleTag.appendChild(tagImage);
    bundleTag.appendChild(tagName);

    const bundleInfo = document.createElement("div");
    bundleInfo.classList.add("bundle__info");

    const bundleName = document.createElement("div");
    bundleName.classList.add("bundle__info-name");
    bundleName.innerText = name;

    const bundleAccessLevel = document.createElement("div");
    bundleAccessLevel.classList.add("bundle__info-access-level");
    switch (accessLevel) {
        case 'PUBLIC':
            bundleAccessLevel.style.backgroundColor = '#00B74A';
            break;
        case 'GROUP':
            bundleAccessLevel.style.backgroundColor = '#B23CFD';
            break;
        case 'PRIVATE':
            bundleAccessLevel.style.backgroundColor = '#1266F1';
            break;
    }
    bundleAccessLevel.innerText = accessLevel;

    bundleInfo.appendChild(bundleAccessLevel);
    bundleInfo.appendChild(bundleName);

    const btnAddBundle = document.createElement("button");
    btnAddBundle.classList.add('btn', 'btn-info');
    btnAddBundle.innerText = '번들추가';
    btnAddBundle.style.flexBasis = '120px'
    itemContainer.appendChild(bundleTag);
    itemContainer.appendChild(bundleInfo);
    itemContainer.appendChild(btnAddBundle);
    return itemContainer;
}

(function init() {
    handleBundleInputEvent();
})();