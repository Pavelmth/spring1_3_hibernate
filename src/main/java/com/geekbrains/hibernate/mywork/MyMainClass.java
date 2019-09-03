package com.geekbrains.hibernate.mywork;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyMainClass {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;

        try {
//            session = factory.getCurrentSession();

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

            session = factory.getCurrentSession();
            session.beginTransaction();
            SimpleItem simpleItem1 = session.get(SimpleItem.class, 1);
            simpleItem1.setName("JavaScript");
            simpleItem1.setCost(100);
            session.getTransaction().commit();


        } finally {
            factory.close();
            session.close();
        }
    }
}
