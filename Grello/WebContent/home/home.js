function cerrar(){
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Logout', configs)
        .then(data => {console.log(data)
                if (data.status ==  200){
                    localStorage.clear();
                    location.href ="../index.html";
                }
        });
}


