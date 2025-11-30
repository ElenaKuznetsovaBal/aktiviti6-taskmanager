
# Aktiviti 6.0 - Task Management System

Бэкенд-сервис для трекинга учебных задач с JWT аутентификацией.

## Особенности

- **Spring Boot 3.1.0** + **Java 17**
- **JWT аутентификация** с PrincessToken
- **H2 Database** (в памяти) для разработки
- **PostgreSQL** для продакшена (Docker)
- **Spring Security** с ролевой моделью
- **REST API** для управления задачами и группами

## Быстрый старт

### H2 Database (разработка)
```bash
./gradlew bootRun
Приложение: http://localhost:8081
H2 Console: http://localhost:8081/h2-console

PostgreSQL (Docker)
bash
docker-compose up -d
./gradlew bootRun

Основные эндпоинты и порядок использования
1. Регистрация пользователя
http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
2. Авторизация и получение токена
http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
В ответе будет PrincessToken для доступа к API.

3. Работа с задачами
http
GET /api/tasks
Authorization: PrincessToken

POST /api/tasks
Authorization: PrincessToken
Content-Type: application/json

{
  "title": "Новая задача",
  "description": "Описание задачи"
}
4. Работа с группами задач
http
GET /api/groups
Authorization: PrincessToken

POST /api/groups
Authorization: PrincessToken
Content-Type: application/json

{
  "name": "Учебные задачи"
}
5. Административные функции
http
GET /api/admin/users
Authorization: PrincessToken

GET /api/admin/statistics
Authorization: PrincessToken
Технологии
Java 17

Spring Boot 3.1.0

Spring Security + JWT

H2 Database / PostgreSQL

Gradle