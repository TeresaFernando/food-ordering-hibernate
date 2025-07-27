# 🍽️ Food Ordering System (Hibernate + Java)

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?logo=hibernate)](https://hibernate.org)
[![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-000000?logo=intellijidea&logoColor=white)](https://www.jetbrains.com/idea/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Last Commit](https://img.shields.io/github/last-commit/TeresaFernando/food-ordering-hibernate.svg)](https://github.com/TeresaFernando/food-ordering-hibernate/commits)
[![Status](https://img.shields.io/badge/Project-Active-brightgreen)](https://github.com/TeresaFernando/food-ordering-hibernate)

This is a console-based food ordering application built using **Java**, **Hibernate ORM**, and **MySQL**.  
It allows users to manage customers, restaurants, and menu items, and place orders using a relational database.  
Great for learning Hibernate, object-relational mapping, and Java CRUD operations.

---

## 📌 Features

- 👥 Add Customers, Restaurants, and Menu Items  
- 🛒 Place Orders  
- 🔍 View Orders by Customer  
- 📋 View All Orders  
- 🔄 Update Order Status  
- ❌ Delete Orders    

---

## 🛠️ Technologies Used

- Java  
- Hibernate ORM  
- MySQL  
- Maven  
- IntelliJ IDEA  

---

## 🗂️ Project Structure
```text
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/foodorder/
│   │   │       ├── AppInsertData.java
│   │   │       ├── FoodOrderApp.java
│   │   │       └── dao/
│   │   │           ├── Customer.java
│   │   │           ├── Restaurant.java
│   │   │           ├── MenuItem.java
│   │   │           └── Order.java
│   │   └── resources/
│   │       └── hibernate.cfg.xml
│   └── test/
│       └── java/
│           └── AppTest.java
├── pom.xml
├── README.md
├── LICENSE
└── screenshots/
    ├── screenshot1.png
    └── screenshot2.png
```

---

## ▶️ How to Run

1. Clone this repository  
2. Open the project in IntelliJ IDEA  
3. Create a MySQL database named `food_order_db`  
4. Update `hibernate.cfg.xml` with your MySQL username, password, and DB details  
5. Run `AppInsertData.java` to populate initial sample data  
6. Run `FoodOrderApp.java` to use the console-based ordering system 

---

## 📸 Screenshots

### 🧭 Main Menu  
![Main Menu](screenshots/screenshot1.png)

### ✅ Placing an Order  
![Place Order](screenshots/screenshot2.png)

---

## 💻 Sample Console Output

```text
====== 🍽️ Welcome to Food Order Management System ======
1️⃣  Place New Order
2️⃣  Update Order Status
3️⃣  View Orders by Customer
4️⃣  🚪 Exit
5️⃣  📋 View All Orders
6️⃣  ❌ Delete an Order
🔸 Enter your choice: 
```
---

## 🙋‍♀️ Author

**Teresa Fernando**  
*IT Enthusiast | Java Developer | Passionate about learning*

---

## 📜 License

This project is for educational purposes and open to learning and improvement.
