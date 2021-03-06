package DAO;
import util.HibernateUtil;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public interface DAO<E, K> {
    default E update(E entity) {
        if (Objects.isNull(entity)) {
            return entity;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    default boolean delete(E entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
        return true;
    }

    default boolean create(E entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    default List<E> getAll(Class persistentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from " + persistentClass.getName());

        @SuppressWarnings("unchecked")
        List<E> result = query.list();
        session.close();
        return result;
    }

    default E getEntityById(Long id, Class persistentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        E entity = (E) session.get(persistentClass, id);
        session.close();
        return entity;
    }

    default List<E> filter(Map<String,List> filters, Class persistentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        filters.entrySet().forEach(entry -> {
            String filterName = entry.getKey();
            List parameters = entry.getValue();
            Filter enableFilter = session.enableFilter(filterName);
            Set<String> paramNames = enableFilter.getFilterDefinition().getParameterNames();
            AtomicInteger i = new AtomicInteger();
            paramNames.forEach(name ->
                    enableFilter.setParameter(name, parameters.get(i.getAndIncrement()))
            );
        });

        Query query = session.createQuery("from " + persistentClass.getName());

        @SuppressWarnings("unchecked")
        List<E> result = query.list();
        session.close();
        return result;
    }
}