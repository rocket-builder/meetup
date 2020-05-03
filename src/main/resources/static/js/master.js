$(function () {
    $('.editable').mediumInsert({
        editor: editor
    });
});

// $('#inp-global-search').on('keyup',() => {
//
//     let match = $('#inp-global-search').val();
//
//     if(match.length > 1 && match !== '') {
//         $.ajax({
//             url: '/events/search',
//             type: 'POST',
//             data: { match: match },
//             success: function (response) {
//                 console.log(response);
//                 response = $.parseJSON(response);
//                 console.log(response);
//
//                 $('#search-results').html('');
//                 $.each(response, function (index, item) {
//                     if(item.isUser) {
//                         $('#search-results').append('<a class="item" href="/profile/'+item.value+'"><i class="user circular icon"></i>'+item.value+'</a>');
//                     } else {
//                         $('#search-results').append('<a class="item" href="/event/'+item.id+'"><i class="users circular icon"></i>'+item.value+'</a>');
//                     }
//                 });
//             },
//             error: function (response) { console.log(response); }
//         });
//     } else {
//         $('#search-results').html('');
//     }
// });

$('.ui.dropdown').dropdown();

// $.fn.api.settings.api = {
//     'search'        : '/events/search'
// };

$('.ui.search')
    .search({
        minCharacters : 3,
        apiSettings   : {
            onResponse: function(data) {
                let response = {
                        results: []
                    };

                $.each(data, function (index, item) {
                   response.results.push(item);
                });
                response.results.reverse();
                return response;
            },
            url: '/events/search?match={query}'
        }
    })
;

function sidebar() {
    $('.ui.sidebar')
        .sidebar('setting', 'transition', 'scale down')
        .sidebar('toggle');
}
function resizeInput() {
    $(this).attr('size', $(this).val().length);
}

$('input[type="text"].custom-simple').keyup(resizeInput).each(resizeInput);

function deleteEvent(element) {
    let id = element.id.split('del').pop();
    $('#delete-modal')
        .modal({
            blurring: true,
            closable  : true,
            onDeny    : function(){
                $(this).modal('hide');
            },
            onApprove : function() {
                $.ajax({
                    url: '/events/delete',
                    type: 'POST',
                    data: { id: id },
                    success: function (response) {
                        console.log(response);
                        $('#event'+id).transition('fade out').remove();
                    },
                    error: function (response) { console.log(response); }
                });
            }
        })
        .modal('show');
}

function subscribe(element) {
    let id = element.id.split('event').pop();

    $.ajax({
        url: '/events/subscribe',
        type: 'POST',
        data: { id: id },
        success: function (response) {
            console.log(response);
            response = $.parseJSON(response);

            if(response.isError && response.message === 'Signup')
                window.location.href = '/signup';
            if(response.message === 'Subscribed')
                $('#event'+id).html('Unsubscribe').transition('fade in');
            if(response.message === 'Unsubscribed')
                $('#event'+id).html('Subscribe').transition('fade in');
        },
        error: function (response) { console.log(response); }
    });
}
function preview(input) {
    if (input.files[0]) {
        var fr = new FileReader();
        fr.addEventListener("load", function () {
            $('#photo-preview').css('background:' , 'linear-gradient(to top, rgba(249, 170, 13, 0.3), rgba(249, 170, 13, 0.3)),' +
                'url('+fr.result+');'+
                'background-position: center center;').transition('fade in');
        }, false);

        fr.readAsDataURL(input.files[0]);
    }
}


