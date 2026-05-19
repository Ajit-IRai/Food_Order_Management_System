# 🍔 Food Management System

A Spring Boot based Food Management System application that provides REST APIs for managing restaurants, customers, menu items, orders, order items, and payments.

This project is built using Java, Spring Boot, Spring Data JPA, PostgreSQL, and Maven.

---

# 🚀 Features

* Customer Management
* Restaurant Management
* Menu Item Management
* Order Management
* Order Item Management
* Payment Management
* RESTful APIs
* PostgreSQL Database Integration
* Layered Architecture
* Exception Handling
* DTO Response Structure

---

# 🛠️ Tech Stack

* Java 24
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* REST API

---

# 📁 Project Structure

```bash
src/main/java/foodOrderSystem
│
├── controller
├── dao
├── dto
├── entity
├── exception
├── repository
├── service
└── FoodManagementSystemApplication.java
```

---

# ⚙️ Dependencies Used

* spring-boot-starter-data-jpa
* spring-boot-starter-webmvc
* spring-boot-starter-validation
* PostgreSQL Driver
* Spring Boot DevTools

---

# 🗄️ Database Configuration

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/food_management
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

# ▶️ Run the Project

## Clone Repository

```bash
git clone https://github.com/your-username/foodManagementSystem.git
```

## Navigate to Project

```bash
cd foodManagementSystem
```

## Run Using Maven

```bash
mvn spring-boot:run
```

---

# 📌 API Endpoints

## 👤 Customer APIs

| Method | Endpoint              | Description        |
| ------ | --------------------- | ------------------ |
| POST   | `/customer`           | Save customer      |
| GET    | `/customer/customers` | Get all customers  |
| GET    | `/customer/{id}`      | Get customer by ID |
| PUT    | `/customer/api`       | Update customer    |
| DELETE | `/customer/{id}`      | Delete customer    |

---

## 🍽️ Restaurant APIs

| Method | Endpoint                | Description          |
| ------ | ----------------------- | -------------------- |
| PUT    | `/resturant`            | Save restaurant      |
| GET    | `/resturant/resturants` | Get all restaurants  |
| GET    | `/resturant/{id}`       | Get restaurant by ID |
| POST   | `/resturant/api`        | Update restaurant    |
| DELETE | `/resturant/{id}`       | Delete restaurant    |

---

## 📋 Menu Item APIs

| Method | Endpoint                | Description                  |
| ------ | ----------------------- | ---------------------------- |
| POST   | `/menu`                 | Add menu item                |
| GET    | `/menu`                 | Get all menu items           |
| GET    | `/menu/{id}`            | Get menu item by ID          |
| PATCH  | `/menu/{id}/{price}`    | Update item price            |
| DELETE | `/menu/{id}`            | Delete menu item             |
| GET    | `/menu/greater/{price}` | Get items greater than price |
| GET    | `/menu/name/{name}`     | Search menu item by name     |

---

## 🛒 Order APIs

| Method | Endpoint                        | Description            |
| ------ | ------------------------------- | ---------------------- |
| POST   | `/orders`                       | Place order            |
| GET    | `/orders`                       | Get all orders         |
| GET    | `/orders/{id}`                  | Get order by ID        |
| GET    | `/orders/customer/{customerId}` | Get orders by customer |
| PATCH  | `/orders/{id}/status/{status}`  | Update order status    |
| PATCH  | `/orders/{id}/cancel`           | Cancel order           |
| GET    | `/orders/status/{status}`       | Get orders by status   |

---

## 🧾 Order Item APIs

| Method | Endpoint                     | Description        |
| ------ | ---------------------------- | ------------------ |
| POST   | `/orderItem/{orderId}`       | Add item to order  |
| PATCH  | `/orderItem/{id}/{quantity}` | Update quantity    |
| DELETE | `/orderItem/{id}`            | Remove order item  |
| GET    | `/orderItem/order/{orderId}` | Get items by order |

---

## 💳 Payment APIs

| Method | Endpoint                        | Description            |
| ------ | ------------------------------- | ---------------------- |
| GET    | `/payment/{id}`                 | Get payment by ID      |
| GET    | `/payment/status/{status}`      | Get payments by status |
| GET    | `/payment/method/{method}`      | Get payments by method |
| PATCH  | `/payment/{id}/status/{status}` | Update payment status  |

---

# 📦 Build Project

```bash
mvn clean install
```

---

# 🧪 Testing

Run tests using:

```bash
mvn test
```

---

# 👨‍💻 Author

Ajit Rai

---

# ⭐ Future Improvements

* JWT Authentication
* Swagger Documentation
* Role-Based Authorization
* Docker Support
* Payment Gateway Integration
* Frontend Integration
* Unit & Integration Testing

---

# 📜 License

This project is developed for learning and educational purposes.
