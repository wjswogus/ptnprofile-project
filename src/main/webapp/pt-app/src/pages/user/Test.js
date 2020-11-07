import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Test = () => {
	const [boards, setBoards] = useState([]);
	const [keyword, setKeyword] = useState("");
	useEffect(() => {
		fetch("http://localhost:8000/boardList")
		.then(res => res.json())
		.then(res => {	
			setBoards(res.content);
		});
	},[]);

	function inputHandle(e) {
		setKeyword(e.target.value);
	}	
	
	const filter = boards.filter(board => board.title.indexOf(keyword) !== -1);

	return (		
		<div>
			<input type="text" name="keyword" onChange={inputHandle} />
			<h1>테스트</h1>
			<table border="1">
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>내용</td>
					<td>작성자</td>
				</tr>
				
				{filter.map((board) => 
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

export default Test;