async function postCommentToServer(cmtData){
    try {
        const url = '/comment/post';
        const config={
            method :'post',
            headers: {
                'content-type': 'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text() ;
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    console.log(cmtText);
    if(cmtText==null || cmtText==''){
        alert("댓글을 입력해주세요");
        cmtText.focus();
        return false;
    }else{
        let cmtData ={
            bno: bnoVal,
            writer: document.getElementById('cmtWriter').innerText,
            content:cmtText
        };
        console.log(cmtData);
        postCommentToServer(cmtData).then(result => {
            if(result > 0){
                alert("댓글이 등록되었습니다.");
            }
            // 화면에 출력
            getCommentList(cmtData.bno);
        });
    }

});

async function spreadCommentFromServer(bno){
    console.log(bno);
    try {
        const resp = await fetch('/comment/'+bno);//주소로 bno값을 달고 감
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function getCommentList(bno){
    spreadCommentFromServer(bno).then(result => {
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        if(result.length > 0){//값이 있다는 의미
            ul.innerHTML = "";
            for(let cvo of result){
                let li =`<li data-cno="${cvo.cno}" class="list-group-item d-flex justify-content-between align-items-start">`;
		            li += `<div class="ms-2 me-auto"><div class="fw-bold">${cvo.writer}</div>`;
                    li += `<input type="text" class="form-control" id="cmtTextMod" value="${cvo.content}"></div>`;
                    li += `<span class="badge bg-dark rounded-pill">${cvo.mod_at}</span>`;
                    li += `<button class="btn btn-sm btn-outline-warning mod" type="button">%</button>`;
                    li += `<button class="btn btn-sm btn-outline-danger del" type="button">X</button>`;
                    li += ` </li>`;

                    ul.innerHTML += li;
            }
        }else{
            let li = `<li class="list-group-item d-flex justify-content-between align-items-start"></li>`;
            ul.innerHTML = li;
        }
    });
}

async function editCommentToServer(cmtDataMod){
    try {
        const url = '/comment/'+cmtDataMod.cno;
        const config ={
            method : 'put',
            headers : {
                'content-type' : 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }

}

async function delCommentToServer(cnoVal){
    try {
        const url = '/comment/'+cnoVal;
        const config ={
            method: 'delete'
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('mod')){
        let li = e.target.closest('li');//나에게 달린 li중에 제일 처음에 잇는 li
        let cnoVal = li.dataset.cno;//li의 dataset중의 cno를 가져와서 저장
        let textContent = li.querySelector('#cmtTextMod').value;

        let cmtDataMod = {
            cno: cnoVal,
            content: textContent
        };
        console.log(cmtDataMod);
        editCommentToServer(cmtDataMod).then(result => {
            if(result > 0){
                alert("댓글 수정 완료");
            }
            getCommentList(bnoVal);
        });
    }else if(e.target.classList.contains('del')){
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;
        
        console.log(cnoVal);
        delCommentToServer(cnoVal).then(result => {
            if(result > 0){
                alert("댓글 삭제 완료");
            }
            console.log(bnoVal);
            getCommentList(bnoVal);
        });

    }
})