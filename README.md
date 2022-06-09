# THE-MOVIES API

## Sobre esse projeto:

API possui integração com duas APIs externas:

Movies: https://www.themoviedb.org/documentation/api

Weather: https://openweathermap.org/api

A integração foi feita utilizando Spring Cloud OpenFeign.

O metódo findTopRated() no client MovieClient faz uma chamada a API externa  do The Movie DB passando os paramêtros necessários e obtém os filmes mais bem 
avaliados.

O metódo getWeatherByCity() no client WeatherClient faz uma chamada a API externa do Open Weather e obtém a temperatura atual da cidade desejada.

Ao startar o projeto a classe LocalConfig, dentro do package configs, realiza cinco chamadas ao método findTopRated() no client MovieClient, obtendo as 
cinco primeiras páginas contendo os filmes mais bem avaliados e após isso salva os filmes no banco de dados.

Os seguintes endpoints foram implementados:

- `GET /movies` obtém todos os filmes
- `GET /movies/{id}` obtém os detalhes de um filme
- `POST /movies` insere um novo filme
- `PUT /movies/{id}` atualiza um filme
- `DELETE /movies/{id}` exclui um filme
- `GET /movies/suggestions` obtém os sugestões de filmes baseado na temperatura atual

Foram implementados alguns testes para os endpoints acima.

As sugestões de filmes são feitas da seguintes forma:

- Temperatura acima de 40 graus, são sugeridos filmes do gênero de Ação.
- Temperatura entre 36 e 40 graus, são sugeridos filmes do gênero de Comédia.
- Temperatura entre 20 e 35 graus, são sugeridos filmes do gênero de Animação.
- Temperatura entre 0 e 20 graus, são sugeridos filmes do gênero de Drama.
- Temperatura abaixo de 0 grau, são sugeridos filmes do gênero de Documentários.

O endpoint de sugestões gera logs na API mostrando a cidade consultada, a temperatura atual e o genero dos filmes sendo exibidos.

Os generos dos filmes foram obtidos através da requisição: 
https://api.themoviedb.org/3/genre/movie/list?api_key={API_KEY}&language=en-US
e mapeados para uma ENUM.

A API possui documentação com Swagger disponível na seguinte URL: http://localhost:8081/swagger-ui.html

Collection no Postman utilizada para testes: https://www.getpostman.com/collections/7464bb20067292e1294d


