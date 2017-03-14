package com.hysw.leinuo.entity;

import com.hysw.leinuo.HibernateShardUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by leinuo on 17-2-16.
 */
public class Main {
    public void list() {
        SessionFactory sessionFactory = HibernateShardUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RecorderEntity> userList = session.createQuery("from RecorderEntity").list();
        Iterator<RecorderEntity> iterator = userList.iterator();
        while (iterator.hasNext()) {
            RecorderEntity recorderEntity = (RecorderEntity) iterator.next();
            System.out.println(recorderEntity);
        }
        session.close();
    }

    public void input() {
        SessionFactory sessionFactory = HibernateShardUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            RecorderEntity recorderEntity = new RecorderEntity(101l,"21.3",new Date(),"001");
            session.save(recorderEntity);

            recorderEntity = new RecorderEntity(102l,"22.3",new Date(),"002");
            session.save(recorderEntity);

            recorderEntity = new RecorderEntity(103l,"23.3",new Date(),"003");
            session.save(recorderEntity);

            recorderEntity = new RecorderEntity(104l,"24.3",new Date(),"004");
            session.save(recorderEntity);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        session.close();
    }


    private void delete(){
        SessionFactory sessionFactory = HibernateShardUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        System.out.println("\n===Delete Contacts===");
        try{
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            List contacts = session.createQuery("from RecorderEntity").list();
            Iterator it = contacts.iterator();
            while(it.hasNext()){
                session.delete(it.next());
            }
            transaction.commit();
        }catch(Exception e){
            if(transaction!=null) transaction.rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            if(session!=null) session.close();
        }
    }

    public static void main(String[] args) {
        Main obj = new Main();
        obj.list();
        obj.input();
        obj.list();
        obj.delete();
    }
}
