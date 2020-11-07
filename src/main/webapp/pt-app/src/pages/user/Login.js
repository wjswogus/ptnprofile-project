import React, { useState } from 'react';

const Login = (props) => {
	const [user, setUser] = useState({});

	function inputHandle(e) {
		setUser({...user, [e.target.name] : e.target.value })
	}

	function loginBtn() {
		fetch("http://localhost:8000/user/login", {
			method : "POST",
			body : JSON.stringify(user),
			headers : {
				"Content-Type" : "application/json; charset=utf-8",
			}
		})
		.then(res => {
			for(let header of res.headers.entries()) {
				if(header[0] === "authorization") {
					localStorage.setItem("Authorization", header[1]);
				}
			}
			return res.text();
		})
		.then(res => {
			if(res === "ok") {
				alert("로그인 성공!!");
				props.history.push("/");
			} else {
				alert("로그인 실패!!");
			}
		});
	}

	return (	
		<div>
			<h1>로그인</h1>
			<input type="text" name="id" onChange={inputHandle} /> <br/>
			<input type="password" name="password" onChange={inputHandle} /> <br/>
			<button onClick={loginBtn}>로그인</button>
		</div>
	);
};

export default Login;