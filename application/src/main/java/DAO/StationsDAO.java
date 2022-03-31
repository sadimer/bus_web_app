package DAO;
import entities.Stations;
import entities.StationsOfRoute;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class StationsDAO implements DAO<Stations, Long> {
    public List<Stations> getByJoin(StationsOfRoute joinClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Stations as st inner join StationsOfRoute as strt on st = strt.st where strt = :strt");
        query.setParameter("strt", joinClass);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.list();
        List<Stations> sub = new ArrayList<Stations>();
        for (Object[] row: result) {
            sub.add((Stations) row[0]);
        }
        session.close();
        return sub;
    }
}
