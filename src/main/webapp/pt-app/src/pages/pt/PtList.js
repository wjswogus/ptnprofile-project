import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const PtList = () => {
	const [ptList, setPtList] = useState([]);

	useEffect(() => {
		fetch("http://localhost:8000/ptList")
		.then(res => res.json())
		.then(res => {
			setPtList(res.content);
			console.log(res);
		});
	}, []);

	return (	
		<div>
			<Link to="/ptWrite">피티 글쓰러</Link>
			<h1>피티 게시판</h1>
			<table>
				{ptList.map((pt) =>
				<tbody>
					<tr>
						<td><Link to={`/PtDetail/${pt.ptNo}`}>제목 : {pt.pt_name}</Link></td>
					</tr>
					<tr>
						<td><img alt="" src={pt.pt_img} style={{width:"300px", height: "300px"}} /></td>
					</tr>
					<tr>
						<td>내용 : {pt.pt_content}</td>
					</tr>
					<tr>
						<td>주소 : {pt.pt_address}</td>
					</tr>
					<tr>
						<td>가격 : {pt.pt_price}</td>
					</tr>
					<hr />
				</tbody>
				)}
			</table>
		</div>
	);
};

export default PtList;