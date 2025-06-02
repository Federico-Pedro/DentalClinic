window.addEventListener('load', function() {
    const formulario = document.querySelector('#add_new_dentist');

    formulario.addEventListener('submit', function(event) {
        const formData = {
            registration: document.querySelector('#registration').value,
            name: document.querySelector('#name').value,
            lastName: document.querySelector('#lastName').value,
        };

        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong> Odontólogo agregado</strong></div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
            })
    })

    .catch(error => {
        '<div class="alert alert-danger alert-dismissible">' +
        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
        '<strong>Error, intente nuevamente</strong> </div>'

        document.querySelector('#response').innerHTML = errorAlert;
                        document.querySelector('#response').style.display = "block";
                        resetUploadForm();
    });

    function resetUploadForm(){
        document.querySelector('#registration').value = '';
        document.querySelector('#name').value = '';
        document.querySelector('#lastName').value = '';
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/dentistList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();

});