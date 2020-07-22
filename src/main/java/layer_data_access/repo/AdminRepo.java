package layer_data_access.repo;

import config.HibernateConfig;
import model.Admin;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AdminRepo {

    private final static Session session = HibernateConfig.Instance();

    public static Admin findAdminByMail(String mail) {

        Transaction transaction = session.beginTransaction();
        Query<Admin> query = session.createQuery("from Admin a where a.mail=:mail", Admin.class);
        query.setParameter("mail", mail);
        Admin admin = query.uniqueResult();
        transaction.commit();

        return admin;
    }
}
