import React from 'react';
import { Link } from 'react-router-dom';

const Nav = () => {
	return (
		<div>
			<li>
				<Link to="/join">
					회원가입
       			</Link>
			</li>
			<li>
				<Link to="/login">
					로그인
		        </Link>
			</li>
			<li>
				<Link to="/">
					게시판
		        </Link>
			</li>
			<li>
				<Link to="/ptList">
					피티게시판
				</Link>
			</li>
			<li>
				<Link to="/test">
					테스트
				</Link>
			</li>
			<hr/>
		</div>
	);
};

export default Nav;