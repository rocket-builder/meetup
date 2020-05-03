function empty(str) {
    return str === '';
}
function isPicture (file) {
    return file.type === "image/png" || file.type === "image/jpeg";
}

function editEvent(element) {
    let title = $('#events-edit-title').val();
    let description = $('#events-edit-description').val();
    let markup = $('#events-edit-markup').html();
    //let file = $('#events-edit-file')[0].files[0];
    let date = $('#events-edit-date').val();
    let place = $('#events-edit-place').val();
    let id = element.id.split('edit').pop();

    console.log(id);
    //console.log(default_picture_path + file.name);
    //console.log(file.type);

    if(empty(title) || empty(description) || empty(markup) || empty(date)
        //|| empty(file)
        || empty(place)) {
        $('#events-edit-error').html("Fill all fields").transition('shake');
    } else if (
        //isPicture(file)
        true
    )
    {
        let data = new FormData();
        data.append("title", title);
        data.append("description", description);
        data.append("markup", markup);
        data.append("dates", date);
        //data.append("file", file);
        data.append("place", place);
        data.append("editId", id);
        $.ajax({
            type: "POST",
            url: "/events/edit",
            processData: false,
            contentType: false,
            data: data,
            success: function (response) {
                console.log("EVENTS EDIT MODULE RESPONSE: " + response);
                response = $.parseJSON(response);

                if (response.isError) {
                    $('#events-edit-error').html(response.message).transition('fade in');
                } else {
                    let publisher_username = response.content;
                    window.location.href = "/profile/"+publisher_username;
                }
            },
            error: function (response) {
                console.log(response);
            }
        });
    } else {
        $('#events-edit-error').html("Picked file is not a picture").transition('shake');
    }
}
