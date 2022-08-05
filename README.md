# Card Game

API REST para jogar um exemplo de "card game" com escolha de filmes para tentar acertar qual tem melhor nota no IMDB.



**Passo 1:**

Acessar a pasta raíz do projeto e executar a aplicação Java com Spring Boot via IDE ou via comando maven.

**Passo 2:**

Usar a ferramenta Postman ou a documentação OpenApi em http://localhost:8080/swagger-ui/index.html

**Passo 3 (para jogar):**

1 - Criar usuário no endpoint POST (/movies/user);

2 - Fazer login usado o endpoint GET (/movies/login); -- Recebe id do usuário.

3 - Iniciar um novo jogo usando o endpoint POST (/movies/quiz/start/{id}) - O campo id é o id do usuário logado previamente.

4 - Começa as rodadas de 6 jogadas, **recebe o primeiro par de filmes** no endpoint GET (/movies/quiz/{id}) - O campo id é o id do usuário logado previamente.

5 - Ao escolher o filme que acha que tem melhor nota, usa o endpoint POST (/movies/quiz/answer/{id}) - O campo id é o id do usuário logado 
    Neste endpoint envia no body um JSON content com o filme escolhido e o outro filme para fins de comparação no backend.

6 - Se o jogador tentar apenas buscar mais pares de filmes, sem responder o par previamente mostrado, deve receber mensagem de que precisa responder
    o par anterior. Após esta mensagem, usa-se o endpoint GET (/movies/quiz/recover/{id}) para ajustar as jogadas e trazer o par anterior.
    
7 - O endpoint para mostrar o ranking após algumas jogadas por mais de um player é o GET (/movies/quiz/ranking).    
