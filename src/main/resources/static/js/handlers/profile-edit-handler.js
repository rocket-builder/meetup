function isPicture (file) {
    return file.type === "image/png" || file.type === "image/jpeg";
}

$('#inp-profile-avatar').change(() => {
    let file = $('#inp-profile-avatar')[0].files[0];
    console.log(file);

    if(isPicture(file)) {
        let data = new FormData();
        data.append("file", file);
        $.ajax({
            type: "POST",
            url: "/profile/avatar/save",
            processData: false,
            contentType: false,
            data: data,
            success: function(response) {
                console.log(response);
            },
            error: function(response) {
                console.log(response);
            }
        });
    }
});

$('#inp-profile-background').change(() => {
    let file = $('#inp-profile-background')[0].files[0];
    console.log(file);

    if(isPicture(file)) {
        let data = new FormData();
        data.append("file", file);
        $.ajax({
            type: "POST",
            url: "/profile/background/save",
            processData: false,
            contentType: false,
            data: data,
            success: function(response) {
                console.log(response);
            },
            error: function(response) {
                console.log(response);
            }
        });
    }
});

$('#btn-profile-edit').click(() => {

    let username = $('#inp-profile-username').val();
    let about = $('#inp-profile-about').val();

    if(username !== '' && about !== '') {
        $.ajax({
            type: "POST",
            url: "/profile/edit/save",
            data: {username: username, about: about},
            success: function(response) {
                response = $.parseJSON(response);
                $('#message-profile-edit').html(response.message).transition('fade in');

                if(!response.isError) {
                    window.location.href="/profile/" + username + "/edit";
                }
            },
            error: function(response) {
                console.log(response);
            }
        });
    }
});