# 📚 Online Bookstore API

Welcome to the Online Bookstore API! This API serves as the core infrastructure for an online bookstore, delivering crucial functionalities for book management, user authentication, and order processing. Born from the necessity for a scalable and efficient solution, this project aims to address the challenges of operating an online book retail platform.

## 🌟 Inspiration and Problem Solved

The concept for this project arose from the increasing demand for online book shopping. Unlike traditional bookstores, which are limited by their physical reach and inventory constraints, online bookstores can provide an extensive selection of books and a streamlined purchasing experience. This API tackles issues related to inventory management, secure user authentication, and order processing, offering a solid framework for creating an online bookstore.

## 🛠️ Technologies and Tools Used

- **Spring Boot**: For building the application framework.
- **Spring Security**: To handle authentication and authorization.
- **Spring Data JPA**: For database interactions using JPA.
- **Spring Web**: For building web-based applications with Spring MVC.
- **Swagger**: For API documentation and testing.
- **Hibernate**: As the ORM tool.
- **MySQL**: For the relational database.
- **Liquibase**: For database schema versioning and management.
- **Docker**: For containerizing the application.
- **JUnit**: For unit testing.
- **Mockito**: For mocking in tests.
- **Maven**: For project management and dependency management.

## 📝 Swagger Documentation

Swagger is integrated into the project to provide comprehensive API documentation and facilitate testing of endpoints. With Swagger, you can visualize and interact with the API's resources without having any of the implementation logic in place.

To access the Swagger UI and explore the API documentation:

1. Once the application is running, navigate to `http://localhost:8080/swagger-ui.html` in your web browser.

2. You will see the Swagger UI interface, where you can view all available endpoints, their descriptions, request parameters, and response schemas.

3. Use the interactive features of Swagger UI to make requests directly from the browser and observe the responses.

## 🎨 Features and Functionality

### Authentication

- **Register user**: `POST /api/auth/register`
- **Login user**: `POST /api/auth/login`

### Books

- **List all books**: `GET /api/books`
- **Get book details**: `GET /api/books/{id}`
- **Search book by criteria**: `GET /api/books/search?author= &isbn= &title=`
- **Add new book**: `POST /api/books` (Admin only)
- **Update book**: `PUT /api/books/{id}` (Admin only)
- **Delete book**: `DELETE /api/books/{id}` (Admin only)

### Categories

- **List all categories**: `GET /api/categories`
- **Get category by id**: `GET /api/categories/{id}`
- **Get all books by category**: `GET /api/categories/{id}/books`
- **Add new category**: `POST /api/categories` (Admin only)
- **Update category**: `PUT /api/categories/{id}` (Admin only)
- **Delete category**: `DELETE /api/categories/{id}` (Admin only)

### Shopping Cart

- **Get user's cart**: `GET /api/cart`
- **Add book to cart**: `POST /api/cart`
- **Remove item from cart by id**: `DELETE /api/cart/{itemId}`
- **Update book quantity in cart**: `PUT /api/cart/{itemId}`
- **Clear user's cart**: `PUT /api/cart/clear`

### Orders

- **Get order history**: `GET /api/orders`
- **Place order**: `POST /api/orders`
- **Get all items from order**: `GET /api/orders/{id}/items`
- **Get specific item from order**: `GET /api/orders/{orderId}/items/{itemId}`
- **Update order status**: `PATCH /api/orders/{id}` (Admin only)

## 📷 Visual Overview

