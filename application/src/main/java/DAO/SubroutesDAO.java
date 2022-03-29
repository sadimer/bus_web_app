package DAO;
import entities.Routes;
import entities.Stations;
import entities.Subroutes;
import entities.Tickets;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class SubroutesDAO implements DAO<Subroutes, Long> {
    // куда можно уехать с моей останоки?
    public List<Subroutes> getByJoin(Stations joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Subroutes as sub inner join sub.depart_st as dep where dep = :dep");
        query.setParameter("dep", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<Subroutes> sub = new ArrayList<Subroutes>();
        for (Object[] row: result) {
            sub.add((Subroutes) row[0]);
        }
        session.close();
        return sub;
    }

    // все варианты подмаршрутов данного маршрута
    public List<Subroutes> getByJoin(Routes joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Subroutes as sub inner join sub.route as route where route = :route");
        query.setParameter("route", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<Subroutes> sub = new ArrayList<Subroutes>();
        for (Object[] row: result) {
            sub.add((Subroutes) row[0]);
        }
        session.close();
        return sub;
    }
}
