package com.geekbrains.hibernate.mywork;

import com.geekbrains.hibernate.basic.Author;
import com.geekbrains.hibernate.basic.Book;
import com.geekbrains.hibernate.basic.Catalog;
import com.geekbrains.hibernate.basic.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyMainClass {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Reader.class)
                .addAnnotatedClass(Catalog.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Reader reader = session.get(Reader.class, 1);
            Book book = session.get(Book.class, 2);
            book = session.createQuery("from Book b where b.id = :id", Book.class).setParameter("id", 1).getSingleResult();

            reader.getBooks().clear();
            session.getTransaction().commit();


            session = factory.getCurrentSession();
            session.beginTransaction();
            Catalog catalog2 = session.find(Catalog.class, 2L);


            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();
            catalog2 = session.find(Catalog.class, 2L);
            session.getTransaction().commit();

            System.out.println(catalog2);
        } finally {
            factory.close();
            session.close();
        }
    }
}
