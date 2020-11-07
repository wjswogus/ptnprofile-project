import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const BoardList = () => {
	const [boards, setBoards] = useState([]);

	useEffect(() => {
		fetch("http://localhost:8000/boardList")
		.then(res => res.json())
		.then(res => {
			console.log(res.content);
			setBoards(res.content)
		});
	}, []);

	return (		
		<div>
			<h1>글목록</h1>
			<table border="1">
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>내용</td>
					<td>작성자</td>
				</tr>
				
				{boards.map((board) => 
				<tr>
					<td>{board.boardNo}</td>
					<td><Link to={`/boardDetail/${board.boardNo}`}>{board.title}</Link></td>
					<td>{board.content}</td>
					<td>{board.user.id}</td>
				</tr>
				)}
				
			</table>
			{localStorage.getItem("Authorization") !== null ?
				<Link to="/boardwrite" >글쓰러</Link> :
				""
			}
		</div>
	);
};

export default BoardList;