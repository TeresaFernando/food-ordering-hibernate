package com.foodorder;

import com.foodorder.dao.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.*;

public class FoodOrderApp {
    private static Scanner sc = new Scanner(System.in);
    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        while (true) {
            System.out.println("\n====== 🍽️ Welcome to Food Order Management System ======");
            System.out.println("1️⃣  Place New Order");
            System.out.println("2️⃣  Update Order Status");
            System.out.println("3️⃣  View Orders by Customer");
            System.out.println("4️⃣  🚪 Exit");
            System.out.println("5️⃣  📋 View All Orders");
            System.out.println("6️⃣  ❌ Delete an Order");
            System.out.print("🔸 Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    updateOrderStatus();
                    break;
                case 3:
                    viewOrdersByCustomer();
                    break;
                case 4:
                    factory.close();
                    System.out.println("👋 Exiting. Thank you!");
                    System.exit(0);
                case 5:
                    viewAllOrders();
                    break;
                case 6:
                    deleteOrder();
                    break;
                default:
                    System.out.println("⚠️Invalid choice. Try again.");
            }
        }
    }

    private static void placeOrder() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();
            System.out.print("Enter Restaurant ID: ");
            int restaurantId = sc.nextInt();

            Customer customer = session.get(Customer.class, customerId);
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);

            if (customer == null || restaurant == null) {
                System.out.println("⚠️Invalid Customer or Restaurant ID.");
                return;
            }

            // Fetch and display menu items for the restaurant
            List<MenuItem> availableItems = session.createQuery(
                            "FROM MenuItem WHERE restaurant.id = :rid", MenuItem.class)
                    .setParameter("rid", restaurantId)
                    .list();

            if (availableItems.isEmpty()) {
                System.out.println("❌No items available for this restaurant.");
                return;
            }

            System.out.println("📜 Available Menu Items at " + restaurant.getName() + ":");
            for (MenuItem item : availableItems) {
                System.out.println("  ID: " + item.getId() + " - " + item.getItemName() + " (₹" + item.getPrice() + ")");
            }

            Set<Integer> validItemIds = new HashSet<>();
            for (MenuItem item : availableItems) {
                validItemIds.add(item.getId());
            }

            List<MenuItem> selectedItems = new ArrayList<>();
            while (true) {
                System.out.print("Enter Menu Item ID to add (or 0 to finish): ");
                int itemId = sc.nextInt();
                if (itemId == 0) break;

                if (validItemIds.contains(itemId)) {
                    MenuItem item = session.get(MenuItem.class, itemId);
                    selectedItems.add(item);
                } else {
                    System.out.println("⚠️ Invalid item or does not belong to selected restaurant.");
                }
            }

            if (selectedItems.isEmpty()) {
                System.out.println("❌No valid items selected.");
                return;
            }

            // Create and save the order
            Order order = new Order();
            order.setCustomer(customer);
            order.setRestaurant(restaurant);
            order.setMenuItems(selectedItems);
            order.setOrderDate(new Date());
            order.setStatus("Placed");

            session.save(order);
            transaction.commit();
            System.out.println("✅Order placed successfully!");

        } catch (Exception e) {
            transaction.rollback();
            System.out.println("⚠️Failed to place order.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void updateOrderStatus() {
        System.out.print("Enter Order ID to update: ");
        int id = sc.nextInt();

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Order order = session.get(Order.class, id);
            if (order == null) {
                System.out.println("❌Order not found!");
                return;
            }

            System.out.println("Choose new status:");
            System.out.println("1. Placed");
            System.out.println("2. In Transit");
            System.out.println("3. Delivered");
            System.out.println("4. Cancelled");
            System.out.print("Enter choice (1-4): ");
            int choice = sc.nextInt();

            String status;
            switch (choice) {
                case 1: status = "Placed"; break;
                case 2: status = "In Transit"; break;
                case 3: status = "Delivered"; break;
                case 4: status = "Cancelled"; break;
                default:
                    System.out.println("⚠️Invalid choice. Status not updated.");
                    return;
            }

            order.setStatus(status);
            session.update(order);
            transaction.commit();

            System.out.println("Order status updated to \"" + status + "\"!");

        } catch (Exception e) {
            transaction.rollback();
            System.out.println("❌Failed to update status.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void viewOrdersByCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();

        Session session = factory.openSession();

        try {
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                System.out.println("⚠️Customer not found!");
                return;
            }

            Query<Order> query = session.createQuery("FROM Order WHERE customer.id = :cid", Order.class);
            query.setParameter("cid", id);
            List<Order> orders = query.list();

            if (orders.isEmpty()) {
                System.out.println("❌No orders found for this customer.");
            } else {
                for (Order order : orders) {
                    System.out.println("\nOrder ID: " + order.getId());
                    System.out.println("Date: " + order.getOrderDate());
                    System.out.println("Status: " + order.getStatus());
                    System.out.println("Items:");
                    for (MenuItem item : order.getMenuItems()) {
                        System.out.println(" - " + item.getItemName() + " (₹" + item.getPrice() + ")");
                    }
                    System.out.println("----------------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("❌Error fetching orders.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void viewAllOrders() {
        Session session = factory.openSession();

        try {
            Query<Order> query = session.createQuery("FROM Order", Order.class);
            List<Order> orders = query.list();

            if (orders.isEmpty()) {
                System.out.println("❌No orders found.");
            } else {
                for (Order order : orders) {
                    System.out.println("\nOrder ID: " + order.getId());
                    System.out.println("Customer: " + order.getCustomer().getName());
                    System.out.println("Restaurant: " + order.getRestaurant().getName());
                    System.out.println("Date: " + order.getOrderDate());
                    System.out.println("Status: " + order.getStatus());
                    System.out.println("Items:");
                    for (MenuItem item : order.getMenuItems()) {
                        System.out.println(" - " + item.getItemName() + " (₹" + item.getPrice() + ")");
                    }
                    System.out.println("----------------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("❌Error fetching orders.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void deleteOrder() {
        System.out.print("Enter Order ID to delete: ");
        int id = sc.nextInt();

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Order order = session.get(Order.class, id);
            if (order == null) {
                System.out.println("⚠️Order not found!");
                return;
            }

            session.delete(order);
            transaction.commit();
            System.out.println("✅Order deleted successfully!");

        } catch (Exception e) {
            transaction.rollback();
            System.out.println("❌Failed to delete order.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}

