# Бэкенд сервис для бронирования отелей 
Spring-приложение для бронирования отелей с возможностью управлять контентом через административную панель CMS

# Функциональность
- Управление отелями
- Управление номерами
- Регистрация и аутентификация пользователей
- Бронирование номеров
- Система оценок отелей
- Поиск и фильтрация
- Сбор статистики
- Экспорт статистики
- Docker контейнеризация (приложение + PostgreSQL)

# Технологии
- Backend: Java 17, Spring Boot 3.2.0
- База данных PostgreSQL 15
- Безопасность: Spring Security (Basic Auth)
- ORM Spring Data JPA, Hibernate
- Валидация: Bean Validation
- CSV экспорт: OpenCSV
- liquibase

# Структура программы
src/main/java/com/example/springexample/
 - controllers/ # REST контроллеры
 - services/ # Бизнес-логика
 - repository/ # Репозитории Spring Data
 - model/ # Сущности JPA
 - dto/ # Data Transfer Objects
 - mapper/ # MapStruct мапперы
 - config/ # Конфигурации
 - exception/ # Обработка ошибок
 - HotelApplication.java # Точка входа

# Endpoints
   - GET	/api/hotels	Список всех отелей	
   - POST	/api/hotels	Создание отеля (ADMIN){
     
    "name": "hotelName",
    "title": "hotelTitle",
    "city": "city",
    "address": "address",
    "center": int
    }
   - GET	/api/hotels/{id}	Получение отеля по ID	
   - PUT	/api/hotels/{id}	Обновление отеля	
   - DELETE	/api/hotels/{id}	Удаление отеля	
   - PATCH	/api/hotels/{id}/rating	Изменение рейтинга 
   - GET	/api/hotels/search	Поиск отелей с фильтрацией	
   - POST	/api/rooms	Создание комнаты	(ADMIN){
     
    "name": "hotelName",
    "description": "descr",
    "number": "numb",
    "price": int,
    "maxGuests": int,
    "hotelId": int
    }
   - POST	/api/users/register	Регистрация пользователя	{
     
    "username": "name",
    "password": "pass",
    "email": "mail",
    "role": "USER" || "ADMIN"
     }
   - POST	/api/bookings	Создание бронирования{

    "roomId": int,
    "checkInDate": "2026-01-1",
    "checkOutDate": "2026-02-20"
    }
     
   - GET	/api/statistics/export	Экспорт статистики в CSV (ADMIN)
