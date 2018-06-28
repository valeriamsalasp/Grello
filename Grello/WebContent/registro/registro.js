function enviar() {
    	    var json ={
    	    		user_name: document.getElementById("nombre").value,
    	    		user_last_name: document.getElementById("apellido").value,
    	    		user_username: document.getElementById("usuario").value,
    	    		user_password: document.getElementById("contrasena").value,
    	    		user_email: document.getElementById("correo").value
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
    	    fetch('../Registro', configs)
    	    	.then(res => res.json())
    	    	.then(data => {console.log(data)
    	    			if (data.status ==  200){
    	        	    	location.href ="../login/index.html";
    	        	    }
    	    	a = data.user_name;
    	    	});
    	    
    	}