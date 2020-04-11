function empty(str) {
    return str === '';
}

$('#login-btn').click(() => {

    let username = $('#login-username').val();
    let password = $('#login-password').val();
    //console.log(login + ' ' + password);

    if(empty(username) || empty(password)) { $('#login-error').html("Fill all fields") } else
    {
        $.ajax({
                    type: "POST",
                    url: "/login",
                    data: {
                        username: username,
                        password: password
                    },
                    success: function(response) {
                        console.log("LOGIN MODULE RESPONSE: " + response);
                        response = $.parseJSON(response);

                        if(response.isError) {
                            $('#login-error').html(response.message);
                        } else {
                            window.location.href="/profile";
                        }
                    },
                    error: function(response) {
                        console.log('ERROR: ' + response);
                    }
                });
    }
});

