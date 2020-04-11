function empty(str) {
    return str === '';
}

function isPicture (file) {
    return file.type === "image/png" || file.type === "image/jpeg";
}

$('#events-add-btn').click( () => {
    let title = $('#events-add-title').val();
    let description = $('#events-add-description').val();
    let markup = $('#events-add-markup').val();
    let file = $('#events-add-file')[0].files[0];
    let date = $('#custom').val();

    //console.log(default_picture_path + file.name);
    console.log(file.type);

    if(empty(title) || empty(description) || empty(markup) || empty(date) || empty(file)) {
        $('#events-add-error').html("Fill all fields").transition('shake');
    } else if (!isPicture(file)) {
        $('#events-add-error').html("Picked file is not a picture").transition('shake');
    } else {

        let data = new FormData();
        data.append("title",title);
        data.append("description", description);
        data.append("markup", markup);
        data.append("dates", date);
        data.append("file", file);

        $.ajax({
                    type: "POST",
                    url: "/events/add",
                    processData: false,
                    contentType: false,
                    data: data,
                    success: function(response) {
                        console.log("EVENTS ADD MODULE RESPONSE: " + response);
                        response = $.parseJSON(response);

                        if(response.isError) {
                            $('#events-add-error').html(response.message).transition('shake');
                        } else {
                            $('#events-add-message').html(response.message).transition({
                               duration: '3s',
                               animation  : 'flash',
                               onComplete : function() {

                                 window.location.href="/profile";
                               }
                             });
                        }
                    },
                    error: function(response) {
                        console.log(response);
                    }
                });
    }
});