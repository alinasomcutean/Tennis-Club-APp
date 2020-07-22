# Tennis-Club-App
# 1. Description
1.1. An application for a tennis club that organizes matches using Java and Java FXML for interface. Every match is between 2 players. A match is played in ‘best of 5’ format (first player that gets 3 sets, wins). This means that each match is composed of a maximum of 5 sets. For each set, the first player to reach minimum 6 games by a margin of 2.

1.2. The application starts with a login window. It has two types of users: 
- regular user represented by the player 
- administrator user. 

Both kinds of uses have to provide an email and a password in order to access the application.

1.3. The regular user can perform the following operations:
- View Sets
- View Matches
- Update score of game (must be in the game, otherwise error).

1.4. The administrator user can perform the following operations:
- CRUD on player accounts
- CRUD on matches.

# 2. Application Contraints
- The data will be stored in a database
- Use the Layers architectural pattern to organize your application.
- The Data Access Layer (DAL) will be implemented using an ORM framework.
- The application will use a config file from which the system administrator can set which DAL implementation will be used to read and write data to the database.
- All the inputs of the application will be validated against invalid data before submitting the data and saving it in the database.
- Unit tests on business logic methods.
