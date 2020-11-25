## Project0 Java api
#### Cory Sebastian

- This is a simple java api that keeps track of users, their roles, and "magic cards" that they own.
- It uses Java, maven, and tomcat.
- Currently, this was hacked together to be "ready" for a presentation, and it will be undergoing a major refactor in the upcoming days...

#### Known issues and spaghetti scheduled for refactoring before being deployed
    - There are several inconsistencies in naming conventions throughout the project as well as with endpoints.
    - Currently, the user's password is stored in plain text on the database and will be instead hashed in the next couple of days.
    - Several servlet endpoints are wildy inconsistent as it pertains to which servelts hold what endpoints.
        - most of the user servlets contain one endpoint per servlet while the card servlets use one messy servlet route all the end points.
        - the user servlets will be refactored to more closely match the card servlets...and both will be cleaned up drastically.
    - There are several endpoints that currently return a string response and will be returning a HttpServletResponse error soon.
        - currently all of the user end points do not return appropriate error codes - most of the card end points do...
    - The current way that a user role is both represented and accessed in the code is super goofy and will be getting a refactor in the next couple days.
    - There is admin validation for the get all user's endpoint but not for the get all cards end point and that as well will be getting an updated in the next couple of days.
    - Currently, there are no unit tests, but they will be implemented by the end of weekend.
    - Several constructors that are unnecessary will be changed
    - A couple methods return values that are not used and should be of a void return type.
    - There is a way in the code to get a user by name but there is not a matching endpoint.
        - we will be adding enpoints to get both users and cards by name, email, card type, etc. 
    - Although variable names are named with clarity in mind, the current documentation is currently very lacking and some of it may even be misleading...we will be adding accurate and useful documentation soon as well. 
    - There are several other simple but important things that will be occurring/getting fixed in the next couple of days to improve both code readability as well as functionality...stay tuned.
    
#### End points that are available are as follows
    - /project0/login
    - /project0/logout
    - /project0/users (a get request as an admin user returns all users, there roles, and all the cards they own.A post request with the necessary parameters inserts a new user)
    - /project0/user/* (gets a user by its id)
    - /project0/user/update/* (updates a user by id...can only update a users name and email currently - will be adding update password soon)
    - /project0/user/delete/* (deletes a user by id)
    - /project0/cards (returns all cards...currently can be accessed by anyone)
    - /project0/cards/* (returns a card by id)
    - /project0/cards/add (inserts a card into the database)
    - /project0/cards/delete/* (deletes a card by id)
    - /project0/card/update/* (updates a card by id...can update a cards name and type - will be adding the ability to update owner that way users can trade cards if they would like)
    
    