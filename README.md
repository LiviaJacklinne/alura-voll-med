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

## Endpoints
- `GET /medicos?size=5&page=1` - Listar médicos com paginação, filtro e ordenação
- `POST /medicos` - Cadastrar um novo médico
```
{
    "nome": "Rodrigo Ferreira",
    "email": "rodrigo.ferreira@voll.med",
    "crm": "123456",
    "especialidade": "ORTOPEDIA",
    "telefone": "8798797",
    "endereco": {
        "logradouro": "rua 1",
        "bairro": "bairro",
        "cep": "12345678",
        "cidade": "Brasilia",
        "uf": "DF",
        "numero": "1",
        "complemento": "complemento"
        }
}
```
