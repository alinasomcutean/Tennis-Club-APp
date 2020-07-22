package layer_data_access.repo;

import config.HibernateConfig;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepo {

    private final static Session session = HibernateConfig.Instance();

    public static User findUserByMail(String mail) {
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("from User u where u.mail=:mail", User.class);
        query.setParameter("mail", mail);
        User user = query.uniqueResult();
        transaction.commit();

        return user;
    }

    public static User findUserById(int id){
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("from User u where u.id=:id", User.class);
        query.setParameter("id", id);
        User user = query.uniqueResult();
        transaction.commit();

        return user;
    }

    public static List<User> findAllUsers(){
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("from User", User.class);
        List<User> list = query.getResultList();
        transaction.commit();

        return list;
    }

    public static void deleteUser(int id){
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("delete from User u where u.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
    }

    public static void updateUser(int id, String mail, String name, String password){
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("update User u set u.mail=:mail, u.name=:name, u.password=:password where u.id=:id");
        query.setParameter("id", id);
        query.setParameter("mail", mail);
        query.setParameter("name", name);
        query.setParameter("password", password);
        query.executeUpdate();
        transaction.commit();
    }
}
