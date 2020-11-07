import React, { useState, useEffect } from 'react';

const Test2 = () => {
	const [wish, setWish] = useState([]);
	const [boards,setBoard] = useState([
		{
			user : 0,
		}
	])
	useEffect(() => {
		fetch("http://localhost:8000/ptList")
		.then(res => res.json())
		.then(res => {
			console.log(res.content);
			setBoard(res.content);
			
		}
			);
	}, []);
	return (
		<div>
			
			{boards.map((board) => 

				<tbody>
				<tr>
					<td>{board.user.uesrNo}</td>
				</tr>
				
				
			</tbody>
			)}
		</div>
	);
};

export default Test2;