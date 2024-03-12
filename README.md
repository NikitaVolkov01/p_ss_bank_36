СМОТРЕТЬ МИКРОСЕРВИС public-info
--------------------------------------------------------------------------------
Stack:

Java 17

Spring Boot 2 - для более быстрой разработки бэка.

Maven - как инструмент сборки.

PostgreSQL - как БД.

Spring Data - для облегчения работы с БД.

Hibernate - как ORM.

Lombok - чтобы не писать boilerplate-код, используем на проекте Lombok.

Mapstruct - для маппинга, Mapstruct.

Liquibase - для миграции БД, Ссылка 1. Ссылка 2.

Openfeign - для использования эврика-сервера, и фейн-клиентов для общения между микросервисами.

Springdoc-openapi-ui - Swagger.

Junit5 - для тестов.

Mockito - для тестов.

Spring Test - для интеграционных тестов.

Spring-security - для аутентификации и авторизации.

Docker - для поднятия контейнеров.

Postman - тестирования через запросы.

Visual paradigm - для проработки базовый структуры БД и генерации ченджлогов.
--------------------------------------------------------------------------------
Бэкенд:
Проект основан на микросерверной архитектуре.
А каждый микросервис выстроен по архитектуре REST.
--------------------------------------------------------------------------------
Слои:
*config конфигурационные классы

*entity сущности базы данных

*dto специальные сущности для передачи/получения данных в/с апи

*repository dao-слой приложения, реализуем в виде интерфейсов Spring Data, имплементирующих JpaRepository.

*service бизнес-логика приложения, реализуем в виде интерфейсов и имплементирующих их классов.

*controller обычные и rest-контроллеры приложения.

*validator валидаторов.

*exception эксепшнов.

*handler хэндлеров.
