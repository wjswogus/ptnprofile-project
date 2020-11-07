import React, { useState } from 'react';

const Join = () => {
	const [user, setUser] = useState({
		id : "",
		password: "",
		name : "",
		email : "",
		address : "",
		auth_pt :  "",
	});

	function inputHanle(e) {
		setUser({...user, [e.target.name] : e.target.value })
		console.log(user);
	}

	function joinBtn() {
		fetch("http://localhost:8000/user/join", {
			method : "POST",
			body : JSON.stringify(user),
			headers : {
				"Content-Type" : "application/json; charset=utf-8"
			}
		})
		.then(res => res.text())
		.then(res => {
			if(res === "ok") {
				alert("회원가입 성공!!");
			} else {
				alert("회원가입 실패!!");
			}
		});
	}

	return (
		<div>
			<h1>회원가입</h1>
			<input type="text" name="id" placeholder="아이디" onChange={inputHanle} /> <br />
			<input type="password" name="password" placeholder="비밀번호" onChange={inputHanle} /> <br />
			<input type="text" name="name" placeholder="이름" onChange={inputHanle} /> <br />
			<input type="email" name="email" placeholder="이메일" onChange={inputHanle} /> <br />
			<input type="text" name="address" placeholder="주소" onChange={inputHanle} /> <br />
			유저 : <input type="radio" name="auth_pt" value="false" onChange={inputHanle} /> 
			트레이너 : <input type="radio" name="auth_pt" value="true" onChange={inputHanle} /> <br />
			<button onClick={joinBtn}>회원가입</button>
		</div>
	);
};

export default Join;