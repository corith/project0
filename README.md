## Project0 Java api
#### Cory Sebastian

- This is a simple java api that keeps track of users, their roles, and "magic cards" that they own.
- It uses Java, maven, PostgreSQL, and tomcat.
- Currently, this was hacked together to be "ready" for a presentation, and it will be undergoing a major refactor in the upcoming days...

#### Known issues and spaghetti scheduled for refactoring before being deployed
    - There are several endpoints that currently return a string response and will be returning a HttpServletResponse error soon.
        - currently all of the user end points do not return appropriate error codes - most of the card end points do...
    - The current way that a user role is both represented and accessed in the code is super goofy and will be getting a refactor in the next couple days.
    - There is admin validation for the get all user's endpoint but not for the get all cards end point and that as well will be getting an updated in the next couple of days.
    - Currently, there are no unit tests, but there will be...
    - There is a way in the code to get a user by name but there is not a matching endpoint.
        - we will be adding enpoints to get both users and cards by name, email, card type, etc.
    
#### End points that are available are as follows
    - /project0/login (POST)
    - /project0/logout (POST)
    - /project0/users/add (POST - creates a new user)
    - /project0/users (GET - retrieves all users - needs an admin account to be logged in)
    - /project0/users/* (GET - retrieves a user by its id)
    - /project0/users/update/* (POST - updates a user by id...can only update a users name and email currently - will be adding update password soon - will also be making this PUT eventually)
    - /project0/users/delete/* (DELETE - deletes a user and all of their cards, by id)
    - /project0/cards/add (POST - creates a card in the database)
    - /project0/cards (GET - retreives all cards...currently can be accessed by anyone)
    - /project0/cards/* (GET - retreives a card by id)
    - /project0/cards/update/* (POST - updates a card by id...can update a cards name and type - will be adding the ability to update owner that way users can trade cards if they would like - also will be made a PUT at some point)
    - /project0/cards/delete/* (DELETE - deletes a card by id)
    
    