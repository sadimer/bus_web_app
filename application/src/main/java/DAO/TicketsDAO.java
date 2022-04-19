package DAO;
import entities.Subroutes;
import entities.Tickets;
import entities.Users;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TicketsDAO implements DAO<Tickets, Long> {
    public List<Tickets> getByJoin(Users joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Tickets as tickets inner join tickets.user as user where user = :user");
        query.setParameter("user", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<Tickets> tickets = new ArrayList<Tickets>();
        for (Object[] row: result) {
            tickets.add((Tickets) row[0]);
        }
        session.close();
        return tickets;
    }

    public List<Tickets> getByJoin(Subroutes joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Tickets as tickets inner join tickets.sub as sub where sub = :sub");
        query.setParameter("sub", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<Tickets> tickets = new ArrayList<Tickets>();
        for (Object[] row: result) {
            tickets.add((Tickets) row[0]);
        }
        session.close();
        return tickets;
    }
}

