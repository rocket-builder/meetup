function getDate() {
    const formatDate = date => ('0'+date.getDate()).slice(-2)  + '.' + ('0'+(date.getMonth() + 1)).slice(-2)  + '.' + date.getFullYear();
    return formatDate(new Date());
}

$('#btn-comment-add').click(() => {

    let text = $('#inp-comment-text').val();
    let eventId = document.location.pathname.split("/event/").pop();

    console.log(eventId);

    if(text !== '') {
        $.ajax({
            type: "POST",
            url: "/comment/add",
            data: {
                text: text,
                eventId: eventId
            },
            success: function (response) {
                console.log(response);

                response = $.parseJSON(response);
                if (!response.isError) {
                    $('#inp-comment-text').val('');
                    let commentHtml = getCommentHtml(text, response.content.toString(), response.content2.toString());
                    $('#comments-container').prepend(commentHtml).transition('fade in');
                }
            },
            error: function (response) {
                console.log(response);
            }
        });
    }
});

function getCommentHtml(text, username, avatarPath) {
    let avatar = avatarPath != null || avatarPath !== ''? '<img style="border-radius: 50%;" src="'+avatarPath+'">' : '<i class="ui tiny circular user icon"></i>';
    return '<div class="comment">\n' +
        '                        <a class="avatar">\n' + avatar + '</a>\n' +
        '                        <div class="content">\n' +
        '                            <a class="author" href="/profile/' + username + '">' + username + '</a>\n' +
        '                            <div class="metadata"><div class="date">' + getDate() + '</div></div>\n' +
        '                            <div class="text"><p>' + text + '</p></div>\n' +
        '                            <div class="actions"><a class="reply">Reply</a></div>\n' +
        '                        </div>\n' +
        '</div>';
}