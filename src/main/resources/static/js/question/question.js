const tagDrop = document.getElementById('tagDrop');
const tagBtn = document.getElementById('tagBtn');
const tagTxt = document.getElementById('tagTxt');
const bundleTxt = document.getElementById('bundleTxt');
const bundleTxtHdn = document.getElementById('bundleTxtHdn');

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
