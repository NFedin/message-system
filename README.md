# Description
This is a simple REST API backend system that is responsible for handling messages between users.

# Endpoints:
In the root folder you can find "postman_collection.json" file for the tests in Postman.<br><br>
**Write message** - creating message by using three fields(receiver, text and subject)<br>https://message-system-abra.herokuapp.com/api/v1/message/write-message

**Get all messages for a specific user (logged-in user)** - getting all messages for a logged-in user and make them "read"<br>https://message-system-abra.herokuapp.com/api/v1/message/get-all-messages

**Get all UNREAD messages for a specific user (logged-in user)** - getting all unread messages for a logged-in user and make them "read"<br>https://message-system-abra.herokuapp.com/api/v1/message/get-all-unread-messages

**Read message** - getting one message by messageID and make it "read" (in this case every user has access to message) <br>https://message-system-abra.herokuapp.com/api/v1/message/read-message/{messageId}

**Delete message** - delete one message by messageID (in this case only sender or receiver can delete message) <br>https://message-system-abra.herokuapp.com/api/v1/message/delete-message/{messageId}

# Auth:
In this project I described three users:

| **Login**    | elvispresley | bobdylan | mickjagger |
|:-------------|--------------|----------|------------|
| **Password** | pass123      | pass123  | pass123    |


To use the API you must choose "Basic Auth" option and provide a username and a password of one of the users (like on the picture below):

![Alt text](./screen.png?raw=true "Auth")

# Local run
To run it locally you need to install the Docker.

You can run "docker-compose.yml" file to create pgadmin and postgres containers (in "docker-compose.yml" you can check login and password for postgres. Login and password are also placed in "application.properties").