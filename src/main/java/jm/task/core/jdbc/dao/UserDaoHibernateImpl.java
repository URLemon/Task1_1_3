package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT NOT NULL, " +
            "name VARCHAR(30) NOT NULL, " +
            "lastname VARCHAR(30) NOT NULL," +
            "age INT NOT NULL," +
            "PRIMARY KEY(id)" +
            ");";

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_TABLE_USERS).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            /*NOP*/
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE users;";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception exception) {
            /*NOP*/
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete users where id = :param");
            query.setParameter("param", String.valueOf(id));
            int n = query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            /*NOP*/
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Query q = session.createQuery(cq);
            List<User> listAllUsers = q.getResultList();

            return listAllUsers;
        } catch (Exception ex) {
            /*NOP*/
        }

        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
