var userLogin;
var nombre;

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
	
	nombre = prompt('Ingrese el nombre del tablero: ');
	
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
        	let boardData = data.userData;
            if(data.status == 200){
                location.href ="";
                localStorage.setItem('tableros', JSON.stringify(boardDData));
                localStorage.setItem('boardId', JSON.stringify(boardData.board_id))
            }
        });		
}
