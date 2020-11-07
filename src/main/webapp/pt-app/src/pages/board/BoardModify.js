import React, { useState, useEffect } from 'react';

const BoardModify = (props) => {
	const [board, setBoard] = useState({
		title : "",
		content : "",
		user : {
			
		}
	});

	useEffect(() => {
		fetch("http://localhost:8000/boardDetail/" + props.match.params.no)
		.then(res => res.json())
		.then(res => setBoard(res));
	}, []);

	function inputHandle(e) {
		setBoard({...board, [e.target.name] : e.target.value })
	}

	function modifyBtn() {
		fetch("http://localhost:8000/board/modify/" + props.match.params.no, {
			method : "PUT",
			body : JSON.stringify(board),
			headers : {
				"Content-Type" : "application/json; charset=utf-8",
				"Authorization" : localStorage.getItem("Authorization"),
			}
		}) 
		.then(res => res.text())
		.then(res => {
			console.log(res);
			if(res === "ok") {
				alert("수정성공!!");
				props.history.push("/");
			} else {
				alert("수정실패!!");
			}
		});
	}

	function deleteBtn() {
		fetch("http://localhost:8000/board/delete/" + props.match.params.no, {
			method : "DELETE",
			headers : {
				"Authorization" : localStorage.getItem("Authorization"),
			}
		}) 
		.then(res => res.text())
		.then(res => {
			console.log(res);
			if(res === "ok") {
				alert("삭제성공!!");
				props.history.push("/");
			} else {
				alert("삭제실패!!");
			}
		});
	}

	return (
		<div>
			<h1>수정페이지</h1>
			<table border="1">
				<tr>
					<td>번호</td>
					<td>{board.boardNo}</td>
				</tr>
				<tr>	
					<td>제목</td> 
					<td><input type="text" name="title" value={board.title} onChange={inputHandle} /></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><input type="text" name="content" value={board.content} onChange={inputHandle} /></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td>{board.user.id}</td>
				</tr>
			</table>
			<button onClick={modifyBtn}>수정</button>
			<button onClick={deleteBtn}>삭제</button>
		</div>
	);
};

export default BoardModify;