
window.onload = () => {
    request();
}

function getId(){
    let url = location.href; //링크주소
    
    console.log(url);
    let id = url.substring(url.lastIndexOf("/")+1); 
    //링크주소 마지막 숫자(페이지 번호 구하기)

    console.log(id);

    return id;
}

function request() {
    $.ajax({
        async: false,
        type: "get",
        url: "/api/news/" + getId(),
        dataType: "json",
        success: (response) => {
            console.log(response);
            setNewsData(response.data);
        },
        error: (error) => {
            console.log(error);
        }
    })
}

function setNewsData(news) {
    const newsTitle = document.querySelector(".news-title");
    const newsBroadcasting = document.querySelector(".news-broadcasting");
    const newsWriter = document.querySelector(".news-writer");
    const newsCreateDate = document.querySelector(".news-create-date");
    const newsContent = document.querySelector(".news-content");
    const newsfiles = document.querySelector(".news-files");

    newsTitle.textContent = news.title;
    newsBroadcasting.textContent = news.broadcastingName;
    newsWriter.textContent = news.writer;
    newsCreateDate.textContent = news.createDate;
    newsContent.textContent = news.content;
    newsfiles.innerHTML = `<a href="/download/news?originFileName=${news.fileList[0].file_origin_name}&tempFileName=${news.fileList[0].file_temp_name}">${news.fileList[0].file_origin_name}</a>`;
}