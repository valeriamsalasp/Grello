var userLogin, boardId;

leerTablero();

function enviar(){
    var json ={
            user_username: document.getElementById("usuario").value,
            user_password: document.getElementById("contrasena").value
    }
    
    
    let configs = {
            method: 'post',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Login', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
                location.href ="../home/index.html";
                localStorage.setItem('sesion', JSON.stringify(userData));
                localStorage.setItem('id', JSON.stringify(userData.user_id))
            }
        });
}

function crearTablero(){
	console.log( "userData" + localStorage.getItem("sesion"));
	console.log(localStorage.getItem("id"));
	userLogin = localStorage.getItem("id");
	
	var nombre = prompt('Ingrese el nombre del tablero: ');
	
	var json ={
            tipo: "crear",
            board_name: nombre,
            user_id: userLogin
    }
    
    
    let configs = {
            method: 'post',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let boardData = data.userBoard;
            if(data.status == 200){
                location.href ="../tableros/index.html";
                localStorage.setItem('tableros', JSON.stringify(boardDData));
                localStorage.setItem('boardId', JSON.stringify(boardData.board_id))
            }
        });		
}

function crearColumna(){
	console.log( "userBoard" + localStorage.getItem("tableros"));
	console.log(localStorage.getItem("boardId"));
	boardId = localStorage.getItem("boardId");
	
	var nombre = prompt('Ingrese el nombre de la columna: ');
	
	var json ={
            tipo: "crear",
            board_id: userLogin,
            column_name: nombre
    }
    
    
    let configs = {
            method: 'post',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Columna', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let boardData = data.userBoard;
            if(data.status == 200){
                location.href ="../tableros/index.html";
                localStorage.setItem('tableros', JSON.stringify(boardDData));
                localStorage.setItem('boardId', JSON.stringify(boardData.board_id))
            }
        });
	
}

function crearTarjeta(){
	
}

function leerTablero(){
	userLogin = localStorage.getItem("id");
	userLogin = JSON.parse(userLogin);
	if(userLogin.legth > 0){
	
		var json ={
	            tipo: "leer",
	            user_id: userLogin
	    }
	    
	    
	    let configs = {
	            method: 'post',
	            body: JSON.stringify(json),
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'application/json'
	            }
	    }
	    fetch('../Tableros', configs)
	        .then(res => res.json())
	        .then(data => {console.log(data)
	        	let boardData = data.userBoard;
	            if(data.status == 200){ console.log(data)
	                localStorage.setItem('tableros', JSON.stringify(boardData));
	                localStorage.setItem('boardId', JSON.stringify(boardData.board_id))
	            }
	        });	
	}
	
}
