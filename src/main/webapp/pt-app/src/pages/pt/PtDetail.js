import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const PtDetail = (props) => {
	const [pt, setPt] = useState({});
	const { no } = props.match.params; 

	useEffect(() => {
		fetch("http://localhost:8000/ptDetail/" + no) 
		.then(res => res.json())
		.then(res => {
			console.log(res);
			setPt(res);});
	}, []);

	return (
		<div>
			<h1>피티 상세정보</h1>
			<table>
				<tr>
					<td>제목 : {pt.pt_name}</td>
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
			</table>
			<button><Link to={`/PtModify/${pt.ptNo}`}>수정</Link></button>
		</div>
	);
};

export default PtDetail;