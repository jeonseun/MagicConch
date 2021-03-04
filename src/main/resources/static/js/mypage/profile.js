"use strict";
const endPoint = "http://localhost:8080";
const profileChangeUri = "/profile/image";

const profileImageInput = document.querySelector("#profileImageChange");
const profileImage = document.querySelector("#profileImage");
const profileImageNav = document.querySelector("#navProfileImage");

function profileImageChange() {
    if (!/\.(jpg|jpeg|png)$/i.test(profileImageInput.files[0].name)) {
        alert("업로드 불가능한 파일입니다.");
        return;
    }
    const formData = new FormData();
    formData.append('profileImage', profileImageInput.files[0]);
    fetch(endPoint + profileChangeUri, {
        method: "PUT",
        body: formData
    }).then(response => response.text())
        .then(data => {
            profileImage.src = endPoint + data;
            profileImageNav.src = endPoint + data;
        })
}

(function init() {
    profileImageInput.addEventListener('change', profileImageChange);
})();