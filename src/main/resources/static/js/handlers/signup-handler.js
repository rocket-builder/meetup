function empty(str) {
    if(str == '') { return true }
    else
        return false;
}

$('#signup-send-code-btn').click(() => {

    let login = $('#signup-login').val();
    let email = $('#signup-email').val();
    let password = $('#signup-password').val();
    let password2 = $('#signup-password2').val();

    console.log(login + ' ' + password + ' ' + email);

    if(password != password2) { alert('passwords are not equal'); } else
    if(empty(login) || empty(email) || empty(password || empty(password2))) { alert('Fill all fields'); } else
    {
        $.ajax({
                    type: "POST",
                    url: "/signup",
                    data: {
                        username: login,
                        email: email,
                        password: password
                    },
                    success: function(response) {
                        console.log("SIGNUP MODULE RESPONSE: SUCCESS");

                    },
                    error: function(response) {
                        console.log('ERROR: ' + response);
                    }
                });
    }
});