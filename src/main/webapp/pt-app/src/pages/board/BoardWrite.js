import React, { useState } from 'react';

const BoardWrite = (props) => {
	const [board, setBoard] = useState({});

	function inputHandle(e) {
		setBoard({...board, [e.target.name] : e.target.value })
	}

	function writeBtn() {
		fetch("http://localhost:8000/board/write", {
			method : "POST",
			body : JSON.stringify(board),
			headers : {
				"Content-Type" : "application/json; charset=utf-8",
				"Authorization" : localStorage.getItem("Authorization"),
			}
		})
		.then(res => res.text())
		.then(res => {
			if(res === "ok") {
				alert("글작성 성공!!");
				props.history.push("/");
			} else {
				alert("글작성 실패!!");
			}
		});
	}

	return (
		<div>
			<h1>글쓰기</h1>
			<input type="text" name="title" onChange={inputHandle} placeholder="글제목" /> <br/>
			<input type="text" name="content" onChange={inputHandle} placeholder="글내용" /> <br/>
			<button onClick={writeBtn}>글작성</button>
		</div>
	);
};

export default BoardWrite;