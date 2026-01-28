# Estudando Spring Boot 3 - Projeto Voll.med

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- Spring Validation
- MySQL 
- Maven   
- Lombok

## Links Úteis
- [Trello](https://trello.com/b/O0lGCsKb/api-voll-med)
- Layout da aplicação mobile [link](https://www.figma.com/design/N4CgpJqsg7gjbKuDmra3EV/Voll.med?node-id=2-1007&p=f)

## Anotações
- Para usar as anotações do Lombok, é necessário instalar o plugin do Lombok na IDE e configurar em: 
`File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing`;
- Para gerenciar a páginação, basta enviar os dados por url `{{url}}/medicos?size=5&page=1`;
- Para filtrar os dados, basta enviar os dados por url `{{url}}/medicos?especialidade=CARDIOLOGIA`;
- Para ordenar os dados, basta enviar os dados por url `{{url}}/medicos?sort=nome,desc` ou `{{url}}/medicos?sort=nome,asc`;
- ALterando o nome das propriedades enviadas na url `http://localhost:8080/medicos?tamanho=5&pagina=1&ordem=email,desc`
```
spring.data.web.pageable.page-parameter=pagina
spring.data.web.pageable.size-parameter=tamanho
spring.data.web.sort.sort-parameter=ordem
```
## Para executar o .jar no servidor
- O `-Dspring.profiles.active=prod` serve para qual perfil de configuração será utilizado, 
no caso o `prod` que está configurado no `application-prod.properties`.
- Passando as variaveis de ambiente durante execução do .jar `DDATASOURCE_PASSWORD`, `DDATASOURCE_URL`,
`DDATASOURCE_USERNAME`
````
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost/vollmed_api -DDATASOURCE_USERNAME=root
 -DDATASOURCE_PASSWORD=root -jar target/api-0.0.1-SNAPSHOT.jar
````
