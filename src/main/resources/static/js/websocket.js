/*
var socket = new SockJS('/secured/room');
var stompClient = Stomp.over(socket);
var sessionId = "";

stompClient.connect({}, function (frame) {
    var url = stompClient.ws._transport.url;
    url = url.replace(
        "ws://localhost:8081/spring-security-mvc-socket/secured/room/",  "");
    url = url.replace("/websocket", "");
    url = url.replace(/^[0-9]+\//, "");
    console.log("Your current session is: " + url);
    sessionId = url;
});

stompClient.subscribe('secured/user/queue/specific-user'
    + '-user' + that.sessionId, function (msgOut) {
    //handle messages
});
*/


// $('#btn-send-message').click(() => {
//
//     let message = $('#inp-message').val();
//
//     stompClient.send("/app/chat/" + selectedUserId, {}, JSON.stringify({
//         fromId: fromId,
//         message: message
//     }));
//
// });
//
// $('#btn-select-user').click(() => {
//
//     //todo cut id from href
//     let userId = window.location.href;
//
//     console.log(userId);
//     selectedUserId = userId;
//
// });

