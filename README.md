# Descrição do desafio

Construa uma aplicação que exponha uma api web que recebe por parametros um JWT (string) e verifica se é valida conforme regras abaixo:

- Deve ser um JWT válido
- Deve conter apenas 3 claims (Name, Role e Seed)
- A claim Name não pode ter carácter de números
- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
- A claim Seed deve ser um número primo.
- O tamanho máximo da claim Name é de 256 caracteres.

#  Definição
Input: Uma token JWT (string).  
Output: Um boolean indicando se a valido ou não conforme regras descritas acima.

#  Continue a partir desse ponto...
Foi disponibilizada uma aplicação inicial para apoiar na conclusão dessa tarefa. Você deve evoluir o código a partir da classe **TokenController**

![token](/img/token.png)

Para executar a aplicação, você pode utilizar qualquer IDE e ela está disponível executando um POST http://localhost:8080/validate

Também foi disponibilizado uma coleção no postman com exemplos de chamadas dos casos de teste exemplificados na massa de teste descrita abaixo.

![postman](/img/postman.png)



# Massa de teste 

### Caso 1:
Entrada:
```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```
Saida:
```
verdadeiro
```
Justificativa:
Abrindo o JWT, as informações contidas atendem a descrição:
```json
{
  "Role": "Admin",
  "Seed": "7841",
  "Name": "Toninho Araujo"
}
```

### Caso 2:
Entrada:
```
eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```
Saida:
```
falso
```
Justificativa:
JWT invalido

### Caso 3:
Entrada:
```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs
```
Saida:
```
falso
```
Justificativa:
Abrindo o JWT, a Claim Name possui caracter de números
```json
{
  "Role": "External",
  "Seed": "72341",
  "Name": "M4ria Olivia"
}
```

### Caso 4:
Entrada:
```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY
```
Saida:
```
falso
```
Justificativa:
Abrindo o JWT, foi encontrado mais de 3 claims.
```json
{
  "Role": "Member",
  "Org": "BR",
  "Seed": "14627",
  "Name": "Valdir Aranha"
}
```

# Execução da Tarefa


### Premissas

Premissas adotadas para o desenvolvimento.
1. **Simplicidade é a chave (KISS):** Optei por manter a abordagem simples, evitando complexidades desnecessárias. Isso não apenas facilita a compreensão do código, mas também simplifica a manutenção.

2. **Foco na tarefa em mãos:** Abordei a tarefa de maneira específica, evitando conjecturas sobre o negócio que podem não ser relevantes. Concentrei-me em resolver os desafios imediatos sem antecipar obstáculos futuros desconhecidos.

3. **Escolha do padrão MVC:**  Adotei o padrão Model-View-Controller (MVC) para proporcionar uma estrutura organizada ao projeto.

### Como foi feito

Foi desenvolvido um serviço através de uma interface, sendo um Model-View-Controller (MVC) padrão.

-  Introduzi um serviço com toda a lógica de negócios encapsulada, enquanto o controlador é responsável por receber as mensagens, invocar o serviço e retornar a resposta.

### Tests

- Os testes foram elaborados considerando o arquivo `TokenTests.java`, que foi expandido para abranger casos de teste mais abrangentes. Além disso, introduzi a classe `TokenTestFixture.java`, projetada para facilitar a geração de diferentes JWTs usando bibliotecas como Faker e Random para dados variados. Isso não só simplifica a criação de casos de teste, mas também enriquece a diversidade dos dados de entrada..

Para executar os testes, utilize o seguinte comando:
```bash

mvn test

```

Testes executados.
![Tests](/img/Tests-itau.png)

### Coverage

Executei o Jacoco para ter uma visualização do coverage de tests.
![coverage](/img/Coverage-itau.png)
