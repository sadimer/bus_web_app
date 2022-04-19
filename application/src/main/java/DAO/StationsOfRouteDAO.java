package DAO;
import entities.Routes;
import entities.Stations;
import entities.StationsOfRoute;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StationsOfRouteDAO implements DAO<StationsOfRoute, Long> {
    public List<StationsOfRoute> getByJoin(Routes joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from StationsOfRoute as strt inner join strt.route as route where route = :route");
        query.setParameter("route", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<StationsOfRoute> strt = new ArrayList<StationsOfRoute>();
        for (Object[] row: result) {
            strt.add((StationsOfRoute) row[0]);
        }
        session.close();
        return strt;
    }

    public List<StationsOfRoute> getByJoin(Stations joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from StationsOfRoute as strt inner join strt.st as station where station = :station");
        query.setParameter("station", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<StationsOfRoute> strt = new ArrayList<StationsOfRoute>();
        for (Object[] row: result) {
            strt.add((StationsOfRoute) row[0]);
        }
        session.close();
        return strt;
    }
}

