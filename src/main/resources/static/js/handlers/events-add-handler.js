function empty(str) {
    return str === '';
}

function isPicture (file) {
    return file.type === "image/png" || file.type === "image/jpeg";
}

$('#events-add-btn').click( () => {
    let title = $('#events-add-title').val();
    let description = $('#events-add-description').val();
    let markup = $('#events-add-markup').html();
    let file = $('#events-add-file')[0].files[0];
    let date = $('#events-add-date').val();
    let place = $('#events-add-place').val();

    //console.log(default_picture_path + file.name);
    console.log(file.type);
    console.log(file.source);
    console.log(markup);
    console.log(title);
    console.log(description);
    console.log(date);
    console.log(place);
    if(empty(title) || empty(description) || empty(markup) || empty(date) || empty(file) || empty(place)) {
        $('#events-add-error').html("Fill all fields").transition('fade in');
    } else if (!isPicture(file)) {
        $('#events-add-error').html("Picked file is not a picture").transition('fade in');
    } else {

        let data = new FormData();
        data.append("title",title);
        data.append("description", description);
        data.append("markup", markup);
        data.append("date", date);
        data.append("file", file);
        data.append("place", place);

        console.log(date);

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
                            let publisher_username = response.content;
                            window.location.href = "/profile/"+publisher_username;
                        }
                    },
                    error: function(response) {
                        console.log(response);
                    }
                });
    }
});