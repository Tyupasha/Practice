/**
 * Created by Dima on 13.03.2016.
 */

var user;
var messages = [];

window.onload = function() {
    var userList = localStorage.getItem('user');
    if(userList == undefined) {
        return;
    }
    userList = JSON.parse(userList);
    user = userList;
    setUserOnUI(user);

   var messageList = localStorage.getItem('messages');
    if (messageList == undefined) {
        return;
    }
    messageList = JSON.parse(messageList);
    for(var i in messageList) {
        if(messageList[i].text == 'Deleted') {
            addDeletedMessageOnUI(messageList[i]);
        }
        else {
            addMessageOnUI(messageList[i]);
        }
    }


};

function sendMessage() {
    if(user == undefined) {
        alert("Firstly input your name!");
        return;
    }

    if(getMessageText().length > 100) {
        alert("Too big message!");
        return;
    }
    if(getMessageText() == 0) {
        alert("You need to input something!");
        return;
    }

    var newMessage = {
        'id': setId(5),
        'date': new Date().toLocaleString(),
        'user': user,
        'text': getMessageText()
    };
    saveToLocalStorage(newMessage);
    addMessageOnUI(newMessage);

    alert(messages);
}

function saveToLocalStorage(message) {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var messageList = localStorage.getItem('messages');
    messageList = JSON.parse(messageList);
    for(var i in messageList) {
        messages.push(messageList[i]);
    }
    messages.push(message);
    localStorage.setItem('messages', JSON.stringify(messages));
    messages = [];
}

function addMessageOnUI(newMessage) {
    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'message';

    var messageInfoDiv = document.createElement('div');
    messageInfoDiv.className = 'my-message-info';
    messageInfoDiv.innerHTML = newMessage.date +
        ' <a href = "#" class ="change-mes" title = "Edit message" onclick="editMessage()">' +
        '<img src = "http://s1.iconbird.com/ico/2013/3/636/w80h8013939672873.png" width=20em height=20em></a>' +
        '<a href = "#" class ="delete-mes" title = "Delete message" onclick="deleteMessage()">' +
        '<img src = "http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/bin-full.png" width=20em height=20em></a>' + user;


    var messageTextDiv = document.createElement('div');
    messageTextDiv.className = 'my-message-text';
    messageTextDiv.innerHTML = newMessage.text;

    newMessageDiv.appendChild(messageInfoDiv);
    newMessageDiv.appendChild(messageTextDiv);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];
    chatPanel.appendChild(newMessageDiv);

    changeUserActivity();
}


function setUser() {
    var newName = prompt("Input your name", "Here: ");

    if(newName == undefined) {
        alert("Input your name!");
        return;
    }

    if((newName.length > 20) || (newName.length == "")) {
        alert("Incorrect name!");
        return;
    }

    var elements = window.document.getElementsByClassName('user');
    for(var i = 0; i < elements.length; i++) {
        if(elements[i].innerHTML == newName) {
            alert("User with such name is already exist!");
            return;
        }
    }

    saveUserToLocalStorage(newName);
    setUserOnUI(newName);
}

function saveUserToLocalStorage(newUser) {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }
    user = newUser;
    localStorage.setItem('user',JSON.stringify(user));
}

function setUserOnUI(name) {
    var newPanelHead = document.createElement('div');

    newPanelHead.className = 'user-panel-head';
    var newSpan = document.createElement('span');
    newSpan.innerHTML = name;
    newSpan.className = 'user-name';
    newPanelHead.innerHTML = '<a href = "#" class = "user-activity" title = "Online"><img src ="http://s1.iconbird.com/ico/2013/3/636/w80h80139396727836.png" width=20em height=15em> </a>' + newSpan.innerHTML +
    '<a href = "#" class ="change-user" title = "Change user" onclick="setUser()"><img src ="http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/user-boss.png" width=20em height=20em></a>';

    var userPanel = window.document.getElementsByClassName('user-panel')[0];
    var userPanelHead = window.document.getElementsByClassName('user-panel-head')[0];

    userPanel.replaceChild(newPanelHead,userPanelHead);
}

function editMessageInLocalStorage(index, newText) {
    var messageListJS = localStorage.getItem('messages');
    if (messageListJS == undefined) {
        return;
    }
    var messageList = JSON.parse(messageListJS);
    messageList[index].text = newText;
    localStorage.setItem('messages', JSON.stringify(messageList));
}

