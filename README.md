# Bank-Account
Tecnologias utilizadas:
- Kotlin
- Gradle
- Docker

Organização do projeto:
- [Package by feature approach](http://www.javapractices.com/topic/TopicAction.do?Id=205)

Dependencias
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin) para transformação de Json em Objeto
- [Mockk](https://github.com/mockk/mockk) para mockar objetos nos testes
- [Shadowjar](https://github.com/johnrengelman/shadow) para criação do jar que será utilizado na imagem docker

Como rodar - na raiz do projeto executar:
- Docker
	- `docker build -t bank-account .`
	- `docker run --rm --name bank-account -it bank-account`
	- Enviar as transações a serem executadas
	- Enviar uma linha em branco informando que o arquivo acabou de ser importado
- Gradle
	- `gradle clean test run`

Main decisions
- Criação de algumas interfaces (`DataConsumer`, `Reader`, `OperationEvent`, `Operation`, `OperationViolation`) para facilitar a extensão caso o projeto tenha a necessidade de ler de outra fonte de dado
- Pelos exemplos executados entendi que a execução é um `batch` e não um `stream` mas seria simples a implementação do `stream`
- Para execução de qualquer evento é necessário inserir uma linha em branco (como se fosse a importação de um arquivo)
- Ordenação dos eventos de transação pelo campo `time`
- Retorno dos eventos na ordem que foram executados (diferente da entrada)
- Como a execução é sequenciada optei por nenhuma abordagem multi-thread mas poderia ser implementado algo com [corroutines](https://proandroiddev.com/synchronization-and-thread-safety-techniques-in-java-and-kotlin-f63506370e6d) ou [workers](https://kotlinlang.org/docs/reference/native/concurrency.html#workers)