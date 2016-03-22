/**
 * Created by Dima on 13.03.2016.
 */

function sendMessage() {

    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'message';

    var messageInfoDiv = document.createElement('div');
    messageInfoDiv.className = 'my-message-info';
    messageInfoDiv.innerHTML = new Date().toLocaleString() + ' <a href = "#" class ="change-mes" title = "Edit message" onclick="editMessage()"><img src = "http://s1.iconbird.com/ico/2013/3/636/w80h8013939672873.png" width=20em height=20em></a>' +
    '<a href = "#" class ="delete-mes" title = "Delete message" onclick="deleteMessage()"><img src = "http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/bin-full.png" width=20em height=20em></a>';


    var messageTextDiv = document.createElement('div');
    messageTextDiv.className = 'my-message-text';
    messageTextDiv.innerHTML = getMessageText();


    if(messageTextDiv.innerHTML.length > 100) {
        alert("Too big message!");
        return;
    }
    if(messageTextDiv.innerHTML.length == 0) {
        alert("You need to input something!");
        return;
    }

    newMessageDiv.appendChild(messageInfoDiv);
    newMessageDiv.appendChild(messageTextDiv);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];
    chatPanel.appendChild(newMessageDiv);

    changeUserActivity()
}

function ClearText(Empty) {
    Empty.value="";
}

function getMessageText() {
    var inputElement = document.getElementsByClassName('text-area')[0];
    return inputElement.value;
}

function changeUser() {
    var newName = prompt("Input your name", "Here: ");
    var newPanelHead = document.createElement('div');

    if(newName == undefined) {
        alert("Input your name!");
        return;
    }

    if((newName.length > 20) || (newName.length == "")) {
        alert("Incorrect name!")
        return;
    }
    var elements = window.document.getElementsByClassName('user');
    for(var i = 0; i < elements.length; i++) {
        if(elements[i].innerHTML == newName) {
            alert("User with such name is already exist!");
            return;
        }
    }

    newPanelHead.className = 'user-panel-head';
    newPanelHead.innerHTML = '<a href = "#" class = "user-activity" title = "Online"><img src ="http://s1.iconbird.com/ico/2013/3/636/w80h80139396727836.png" width=20em height=15em> </a>' + newName +
    '<a href = "#" class ="change-user" title = "Change user" onclick="changeUser()"><img src ="http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/user-boss.png" width=20em height=20em></a>';

    var userPanel = window.document.getElementsByClassName('user-panel')[0];
    var userPanelHead = window.document.getElementsByClassName('user-panel-head')[0];

    userPanel.replaceChild(newPanelHead,userPanelHead);
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
        '<a href = "#" class ="delete-mes" title = "Delete message" onclick="deleteMessage()"><img src = "http://www.iconsearch.ru/uploads/icons/49handdrawing/128x128/bin-full.png" width=20em height=20em></a>';

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

}

function click() {
    var t = event.target;
    var par = t.parentNode;
    var par2 = par.parentNode;
    var par3 = par2.parentNode;
    return par3;
}

function deleteMessage() {
    var newMessageDiv = document.createElement('div');
    newMessageDiv.className = 'del-message';

    var mesInfo = document.createElement('div');
    mesInfo.className = 'del-message-info';
    mesInfo.innerHTML = new Date().toLocaleString();

    var mesText = document.createElement('div');
    mesText.className = 'del-message-text';
    mesText.innerHTML = 'This message has been deleted';

    newMessageDiv.appendChild(mesInfo);
    newMessageDiv.appendChild(mesText);

    var chatPanel = window.document.getElementsByClassName('chat-screen')[0];

    chatPanel.replaceChild(newMessageDiv,click());
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

function CreateFile()
{
    var fso, f1;
    fso = new ActiveXObject("Scripting.FileSystemObject");
    f1 = fso.CreateTextFile("c:\\testfile.txt", true);
}