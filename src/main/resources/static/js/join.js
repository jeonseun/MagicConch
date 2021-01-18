"use strict";
const endPoint = "http://localhost:8080";
const idCheckUri = "/idCheck";

const btnIdCheck = document.querySelector("#btnIdCheck");
const btnJoin = document.querySelector("#btnJoin");
const inputLoginId = document.querySelector("#loginId");

function addEventListenerForInputLoginId() {
    inputLoginId.addEventListener("keypress", function () {
        btnJoin.classList.add("disabled");
    });
}

function addEventListenerForBtnIdCheck() {
    btnIdCheck.addEventListener("click", function () {
        const data = { id: inputLoginId.value };
        fetch(endPoint + idCheckUri, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        }).then((response) => {
            if (response.status == 200) {
                alert("사용가능한 ID 입니다.");
                btnJoin.classList.remove("disabled");
            } else if (response.status == 400) {
                alert("사용불가능한 ID 입니다.");
                btnJoin.classList.add("disabled");
            }
        });
        btnJoin.classList.remove("disabled");
    });
}

(function init() {
    addEventListenerForBtnIdCheck();
    addEventListenerForInputLoginId();
})();
