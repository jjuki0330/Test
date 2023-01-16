document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('files').click();//files라는 아이디를 가진 애를 클릭할 수 있게
});

//파일에 대한 정규표현식을 이용한 생성자 함수 만들기
//fileupload 시 형식 제한 함수
//실행파일 막기
//이미지 파일만 넣을 수 있게
//정규식의 시작은 \, 끝은 $
const regExp = new RegExp("\.(exe|sh|bat|msi|dll|js)$");//이 형식(실행파일 형식)의 확장자는 들어올 수 없어! => 거부할 파일
const regExpImg = new RegExp("\.(jpg|jpeg|png|gif)$");//이미지 파일 => 허용할 파일
const maxSize = 1024*1024*20;//이 크기보다 용량이 크면 첨부파일에 업로드해주지 않겠다!

function fileSizeValidation(fileName,fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(!regExpImg.test(fileName)){
        return 0;
    }else if(fileSize>maxSize){
        return 0;
    }else{
        return 1;
    }
}

document.addEventListener('change',(e)=>{
    if(e.target.id == "files"){
        //출력버튼을 비활성화(첨부되지 말아야하는 파일이 들어왔을 때 클릭되는 것을 방지하기 위함)
        //위에걸 해주는 이유: false에 걸리는 애들을 submit하면 안되기 때문에
        document.getElementById('regBtn').disabled =false;
        //input type="file" element fileObject 객체로 리턴
        const fileObject = document.getElementById('files').files;
        console.log(fileObject);

        let div = document.getElementById('fileZone');
        div.innerHTML="";
        let ul = '<ul class="list-group list-group-flush">';
        let isOk = 1;//fileSizeValidation을 통과했는지에 대한 값을 받으려고 함
        for(let file of fileObject){
            let validResult = fileSizeValidation(file.name, file.size);//결과 값이 : 0 or 1
            isOk *= validResult;//업로드한 모든 파일이 허용되는 파일인지 체크(만약 하나라도 거부되는 파일이면 값이 0)
            ul+=`<li class="list-group-item d-flex justify-content-between align-items-start">`;
            ul+=`${validResult?'<div class="fw-bold">업로드 가능':'<div class="fw-bold text-danger">업로드 불가'}</div>`;//업로드가 가능한지 불가능한지에 대한 결과를 넣을 것
            ul+=`${file.name}`;
            ul+=`<span class="badge bg-${validResult?'success':'danger'} rounded-pill">${file.size} Bytes</span></li>`;
        }
        ul+=`</ul>`;
        div.innerHTML=ul;

        if(isOk==0){
            document.getElementById('regBtn').disabled = ture;
        }
    }
})

async function delFileToServer(uuidVal){
    try {
        const url ='/board/'+uuidVal;
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

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('file-x')){
        let li = e.target.closest('li');
        let uuidVal = e.target.dataset.uuid;

        console.log(uuidVal);
        delFileToServer(uuidVal).then(result => {
            if(result>0){
                alert("파일 삭제 완료");
                li.remove();
            }
        })

    }
})
