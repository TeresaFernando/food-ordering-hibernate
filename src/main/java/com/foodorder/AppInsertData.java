package com.foodorder;

import com.foodorder.dao.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class AppInsertData {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // --- Restaurant 1 ---
            Restaurant r1 = new Restaurant();
            r1.setName("Tasty Treats");
            r1.setLocation("Mumbai");
            r1.setCuisine("South Indian");

            r1.addMenuItem(createItem("Masala Dosa", 80, "Breakfast"));
            r1.addMenuItem(createItem("Idli Vada", 60, "Breakfast"));
            r1.addMenuItem(createItem("Upma", 50, "Breakfast"));
            r1.addMenuItem(createItem("Lemon Rice", 70, "Lunch"));
            r1.addMenuItem(createItem("Filter Coffee", 30, "Beverage"));

            // --- Restaurant 2 ---
            Restaurant r2 = new Restaurant();
            r2.setName("Punjabi Junction");
            r2.setLocation("Delhi");
            r2.setCuisine("North Indian");

            r2.addMenuItem(createItem("Butter Chicken", 250, "Main Course"));
            r2.addMenuItem(createItem("Paneer Tikka", 180, "Starter"));
            r2.addMenuItem(createItem("Naan", 25, "Bread"));
            r2.addMenuItem(createItem("Lassi", 60, "Beverage"));
            r2.addMenuItem(createItem("Rajma Chawal", 120, "Lunch"));

            // --- Restaurant 3 ---
            Restaurant r3 = new Restaurant();
            r3.setName("Pizza Palace");
            r3.setLocation("Bangalore");
            r3.setCuisine("Italian");

            r3.addMenuItem(createItem("Margherita Pizza", 200, "Pizza"));
            r3.addMenuItem(createItem("Garlic Bread", 90, "Starter"));
            r3.addMenuItem(createItem("Pasta Alfredo", 220, "Main Course"));
            r3.addMenuItem(createItem("Tiramisu", 150, "Dessert"));
            r3.addMenuItem(createItem("Coke", 40, "Beverage"));

            // --- Customers ---
            Customer c1 = createCustomer("Rahul Sharma", "rahul@example.com", "Mumbai", r1);
            Customer c2 = createCustomer("Simran Kaur", "simran@example.com", "Delhi", r2);
            Customer c3 = createCustomer("Arjun Mehta", "arjun@example.com", "Bangalore", r3);
            Customer c4 = createCustomer("Neha Patil", "neha@example.com", "Pune", r1);
            Customer c5 = createCustomer("Ravi Verma", "ravi@example.com", "Chennai", r2);

            // --- Save everything ---
            session.save(r1);
            session.save(r2);
            session.save(r3);

            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.save(c5);

            transaction.commit();
            System.out.println("✅Data inserted successfully!");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("❌Transaction failed.");
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }

    // Utility method to create a MenuItem
    private static MenuItem createItem(String name, double price, String category) {
        MenuItem item = new MenuItem();
        item.setItemName(name);
        item.setPrice(price);
        item.setCategory(category);
        return item;
    }

    // Utility method to create a Customer
    private static Customer createCustomer(String name, String email, String address, Restaurant restaurant) {
        Customer c = new Customer();
        c.setName(name);
        c.setEmail(email);
        c.setAddress(address);
        c.setRestaurant(restaurant);
        return c;
    }
}
