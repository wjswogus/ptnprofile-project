import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import jwt_decode from 'jwt-decode';

const BoardDetail = (props) => {
	const no = props.match.params.no;
	const [userId, setUserId] = useState();

	const [board, setBoard] = useState({
		boardNo: "",
		title: "",
		content: "",
		user: {

		}
	});

	const [replyList, setReplyList] = useState([]);

	const [reply, setReply] = useState({
		b_re_content : "",
	});

	useEffect(() => {
		if(localStorage.getItem("Authorization") !== null) {
			let jwtTokenTemp = localStorage.getItem("Authorization");
			let jwtToken = jwtTokenTemp.replace("Bearer", "");		
			setUserId(jwt_decode(jwtToken).id);
		}
		
		fetch("http://localhost:8000/boardDetail/" + no)
			.then(res => res.json())
			.then(res => {
				console.log(res.reply);
				setBoard(res);
				setReplyList(res.reply);
			}); 
	}, [setReplyList]);

	function inputHandle(e) {
		setReply({...reply, [e.target.name] : e.target.value });
	}

	function replyBtn() {
		
		if(localStorage.getItem("Authorization") === null) {
			alert("로그인을 하지 않았습니다.");
		} else {
			fetch("http://localhost:8000/board/reply/" + no, {
				method : "POST",
				body : JSON.stringify(reply),
				headers : {
					"Content-Type" : "application/json; charset=utf-8",
					"Authorization" : localStorage.getItem("Authorization"),
				}
			})
			.then(res => res.text())
			.then(res => {
				if(res === "ok") {
					alert("댓글달기 성공!!");
					window.location.reload();
				} else {
					alert("댓글달기 실패!!");
				}
			});
		}	
	}

	function replyDelete(boardReplyNo) {

		fetch("http://localhost:8000/board/reply/" + boardReplyNo, {
			method : "DELETE",
			headers : {
				"Authorization" : localStorage.getItem("Authorization"),
			}
		})
		.then(res => res.text())
		.then(res => {
			if(res === "ok") {
				alert("삭제 성공");
				setReplyList(replyList.filter((r) => r.boardReplyNo !== boardReplyNo));
			} else {
				alert("삭제 실패");
			}
		});
	}

	return (
		<div>
			<h1>게시글 상세</h1>
			<table border="1">
				<tr>
					<td>번호</td>
					<td>{board.boardNo}</td>
				</tr>
				<tr>	
					<td>제목</td> 
					<td>{board.title}</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>{board.content}</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td>{board.user.id}</td>
				</tr>
			</table>
			{board.user.user_no === userId ? 
				<button><Link to={`/boardModify/${board.boardNo}`} style={{ textDecoration: "none" }}>수정</Link></button> : 
				""
			}
			
			<h3>댓글</h3>
			<textarea name="b_re_content" onChange={inputHandle} style={{resize : "none"}}></textarea> <br/>
			<button onClick={replyBtn}>댓글달기</button>
			<hr/>
			{
				replyList.map((reply) => 
				<tbody>
					<tr>
						<td>{reply.user.id}</td>
					</tr>
					<tr>
						<td>{reply.b_re_content}</td>
					</tr>
					{reply.user.user_no === userId ? 
						<tr>
							<button onClick={() => replyDelete(reply.boardReplyNo)}>삭제</button>
						</tr>
						: 
						""
					}
				</tbody>
			)}
		</div>
	);
};

export default BoardDetail;