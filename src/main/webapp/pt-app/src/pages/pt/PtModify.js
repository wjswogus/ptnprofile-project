import React, { useState, useEffect } from 'react';

const PtModify = (props, route) => {
	const { no } = props.match.params;
	const [pt, setPt] = useState({});

	useEffect(() => {
		fetch("http://localhost:8000/ptDetail/" + no) 
		.then(res => res.json())
		.then(res => setPt(res));
	}, []);


	function updateBtn() {
		fetch("http://localhost:8000/pt/modify" + no,{
			method : "PUT",
			
		}) 
		.then()
		.then();
	}

	function deleteBtn() {
		fetch("http://localhost:8000/pt/remove/" + no, {
			method : "DELETE",
			headers : {
				"Authorization" : localStorage.getItem("Authorization"),
			}
		}) 
		.then(res => res.text())
		.then(res => {
			if(res === "ok") {
				alert("삭제 성공!!");
			} else {
				alert("삭제 실패!!");
			}
		});
	}

	return (
		<div>
			<h1>피티 수정</h1>
			<table>
				<tr>
					<td>제목 : <input type="text" name="pt_name" value={pt.pt_name} /></td>
				</tr>
				<tr>
					<td><img alt="" src={pt.pt_img} style={{width:"300px", height: "300px"}}  /></td>
				</tr>
				<tr>
					<td>내용 : <input type="text" name="pt_content" value={pt.pt_content} /></td>
				</tr>
				<tr>
					<td>주소 : <input type="text" name="pt_address" value={pt.pt_address} /></td>
				</tr>
				<tr>
					<td>가격 : <input type="text" name="pt_price" value={pt.pt_price} /></td>
				</tr>
				<button onClick={updateBtn}>수정</button>
				<button onClick={deleteBtn}>삭제</button>
			</table>
		</div>
	);
};

export default PtModify;