[![Watch on Loom](https://img.shields.io/badge/Watch%20on-Loom-00a4d9)](https://www.loom.com/share/c82e8bf7ab634c2fbcf7852bd3fe5142?sid=11b04c4b-3e88-42d7-874d-6c122676626d)

## 🚀 Getting Started

### Prerequisites

- **Java 21+**
- **Maven 4+**
- **MySQL 8+**
- **Docker**

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/reamfox/online-book-store.git
    cd online-book-store
    ```

2. **Set up MySQL:**

   Create a new MySQL database and note the database URL, username, and password.


3. **Configure environment variables:**

   Create a `application.properties` file in the `src/main/resources` directory and add the following:

    ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    spring.jpa.hibernate.ddl-auto=update
   server.servlet.context-path=/api
   
    jwt.expiration=token_expiration_time
    jwt.secret=your_secret_key
   
    ```

4. **Install dependencies and build the project:**

    ```bash
    mvn clean install
    ```

5. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

   The server will start on `http://localhost:8080`.

### Using Docker

1. **Build the Docker image:**

    ```bash
    docker build -t books-service .
    ```

2. **Run the Docker container:**

    ```bash
    docker run -d -p 8080:8080 --name books-service books-service
    ```
### Using Docker Compose

1. **Set up your environment variables:**

   Create a `.env` file in the root directory and add the following variables:

    ```env
    MYSQLDB_USER=your_db_username
    MYSQLDB_PASSWORD=your_db_password
    MYSQLDB_DATABASE=your_db_name
    MYSQLDB_ROOT_PASSWORD=your_root_password
    MYSQLDB_LOCAL_PORT=3306
    MYSQLDB_DOCKER_PORT=3306
    SPRING_LOCAL_PORT=8080
    SPRING_DOCKER_PORT=8080
    DEBUG_PORT=5005
    ```

2. **Create a `docker-compose.yml` file in the root directory:**

    ```yaml
    version: "3.8"

    services:
      mysqldb:
        platform: linux/arm64
        image: mysql
        restart: unless-stopped
        env_file: ./.env
        environment:
          - MYSQL_USER=$MYSQLDB_USER
          - MYSQL_PASSWORD=$MYSQLDB_PASSWORD
          - MYSQL_DATABASE=$MYSQLDB_DATABASE
          - MYSQL_ROOT_PASSWORD=$MYSQLDB_ROOT_PASSWORD
        ports:
          - $MYSQLDB_LOCAL_PORT:$MYSQLDB_DOCKER_PORT
        healthcheck:
          test: [ "CMD-SHELL", "mysqladmin ping -h mysqldb -u $MYSQLDB_USER -p$MYSQLDB_PASSWORD" ]
          interval: 60s
          timeout: 60s
          retries: 3

      app:
        depends_on:
          mysqldb:
            condition: service_healthy
        restart: on-failure
        image: books-service
        build: .
        env_file: ./.env
        ports:
          - $SPRING_LOCAL_PORT:$SPRING_DOCKER_PORT
          - $DEBUG_PORT:$DEBUG_PORT
        environment:
          SPRING_APPLICATION_JSON: '{
            "spring.datasource.url"  : "jdbc:mysql://mysqldb:$MYSQLDB_DOCKER_PORT/$MYSQLDB_DATABASE",
            "spring.datasource.username" : "$MYSQLDB_USER",
            "spring.datasource.password" : "$MYSQLDB_PASSWORD",
            "spring.jpa.properties.hibernate.dialect" : "org.hibernate.dialect.MySQLDialect"
          }'
          JAVA_TOOL_OPTIONS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    ```

3. **Run the Docker Compose setup:**

    ```bash
    docker-compose up -d
    ```

   This command will start both the MySQL and the application containers, with the application available at `http://localhost:8080`.

## 🧪 Running Tests

To run tests, use the following command:

```bash
mvn test
```

### Testing with JUnit and Mockito

The project uses JUnit for unit testing and Mockito for mocking dependencies. This ensures that the application logic is tested in isolation, making the tests more reliable and easier to maintain.
## 📄 Challenges and Solutions

### Challenge 1: Securing the API

**Solution:** Implemented Spring Security to manage authentication and authorization, ensuring that sensitive endpoints are protected.

### Challenge 2: Database Management

**Solution:** Utilized Spring Data JPA and Hibernate for efficient database interactions and ORM capabilities.

### Challenge 3: Comprehensive API Documentation

**Solution:** Integrated Swagger to provide detailed API documentation and facilitate easy testing of endpoints.
## 📬 Postman Collection

[![Run in Postman](https://run.pstmn.io/button.svg)](https://elements.getpostman.com/redirect?entityId=33948143-d2ac0092-0285-4de1-a6cf-cef4cd01c19a&entityType=collection)

Here you can fork  collection of requests for API in Postman.
