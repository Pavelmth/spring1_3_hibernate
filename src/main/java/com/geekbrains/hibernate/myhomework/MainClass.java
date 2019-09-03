package com.geekbrains.hibernate.myhomework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;

        System.out.println("List of commands:\nshow customer purchases - scp\nshow customers who bought certain product - scw\ndelete customer by id- dc\ndelete product by id - dp\nput your command:");

        try {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("dc")) {
                int a = scanner.nextInt();
                session = factory.getCurrentSession();
                session.beginTransaction();
                Customer customer = session.get(Customer.class, a);
                if(customer!=null){
                    session.delete(customer);
                    System.out.println("Customer " + a + " was deleted");
                }
                session.getTransaction().commit();
            }

            if (scanner.nextLine().equals("dp")) {
                int a = scanner.nextInt();
                session = factory.getCurrentSession();
                session.beginTransaction();
                Product product = session.get(Product.class, a);
                if(product!=null){
                    session.delete(product);
                    System.out.println("Product " + a + " was deleted");
                }
                session.getTransaction().commit();
            }

//            session = factory.getCurrentSession();
//            Customer customer = new Customer();
//            customer.setName("Dasha");
//            session.beginTransaction();
//            session.save(customer);
//            session.getTransaction().commit();

//            SimpleItem simpleItem = new SimpleItem();
//            simpleItem.setName("Jave");
//            simpleItem.setCost(110);
//
//            session.beginTransaction();
//            session.save(simpleItem);
//            session.getTransaction().commit();

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            SimpleItem simpleItem1 = session.get(SimpleItem.class, 1);
//            System.out.println(simpleItem1);
//            session.getTransaction().commit();

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            SimpleItem simpleItem1 = session.get(SimpleItem.class, 1);
//            simpleItem1.setName("JavaScript");
//            simpleItem1.setCost(100);
//            session.getTransaction().commit();


        } finally {
            factory.close();
            session.close();
        }
    }
}
