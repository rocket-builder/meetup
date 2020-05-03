function message(text) {
    $('#message-profile-settings').html(text).transition('fade in');
}

$('#btn-save-email').click(() => {

    let new_email = $('#inp-profile-email').val();

    if(new_email !== '') {
        $.ajax({
            type: "POST",
            url: "/profile/email/save",
            data: {email: new_email},
            success: function(response) {
                console.log(response);
            },
            error: function(response) {
                console.log(response);
            }
        });
    }
});

$('#btn-save-password').click(() => {

    let old_password = $('#inp-profile-password-old').val();
    let new_password = $('#inp-profile-password-new').val();
    let new_password2 = $('#inp-profile-password-new2').val();

    if(old_password === '' || new_password === '' || new_password2 === '') {
        message("Fill all password fields");
    } else if (new_password !== new_password2) {
        message("Passwords are not equal");
    } else {
        $.ajax({
            type: "POST",
            url: "/profile/password/save",
            data: {oldPassword: old_password, newPassword: new_password},
            success: function(response) {
                console.log(response);
                response = $.parseJSON(response);
                if(response.isError) {
                    message(response.message);
                } else {
                    $('#inp-profile-password-old').val('');
                    $('#inp-profile-password-new').val('');
                    $('#inp-profile-password-new2').val('');
                    $('#message-profile-settings').html(response.message).transition({
                        animation: 'flash',
                        onComplete: function () {
                            $('#message-profile-settings').transition('fade out');
                        }
                    })
                }
            },
            error: function(response) {
                console.log(response);
            }
        });
    }
});