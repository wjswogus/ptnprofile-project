import React, { useState } from 'react';

const PtWrite = (props) => {

	const [pt, setPt] = useState({});

	function inputHandle(e) {
		setPt({...pt, [e.target.name] : e.target.value });
	}

	function ptWriteBtn() {
		let form = document.getElementById("form");
		const formData = new FormData(form);
		
		fetch("http://localhost:8000/pt/write", {
			method : "POST",
			body : formData,
			headers : {
				"Authorization" : localStorage.getItem("Authorization"),
			}
		})
		.then(res => res.text())
		.then(res => {
			if(res === "ok") {
				alert("피티등록 성공!!");
				props.history.push("/ptList");
			} else {
				alert("피티등록 실패!!");
			}
		});
	}

	

	return (
		<div>
			<form id="form">
				<input type="text" name="pt_name" placeholder="피티이름" onChange={inputHandle} /> <br/>
				<input type="text" name="pt_address" placeholder="피티주소" onChange={inputHandle} /> <br/>
				<input type="file" name="pt_img" onChange={inputHandle} /><br/>
				<input type="text" name="pt_content" placeholder="피티내용" onChange={inputHandle} /><br/>
				<input type="text" name="pt_price" placeholder="피티가격" onChange={inputHandle} /><br/>
			</form>
			<button onClick={ptWriteBtn}>피티등록</button>	
		</div>
	);
};

export default PtWrite;