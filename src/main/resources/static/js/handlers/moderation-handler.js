
$('.btn-moderation-publish').click(function () {
    let id = $(this).attr('id').split('eventPublish').pop();

    $('#approve-modal')
        .modal({
            blurring: true,
            closable  : true,
            onDeny    : function(){
                $(this).modal('hide');
            },
            onApprove : function() {
                $.ajax({
                    url: '/moderation/approve',
                    type: 'POST',
                    data: { id: id },
                    success: function (response) {
                        console.log(response);
                        $('#eventModeration'+id).transition('fade out').remove();
                    },
                    error: function (response) { console.log(response); }
                });
            }
        })
        .modal('show');
});


$('.btn-moderation-deny').click(function (){
    let id = $(this).attr('id').split('eventDeny').pop();

    $('#approve-modal')
        .modal({
            blurring: true,
            closable  : true,
            onDeny    : function(){
                $(this).modal('hide');
            },
            onApprove : function() {
                $.ajax({
                    url: '/moderation/deny',
                    type: 'POST',
                    data: { id: id },
                    success: function (response) {
                        console.log(response);
                        $('#eventModeration'+id).transition('fade out').remove();
                    },
                    error: function (response) { console.log(response); }
                });
            }
        })
        .modal('show');
});
