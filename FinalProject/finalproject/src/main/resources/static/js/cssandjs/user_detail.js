const addressEl = document.getElementById("address");

// Lấy danh sách tỉnh thành phố
const getDistrict = async () => {
    try {
        let res = await axios.get("https://provinces.open-api.vn/api/p/");
        renderDistrict(res.data);
    } catch (error) {
        console.log(error);
    }
}


// Hiển thị tỉnh thành phố

const renderDistrict = (arr) => {
    let html = "";
    for(let i = 0 ; i < arr.length ; i++){
        const d = arr[i];
        html += `<option value="${d.name}">${d.name}</option>`;
    }
    addressEl.innerHTML = html;
}