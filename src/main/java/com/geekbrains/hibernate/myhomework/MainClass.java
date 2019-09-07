package com.geekbrains.hibernate.myhomework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Урок 3
 *  1. В базе данных (MySQL) необходимо реализовать возможность хранить информацию о покупателях (id, имя) и товарах (id, название, стоимость). У каждого покупателя свой набор купленных товаров.
 *  Задача: написать тестовое консольное приложение, которое позволит посмотреть, какие товары покупал клиент, какие клиенты купили определенный товар, и предоставит возможность удалять из базы товары/покупателей.
 *  2. * Добавить детализацию по паре «покупатель — товар»: сколько стоил товар в момент покупки клиентом.
 *
 * Урок 4
 *  1. Создать сущность «товар» (id, название, стоимость) и соответствующую таблицу в БД. Заполнить таблицу тестовыми данными (20 записей).
 *  2. Сделать страницу, в которую будут выведены эти записи.
 *  3. С помощью GET-запроса указывать фильтрацию по:
 *  a. только минимальной,
 *  b. только максимальной,
 *  c. или минимальной и максимальной цене.
 *  4. * Добавить постраничное отображение (по 5 записей на странице).
*/

/*
CREATE SCHEMA hiber;
SET search_path TO hiber;
DROP TABLE if EXISTS customers; CREATE TABLE customers (id serial PRIMARY KEY, name VARCHAR(255));
DROP TABLE if EXISTS products; CREATE TABLE products (id serial PRIMARY KEY, name VARCHAR(255), cost int, date DATE NOT NULL DEFAULT CURRENT_DATE);
DROP TABLE if EXISTS baskets; CREATE TABLE baskets (id_customer RESTRICTED
*/
public class MainClass {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;

        try {

            Scanner scanner = new Scanner(System.in);

            String command = "";

            while (!command.equals("q")) {
                listOfCommands();
                command = scanner.nextLine();

                //add customer
                if (command.equals("ac")) {
                    System.out.println("Input name");
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    String name = scanner.nextLine();
                    Customer customer = new Customer();
                    customer.setName(name);
                    if(name!= null){
                        session.save(customer);
                        System.out.println("Customer " + name + " was added");
                    }
                    session.getTransaction().commit();
                }

                //add product
                if (command.equals("ap")) {
                    System.out.println("Input name");
                    String name = scanner.nextLine();
                    System.out.println("Input cost");
                    Integer cost = scanner.nextInt();
                    Product product = new Product();
                    product.setName(name);
                    product.setCost(cost);
                    if(name!= null){
                        session = factory.getCurrentSession();
                        session.beginTransaction();
                        session.save(product);
                        System.out.println("Product " + name + " and cost " + cost + " was added");
                    }
                    session.getTransaction().commit();
                }

                //show list of customers
                if (command.equals("sc")) {
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    Query query = session.createNativeQuery("SELECT * FROM customers;", Customer.class);
                    List<Customer> customers = query.getResultList();
                    for (Customer o:
                            customers) {
                        System.out.println(o.getName());
                    }
                    session.getTransaction().commit();
                }

                //show list of products
                if (command.equals("sp")) {
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    Query query = session.createNativeQuery("SELECT * FROM products;", Product.class);
                    List<Product> products = query.getResultList();

                    for (Product o:
                            products) {
                        System.out.println(o.getName());
                    }
                    session.getTransaction().commit();
                }

                //delete customer
                if (command.equals("dc")) {
                    String name = scanner.nextLine();
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    Customer customer = session.createQuery("SELECT c FROM Customer c WHERE c.name = :name", Customer.class)
                            .setParameter("name", name)
                            .getSingleResult();
                    if(customer!=null){
                        session.delete(customer);
                        System.out.println("Customer " + name + " was deleted");
                    }
                    session.getTransaction().commit();
                }

                //delete product
                if (command.equals("dp")) {
                    String name = scanner.nextLine();
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    Product product = session.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                            .setParameter("name", name)
                            .getSingleResult();
                    if(product!=null){
                        session.delete(product);
                        System.out.println("Customer " + name + " was deleted");
                    }
                    session.getTransaction().commit();
                }

                //show customer purchases
                if (command.equals("scp")) {
                    String name = scanner.nextLine();
                    List<Product> products = new ArrayList<>();
                    session = factory.getCurrentSession();
                    session.beginTransaction();
                    Customer customer = session.createQuery("SELECT c FROM Customer c WHERE c.name = :name", Customer.class)
                            .setParameter("name", name)
                            .getSingleResult();
                    if(customer!=null){
                        for (Basket o :
                             customer.getBasketList()) {
                            products.add(session.createQuery("SELECT p FROM Product p WHERE p.customer_id = :id", Product.class)
                                    .setParameter("id", o.getProductId())
                                    .getSingleResult());
                        }
                    }
                    session.getTransaction().commit();
                }

                //show customers who bought certain product
                if (command.equals("scbp")) {

                }
            }

            factory.close();
            session.close();

        } finally {
            factory.close();
            session.close();
        }
    }

    public static void listOfCommands() {
        System.out.println(
                "List of commands:\n" +
                "quit - q\n" +
                "add customer into table \"customers\" - ac\n" +
                "add product into table \"products\" - ap\n" +
                "show list of customers - sc\n" +
                "show list of products - sp\n" +
                "delete customer - dc\n" +
                "delete product - dp\n" +
                "show customer purchases - scp\n" +
                "show customers who bought certain product - scbp\n" +
                "input command"
        );
    }
}