function editMessage() {
    var currentText = click().lastChild.innerHTML;
    var newText = prompt("Change your message: ", currentText.toString());

    if(newText == "") {
        alert("You should input something!");
        return;
    }

    if(newText == null) {
        return;
    }

    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'message';

    var messageInfoDiv = document.createElement('div');
    messageInfoDiv.className = 'my-message-info';
    messageInfoDiv.innerHTML = new Date().toLocaleString() + ' <a href = "#" class ="change-mes" title = "Edit message" onclick="editMessage()"><img src = "http://s1.iconbird.com/ico/2013/3/636/w80h8013939672873.png" width=20em height=20em></a>' +
        '<a href = "#" class ="delete-mes" title = "Delete message" onclick="deleteMessage()"><img src = "http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/bin-full.png" width=20em height=20em></a>' + user;

    var messageTextDiv = document.createElement('div');
    messageTextDiv.className = 'my-message-text';
    messageTextDiv.innerHTML = newText;

    if(messageTextDiv.innerHTML.length > 100) {
        alert("Too big message!");
        return false;
    }

    newMessageDiv.appendChild(messageInfoDiv);
    newMessageDiv.appendChild(messageTextDiv);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];
    chatPanel.replaceChild(newMessageDiv,click());

    var index = findMessageInLocalStorage(currentText);
    editMessageInLocalStorage(index, newText);
}

function deleteMessage() {
    var currentText = click().lastChild.innerHTML;
    var index = findMessageInLocalStorage(currentText);
    deleteMessageInLocalStorage(index);
    deleteMessageOnUI();
}

function deleteMessageInLocalStorage(index) {
    var messageListJS = localStorage.getItem('messages');
    if (messageListJS == undefined) {
        return;
    }
    var messageList = JSON.parse(messageListJS);
    messageList[index].text = 'Deleted';
    localStorage.setItem('messages', JSON.stringify(messageList));
}

function deleteMessageOnUI() {
    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'del-message';

    var mesInfo = document.createElement('div');
    mesInfo.className = 'del-message-info';
    mesInfo.innerHTML = new Date().toLocaleString() + ' ' + user;

    var mesText = document.createElement('div');
    mesText.className = 'del-message-text';
    mesText.innerHTML = 'This message has been deleted';

    newMessageDiv.appendChild(mesInfo);
    newMessageDiv.appendChild(mesText);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];

    chatPanel.replaceChild(newMessageDiv,click());
}

function addDeletedMessageOnUI(message) {
    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'del-message';

    var mesInfo = document.createElement('div');
    mesInfo.className = 'del-message-info';
    mesInfo.innerHTML = message.date + ' ' + user;

    var mesText = document.createElement('div');
    mesText.className = 'del-message-text';
    mesText.innerHTML = 'This message has been deleted';

    newMessageDiv.appendChild(mesInfo);
    newMessageDiv.appendChild(mesText);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];

    chatPanel.appendChild(newMessageDiv);
}

function changeUserActivity() {
    var userPanelHead = window.document.getElementsByClassName('user-panel-head')[0];
    var userActivity = window.document.getElementsByClassName('user-activity')[0];

    var newActivity = window.document.createElement('a');
    newActivity.className = 'user-activity';
    newActivity.title = 'Online';
    newActivity.innerHTML = '<img src ="http://s1.iconbird.com/ico/2013/3/636/w80h80139396727836.png" width=20em height=15em>';

    userPanelHead.replaceChild(newActivity, userActivity);
}

function ClearText(Empty) {
    Empty.value="";
}

function click() {
    var t = event.target;
    var par = t.parentNode;
    var par2 = par.parentNode;
    var par3 = par2.parentNode;
    return par3;
}

function getMessageText() {
    var inputElement = document.getElementsByClassName('text-area')[0];
    return inputElement.value;
}

function findMessageInLocalStorage(messageText) {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var messageList = localStorage.getItem('messages');
    if (messageList == undefined) {
        return;
    }
    messageList = JSON.parse(messageList);
    for(var i in messageList) {
        if(messageList[i].text == messageText) {
            return i;
        }
    }
}

function setId(length) {
    var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz".split("");
    if (! length) {
        length = Math.floor(Math.random() * chars.length);
    }
    var str = "";
    for (var i = 0; i < length; i++) {
        str += chars[Math.floor(Math.random() * chars.length)];
    }
    return str;
}






