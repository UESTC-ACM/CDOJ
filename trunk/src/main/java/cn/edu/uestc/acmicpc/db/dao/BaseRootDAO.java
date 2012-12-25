package cn.edu.uestc.acmicpc.db.dao;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base DAO implementation
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class BaseRootDAO {

    public BaseRootDAO() {
    }

    public BaseRootDAO(Session session) {
        setSession(session);
    }

    protected static Map<String, SessionFactory> sessionFactoryMap;
    protected SessionFactory sessionFactory;

    protected Session session;
    protected final static ThreadLocal<Session> currentSession = new ThreadLocal<Session>();

    /**
     * Return a new Session object that must be closed when the work has been
     * completed.
     *
     * @return the active Session
     */
    public Session getSession() {
        return getSession(getConfigurationFileName());
    }

    /**
     * Return a new Session object that must be closed when the work has been
     * completed.
     *
     * @param configFile the config file must match the meta attribute "config-file" in
     *                   the hibernate mapping file
     * @return the active Session
     */
    protected Session getSession(String configFile) {
        if (null != session && session.isOpen())
            return session;
        else if (null != sessionFactory) {
            Session s = currentSession.get();
            if (null == s || !s.isOpen()) {
                s = sessionFactory.openSession();
                currentSession.set(s);
            }
            return s;
        } else {
            Session s = currentSession.get();
            if (null == s || !s.isOpen()) {
                s = getSessionFactory(configFile).openSession();
                currentSession.set(s);
            }
            return s;
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Configure the session factory by reading hibernate config file
     */
    @SuppressWarnings("unused")
    public static void initialize() {
        initialize(null);
    }

    /**
     * Configure the session factory by reading hibernate config file
     *
     * @param configFileName the name of the configuration file
     */
    public static void initialize(String configFileName) {
        initialize(configFileName, getNewConfiguration(null));
    }

    /**
     * get session factory <STRONG>after</STRONG> the configuration object being configured
     *
     * @param configuration app configuration object
     * @return session factory to deal with datbase
     */
    private static SessionFactory buildSessionFactory(Configuration configuration) {
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void initialize(String configFileName, Configuration configuration) {
        if (null != sessionFactoryMap && null != sessionFactoryMap.get(configFileName)) {
        } else {
            if (null == configFileName) {
                configuration.configure();
                setSessionFactory(null, buildSessionFactory(configuration));
            } else {
                configuration.configure(configFileName);
                setSessionFactory(configFileName, buildSessionFactory(configuration));
            }
        }
    }

    /**
     * Set the session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Set the session factory
     */
    protected static void setSessionFactory(String configFileName, SessionFactory sf) {
        if (null == configFileName)
            configFileName = "";
        if (null == sessionFactoryMap)
            sessionFactoryMap = new HashMap<String, SessionFactory>();
        sessionFactoryMap.put(configFileName, sf);
    }

    /**
     * Return the SessionFactory that is to be used by these DAOs. Change this
     * and implement your own strategy if you, for example, want to pull the
     * SessionFactory from the JNDI tree.
     */
    public SessionFactory getSessionFactory() {
        if (null != sessionFactory)
            return sessionFactory;
        else
            return getSessionFactory(getConfigurationFileName());
    }

    public SessionFactory getSessionFactory(String configFileName) {
        if (null == configFileName)
            configFileName = "";
        if (null == sessionFactoryMap)
            initialize(configFileName);
        SessionFactory result = sessionFactoryMap.get(configFileName);
        if (null == result)
            throw new RuntimeException("The session factory for '" + configFileName + "' has not been initialized (or an error occured during initialization)");
        else
            return result;
    }

    /**
     * Close all sessions for the current thread
     */
    public static void closeCurrentSession() {
        Session s = currentSession.get();
        if (null != s) {
            if (s.isOpen())
                s.close();
            currentSession.set(null);
        }
    }

    /**
     * Close the session
     */
    public void closeSession(Session session) {
        if (null != session)
            session.close();
    }

    /**
     * Begin the transaction related to the session
     */
    public Transaction beginTransaction(Session s) {
        return s.beginTransaction();
    }

    /**
     * Commit the given transaction
     */
    public void commitTransaction(Transaction t) {
        t.commit();
    }

    /**
     * Return a new Configuration to use. This is not a mistake and is meant to
     * be overridden in the RootDAO if you want to do something different. The
     * file name is passed in so you have that to access. The config file is
     * read in the initialize method.
     */
    public static Configuration getNewConfiguration(String configFileName) {
        return new Configuration();
    }

    /**
     * Return the name of the configuration file to be used with this DAO or
     * null if default
     */
    public String getConfigurationFileName() {
        return null;
    }

    /**
     * Return the specific Object class that will be used for class-specific
     * implementation of this DAO.
     *
     * @return the reference Class
     */
    protected abstract Class getReferenceClass();

    /**
     * Used by the base DAO classes but here for your modification Get object
     * matching the given key and return it.
     */
    protected Object get(Class refClass, Serializable key) {
        Session s = null;
        try {
            s = getSession();
            return get(refClass, key, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Used by the base DAO classes but here for your modification Get object
     * matching the given key and return it.
     */
    protected Object get(Class refClass, Serializable key, Session s) {
        return s.get(refClass, key);
    }

    /**
     * Used by the base DAO classes but here for your modification Load object
     * matching the given key and return it.
     */
    protected Object load(Class refClass, Serializable key) {
        Session s = null;
        try {
            s = getSession();
            return load(refClass, key, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Used by the base DAO classes but here for your modification Load object
     * matching the given key and return it.
     */
    protected Object load(Class refClass, Serializable key, Session s) {
        return s.load(refClass, key);
    }

    /**
     * Return all objects related to the implementation of this DAO with no
     * filter.
     */
    public java.util.List findAll() {
        Session s = null;
        try {
            s = getSession();
            return findAll(s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Return all objects related to the implementation of this DAO with no
     * filter. Use the session given.
     *
     * @param s the Session
     */
    public java.util.List findAll(Session s) {
        return findAll(s, getDefaultOrder());
    }

    /**
     * Return all objects related to the implementation of this DAO with no
     * filter.
     */
    public java.util.List findAll(Order defaultOrder) {
        Session s = null;
        try {
            s = getSession();
            return findAll(s, defaultOrder);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Return all objects related to the implementation of this DAO with no
     * filter. Use the session given.
     *
     * @param s the Session
     */
    public java.util.List findAll(Session s, Order defaultOrder) {
        Criteria crit = s.createCriteria(getReferenceClass());
        if (null != defaultOrder)
            crit.addOrder(defaultOrder);
        return crit.list();
    }

    /**
     * Return all objects related to the implementation of this DAO with a
     * filter. Use the session given.
     *
     * @param propName the name of the property to use for filtering
     * @param filter   the value of the filter
     */
    protected Criteria findFiltered(String propName, Object filter) {
        return findFiltered(propName, filter, getDefaultOrder());
    }

    /**
     * Return all objects related to the implementation of this DAO with a
     * filter. Use the session given.
     *
     * @param propName the name of the property to use for filtering
     * @param filter   the value of the filter
     * @param order    the name of the property used for ordering
     */
    protected Criteria findFiltered(String propName, Object filter, Order order) {
        Session s = null;
        try {
            s = getSession();
            return findFiltered(s, propName, filter, order);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Return all objects related to the implementation of this DAO with a
     * filter. Use the session given.
     *
     * @param s        the Session
     * @param propName the name of the property to use for filtering
     * @param filter   the value of the filter
     * @param order    the name of the property used for ordering
     */
    protected Criteria findFiltered(Session s, String propName, Object filter,
                                    Order order) {
        Criteria crit = s.createCriteria(getReferenceClass());
        crit.add(Restrictions.eq(propName, filter));
        if (null != order)
            crit.addOrder(order);
        return crit;
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file.
     *
     * @param name the name of a query defined externally
     * @return Query
     */
    protected Query getNamedQuery(String name) {
        Session s = null;
        try {
            s = getSession();
            return getNamedQuery(name, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the session given.
     *
     * @param name the name of a query defined externally
     * @param s    the Session
     * @return Query
     */
    protected Query getNamedQuery(String name, Session s) {
        return s.getNamedQuery(name);
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file.
     *
     * @param name  the name of a query defined externally
     * @param param the first parameter to set
     * @return Query
     */
    protected Query getNamedQuery(String name, Serializable param) {
        Session s = null;
        try {
            s = getSession();
            return getNamedQuery(name, param, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the session given.
     *
     * @param name  the name of a query defined externally
     * @param param the first parameter to set
     * @param s     the Session
     * @return Query
     */
    protected Query getNamedQuery(String name, Serializable param, Session s) {
        Query q = s.getNamedQuery(name);
        q.setParameter(0, param);
        return q;
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given.
     *
     * @param name   the name of a query defined externally
     * @param params the parameter array
     * @return Query
     */
    protected Query getNamedQuery(String name, Serializable[] params) {
        Session s = null;
        try {
            s = getSession();
            return getNamedQuery(name, params, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given and the Session given.
     *
     * @param name   the name of a query defined externally
     * @param params the parameter array
     * @return Query
     * @s the Session
     */
    protected Query getNamedQuery(String name, Serializable[] params, Session s) {
        Query q = s.getNamedQuery(name);
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        return q;
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given.
     *
     * @param name   the name of a query defined externally
     * @param params the parameter Map
     * @return Query
     */
    protected Query getNamedQuery(String name, Map params) {
        Session s = null;
        try {
            s = getSession();
            return getNamedQuery(name, params, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given and the Session given.
     *
     * @param name   the name of a query defined externally
     * @param params the parameter Map
     * @return Query
     * @s the Session
     */
    protected Query getNamedQuery(String name, Map params, Session s) {
        Query q = s.getNamedQuery(name);
        if (null != params) {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                q.setParameter((String) entry.getKey(), entry.getValue());
            }
        }
        return q;
    }

    /**
     * Execute a query.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @return a distinct list of instances (or arrays of instances)
     */
    public Query getQuery(String queryStr) {
        Session s = null;
        try {
            s = getSession();
            return getQuery(queryStr, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Execute a query but use the session given instead of creating a new one.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param s        the Session to use
     */
    public Query getQuery(String queryStr, Session s) {
        return s.createQuery(queryStr);
    }

    /**
     * Execute a query.
     *
     * @param queryStr the name of a query defined externally
     * @param param    the first parameter to set
     * @return Query
     */
    protected Query getQuery(String queryStr, Serializable param) {
        Session s = null;
        try {
            s = getSession();
            return getQuery(queryStr, param, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Execute a query but use the session given instead of creating a new one.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param param    the first parameter to set
     * @return Query
     * @s the Session to use
     */
    protected Query getQuery(String queryStr, Serializable param, Session s) {
        Query q = getQuery(queryStr, s);
        q.setParameter(0, param);
        return q;
    }

    /**
     * Execute a query.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param params   the parameter array
     * @return Query
     */
    protected Query getQuery(String queryStr, Serializable[] params) {
        Session s = null;
        try {
            s = getSession();
            return getQuery(queryStr, params, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Execute a query but use the session given instead of creating a new one.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param params   the parameter array
     * @return Query
     * @s the Session
     */
    protected Query getQuery(String queryStr, Serializable[] params, Session s) {
        Query q = getQuery(queryStr, s);
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        return q;
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param params   the parameter Map
     * @return Query
     */
    protected Query getQuery(String queryStr, Map params) {
        Session s = null;
        try {
            s = getSession();
            return getQuery(queryStr, params, s);
        } finally {
            closeSession(s);
        }
    }

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given and the Session given.
     *
     * @param queryStr a query expressed in Hibernate's query language
     * @param params   the parameter Map
     * @return Query
     * @s the Session
     */
    protected Query getQuery(String queryStr, Map params, Session s) {
        Query q = getQuery(queryStr, s);
        if (null != params) {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                q.setParameter((String) entry.getKey(), entry.getValue());
            }
        }
        return q;
    }

    protected Order getDefaultOrder() {
        return null;
    }

    /**
     * Used by the base DAO classes but here for your modification Persist the
     * given transient instance, first assigning a generated identifier. (Or
     * using the current value of the identifier property if the assigned
     * generator is used.)
     */
    protected Serializable save(final Object obj) {
        return (Serializable) run(new TransactionRunnable() {
            public Object run(Session s) {
                return save(obj, s);
            }
        });
    }

    /**
     * Used by the base DAO classes but here for your modification Persist the
     * given transient instance, first assigning a generated identifier. (Or
     * using the current value of the identifier property if the assigned
     * generator is used.)
     */
    protected Serializable save(Object obj, Session s) {
        return s.save(obj);
    }

    /**
     * Used by the base DAO classes but here for your modification Either save()
     * or update() the given instance, depending upon the value of its
     * identifier property.
     */
    protected void saveOrUpdate(final Object obj) {
        run(new TransactionRunnable() {
            public Object run(Session s) {
                saveOrUpdate(obj, s);
                return null;
            }
        });
    }

    /**
     * Used by the base DAO classes but here for your modification Either save()
     * or update() the given instance, depending upon the value of its
     * identifier property.
     */
    protected void saveOrUpdate(Object obj, Session s) {
        s.saveOrUpdate(obj);
    }

    /**
     * Used by the base DAO classes but here for your modification Update the
     * persistent state associated with the given identifier. An exception is
     * thrown if there is a persistent instance with the same identifier in the
     * current session.
     *
     * @param obj a transient instance containing updated state
     */
    protected void update(final Object obj) {
        run(new TransactionRunnable() {
            public Object run(Session s) {
                update(obj, s);
                return null;
            }
        });
    }

    /**
     * Used by the base DAO classes but here for your modification Update the
     * persistent state associated with the given identifier. An exception is
     * thrown if there is a persistent instance with the same identifier in the
     * current session.
     *
     * @param obj a transient instance containing updated state
     * @param s   the Session
     */
    protected void update(Object obj, Session s) {
        s.update(obj);
    }

    /**
     * Delete all objects returned by the query
     */
    protected int delete(final Query query) {
        return (Integer) run(new TransactionRunnable() {
            public Object run(Session s) {
                return delete(query, s);
            }
        });
    }

    /**
     * Delete all objects returned by the query
     */
    protected int delete(Query query, Session s) {
        List list = query.list();
        for (Object aList : list)
            delete(aList, s);
        return list.size();
    }

    /**
     * Used by the base DAO classes but here for your modification Remove a
     * persistent instance from the datastore. The argument may be an instance
     * associated with the receiving Session or a transient instance with an
     * identifier associated with existing persistent state.
     */
    protected void delete(final Object obj) {
        run(new TransactionRunnable() {
            public Object run(Session s) {
                delete(obj, s);
                return null;
            }
        });
    }

    /**
     * Used by the base DAO classes but here for your modification Remove a
     * persistent instance from the datastore. The argument may be an instance
     * associated with the receiving Session or a transient instance with an
     * identifier associated with existing persistent state.
     */
    protected void delete(Object obj, Session s) {
        s.delete(obj);
    }

    /**
     * Used by the base DAO classes but here for your modification Re-read the
     * state of the given instance from the underlying database. It is
     * inadvisable to use this to implement long-running sessions that span many
     * business tasks. This method is, however, useful in certain special
     * circumstances.
     */
    protected void refresh(Object obj, Session s) {
        s.refresh(obj);
    }

    protected void throwException(Throwable t) {
        if (t instanceof HibernateException)
            throw (HibernateException) t;
        else if (t instanceof RuntimeException)
            throw (RuntimeException) t;
        else
            throw new HibernateException(t);
    }

    /**
     * Execute the given transaction runnable.
     */
    protected Object run(TransactionRunnable transactionRunnable) {
        Transaction t = null;
        Session s = null;
        try {
            s = getSession();
            t = beginTransaction(s);
            Object obj = transactionRunnable.run(s);
            commitTransaction(t);
            return obj;
        } catch (Throwable throwable) {
            if (null != t) {
                try {
                    t.rollback();
                } catch (HibernateException e) {
                    handleError(e);
                }
            }
            if (transactionRunnable instanceof TransactionFailHandler) {
                try {
                    ((TransactionFailHandler) transactionRunnable).onFail(s);
                } catch (Throwable e) {
                    handleError(e);
                }
            }
            throwException(throwable);
            return null;
        } finally {
            closeSession(s);
        }
    }

    /**
     * Execute the given transaction runnable.
     */
    protected TransactionPointer runAsnyc(
            TransactionRunnable transactionRunnable) {
        final TransactionPointer transactionPointer = new TransactionPointer(
                transactionRunnable);
        ThreadRunner threadRunner = new ThreadRunner(transactionPointer);
        threadRunner.start();
        return transactionPointer;
    }

    /**
     * This class can be used to encapsulate logic used for a single
     * transaction.
     */
    public abstract class TransactionRunnable {
        public abstract Object run(Session s) throws Exception;
    }

    /**
     * This class can be used to handle any error that has occured during a
     * transaction
     */
    public interface TransactionFailHandler {
        public void onFail(Session s);
    }

    /**
     * This class can be used to handle failed transactions
     */
    public abstract class TransactionRunnableFailHandler extends
            TransactionRunnable implements TransactionFailHandler {
    }

    public class TransactionPointer {
        private TransactionRunnable transactionRunnable;
        private Throwable thrownException;
        private Object returnValue;
        private boolean hasCompleted = false;

        public TransactionPointer(TransactionRunnable transactionRunnable) {
            this.transactionRunnable = transactionRunnable;
        }

        public boolean hasCompleted() {
            return hasCompleted;
        }

        public void complete() {
            this.hasCompleted = true;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        public void setReturnValue(Object returnValue) {
            this.returnValue = returnValue;
        }

        public Throwable getThrownException() {
            return thrownException;
        }

        public void setThrownException(Throwable thrownException) {
            this.thrownException = thrownException;
        }

        public TransactionRunnable getTransactionRunnable() {
            return transactionRunnable;
        }

        public void setTransactionRunnable(
                TransactionRunnable transactionRunnable) {
            this.transactionRunnable = transactionRunnable;
        }

        /**
         * Wait until the transaction completes and return the value returned
         * from the run method of the TransactionRunnable. If the transaction
         * throws an Exception, throw that Exception.
         *
         * @param timeout the timeout in milliseconds (or 0 for no timeout)
         * @return the return value from the TransactionRunnable
         * @throws Throwable the thrown Throwable
         */
        public Object waitUntilFinish(long timeout) throws Throwable {
            long killTime = -1;
            if (timeout > 0)
                killTime = System.currentTimeMillis() + timeout;
            do {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            } while (!hasCompleted
                    && ((killTime > 0 && System.currentTimeMillis() < killTime) || killTime <= 0));
            if (!hasCompleted)
                throw new javax.naming.TimeLimitExceededException();
            if (null != thrownException)
                throw thrownException;
            else
                return returnValue;
        }
    }

    private class ThreadRunner extends Thread {
        private TransactionPointer transactionPointer;

        public ThreadRunner(TransactionPointer transactionPointer) {
            this.transactionPointer = transactionPointer;
        }

        public void run() {
            Transaction t = null;
            Session s = null;
            try {
                s = getSession();
                t = beginTransaction(s);
                Object obj = transactionPointer.getTransactionRunnable().run(s);
                t.commit();
                transactionPointer.setReturnValue(obj);
            } catch (Throwable throwable) {
                if (null != t) {
                    try {
                        t.rollback();
                    } catch (HibernateException e) {
                        handleError(e);
                    }
                }
                if (transactionPointer.getTransactionRunnable() instanceof TransactionFailHandler) {
                    try {
                        ((TransactionFailHandler) transactionPointer
                                .getTransactionRunnable()).onFail(s);
                    } catch (Throwable e) {
                        handleError(e);
                    }
                }
                transactionPointer.setThrownException(throwable);
            } finally {
                transactionPointer.complete();
                try {
                    closeSession(s);
                } catch (HibernateException e) {
                    transactionPointer.setThrownException(e);
                }
            }
        }
    }

    protected void handleError(Throwable t) {
    }

}
