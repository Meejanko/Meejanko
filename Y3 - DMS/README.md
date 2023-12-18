## Snake Game

### Project structure
MVC architecture is adopted. Under the controller package is the controller, which is responsible for the logic processing of the game. Under model is the game model data, util is an auxiliary class, and under the view package is the game interface.

### Game Logic
The game entity model is divided into food, snake head, and snake body. The game interface adopts a square shape. The size of each game element is determined by the interface width and game grid. After the game starts, it is judged whether there is food on the interface. If not, it is randomly generated. When a food is on the interface, the snake's movement first determines the direction of the snake's head, and increases or decreases the coordinates according to the direction of the snake's head. When the snake's head touches the food, the score increases, the existing food is removed, and new food is randomly generated. According to the score, the speed of the snake's movement is increased. Next, it is judged whether the snake's head collides with the wall and whether it collides with its own body. If it collides with its own body, the game is over. At the end of the game, the player's score is displayed and the player is reminded to enter their name and Save to file.

### Maintenance, changes and additions
To change from the swing version of Snake to the javafx version, you first need to smooth out the logic of the original code and find replacements for the components in swing based on the original logic, that is, which components to use in javafx. In swing, in javafx, the direction of the snake is also controlled by monitoring the up, down, left and right keys. After the basic version is implemented, functions such as ranking list, viewing help information, setting background color, starting, temporarily, and ending the game are added.
