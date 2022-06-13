# Description
This is a simple REST API backend system that is responsible for handling messages between users.

# Local run
To run it locally you need to install the Docker. 

You can run "docker-compose.yml" file to create pgadmin and postgres containers (in "docker-compose.yml" you can check login and password for postgres. Login and password are also placed in "application.properties").

In the root folder you can find "postman_collection.json" file for the tests in Postman.

# Auth:
In this project I described three users:

| **Login**    | elvispresley | bobdylan | mickjagger |
|:-------------|--------------|----------|------------|
| **Password** | pass123      | pass123  | pass123    |


To use the API you must choose "Basic Auth" option and provide a username and a password of one of the users (like on the picture below):

![Alt text](./screen.png?raw=true "Auth")


# Endpoints:
**Write message -**<br>localhost:8080/api/v1/message/write-message

**Get all messages for a specific user (logged-in user) -**<br>localhost:8080/api/v1/message/get-all-messages

**Get all UNREAD messages for a specific user (logged-in user) -**<br>localhost:8080/api/v1/message/get-all-unread-messages

**Read message (in this case every user has access to message, you need only the message ID) -**<br>localhost:8080/api/v1/message/read-message/{messageId}

**Delete message (in this case only sender or receiver can delete message by the message ID) -**<br>localhost:8080/api/v1/message/delete-message/{messageId}
