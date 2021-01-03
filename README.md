# Magic Card Api

## Project Description
This is a simple api written in Java that keeps track of users, their roles, and "magic cards" that they own. A user can create an account and login. Once logged in the user can see the magic cards that belong to them.
 
## Technologies Used
- Java v8.0
- Maven v4.0.0
- PostgreSQL v13.0
- Tomcat v9.0.39
- AWS EC2
- Jenkins

## Features
- Users can create an account and login.
- The user's password will be hashed.
- A user can create a new magic card.
- A user can update a magic card.
- A user can update their name and email.
- There is a seeder called "Seeder.java"...run this class's main method once connected properly to the DB to seed the DB with dummy data provided by the Faker library.

#### Fantastic Future Features
- The ability for users to trade cards with one another.
- Increase validation for appropriate endpoints.
- Transfer project to spring boot.
- Allow a user to change their password.
- Write Junit tests for every endpoint.
- Make sure that endpoints return proper status codes.

## Getting Started
- Make sure you have a tomcat instance up and running
- ```git clone https://github.com/corith/project0.git```
- ```cd project0```
- ```mvn clean package```
- ```cp target/project0.war ~/path/to/tomcat/instance/webapps```
- I like to restart the tomcat server after each copy, however, that should be unnecessary...
- I suggest making an alias that includes steps 3,4, and the restart into one command like ```mvn_build```
- For example: ```alias mvn_build='mvn clean package && cp target/project0.war ~/path/to/tomcat/webapps && ~/path/to/tomcat/bin/./shutdown.sh && .~/path/to/tomcat/bin/./startup.sh'```
## Usage
Once you have the tomcat server running and the WAR file in the webapps directory, you can hit the local api at localhost:8080/project0/{endpoint}.

#### End points that are available to hit are below - Live example: http://18.191.121.52:8080/project0/cards
    - /project0/login (POST)
    - /project0/logout (POST)
    - /project0/users/add (POST - creates a new user)
    - /project0/users (GET - retrieves all users - requires authentication as an admin user)
    - /project0/users/{id} (GET - retrieves a user by its id)
    - /project0/users/update/{id} (POST - updates a user by id...can only update a users name and email currently - will be adding update password)
    - /project0/users/delete/{id} (DELETE - deletes a user and all of their cards, by id)
    - /project0/cards/add (POST - creates a card in the database)
    - /project0/cards (GET - retreives all cards...currently can be accessed by anyone)
    - /project0/cards/{id} (GET - retreives a card by id)
    - /project0/cards/update/{id} (POST - updates a card by id...can update a cards name and type - will be adding the ability to update owner that way users can trade cards if they would like)
    - /project0/cards/delete/{id} (DELETE - deletes a card by id)
    
#### Example Requests Bodies
Logging in
```json
{
    "userName": "King Admin",
    "password": "456"
}
```

Adding an admin User
```json
{
   "userName": "User Name",
   "password": "secret",
   "email": "username@gmail.com",
   "role_id": 1
}
```
Updating a User
```json
{
    "userName": "New Name",
    "email": "newEmail@yahoo.com"
}
```

Adding a Card
```json
{
  "name": "card name",
  "type": "type of magic",
  "owner": "2"
}
````
Updating a Card
```json
{
    "name": "new name",
    "type": "new type"
}
```
