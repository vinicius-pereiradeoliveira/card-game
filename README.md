# Card Game

REST API to play an example of a "card game" with choosing films to try to guess which one has the best rating on IMDB.


**Step 1:**

Access the project root folder and run the Java application with Spring Boot via the IDE or via the maven command.

**Step 2:**

Use the Postman tool or the OpenApi documentation at http://localhost:8080/swagger-ui/index.html

**Step 3 (play game):**

1 - Create user at the POST endpoint (/movies/user);

2 - Log in using the GET endpoint (/movies/login); -- Receives user id.

3 - Start a new game using the POST endpoint (/movies/quiz/start/{id}) - The id field is the id of the previously logged in user.

4 - Start the rounds of 6 moves, **receive the first pair of movies** in the GET endpoint (/movies/quiz/{id}) - The id field is the id of the previously logged in user.

5 - When choosing the film that you think has the best rating, use the POST endpoint (/movies/quiz/answer/{id}) - The id field is the id of the logged in user
     This endpoint sends a JSON content in the body with the chosen film and the other film for comparison purposes in the backend.

6 - If the player just tries to search for more pairs of films, without responding to the pair previously shown, they should receive a message that they need to respond
     the previous pair. After this message, the GET endpoint (/movies/quiz/recover/{id}) is used to adjust the moves and bring the previous pair.
    
7 - The endpoint to show the ranking after a few plays by more than one player is GET (/movies/quiz/ranking).   
