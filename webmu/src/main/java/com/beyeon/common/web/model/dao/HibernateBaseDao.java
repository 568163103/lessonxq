package com.beyeon.common.web.model.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.beyeon.common.web.model.util.PageObject;


/**
 * Hibernate Dao的基类.
 * 继承于Spring的<code>HibernateDaoSupport</code>,对数据库操作进行封装. *
 * @see HibernateDaoSupport
 */
@SuppressWarnings("unchecked")
public class HibernateBaseDao extends HibernateDaoSupport{

	public HibernateBaseDao() {
	}
	
    public HibernateBaseDao(SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    /**
     * @param <T>
     * @param entityClass
     * @param id
     * @return
     */
    public <T> T get(Class<T> entityClass, Serializable id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }

    /**
     * @param entityClass
     * @return
     */
    public <T> List<T> getAll(Class<T> entityClass) {
        return this.getHibernateTemplate().loadAll(entityClass);
    }

    /**
     * @param o
     */
    public void save(Object o) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().save(o);
    }

    /**
     * @param o
     */
    public void update(Object o) {
        this.getHibernateTemplate().update(o);
    }

    /**
     * @param o
     */
    public void saveOrUpdate(Object o) {
        this.getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * @param col
     */
    public void saveAll(Collection col) {
        for (Object o : col) {
            this.getHibernateTemplate().saveOrUpdate(o);
        }
    }
    
    public void fresh(Object entity) {
        this.getHibernateTemplate().refresh(entity);
    }
    
    public void flush() {
        this.getHibernateTemplate().flush();
    }
    
    public void clear() {
        this.getHibernateTemplate().clear();
    }
    
    public void evict(Object entity) {
        this.getHibernateTemplate().evict(entity);
    }
    
    public void evicts(List entitys) {
    	for (Object object : entitys) {
            this.getHibernateTemplate().evict(object);
		}
    }

    /**
     * @param hql
     * @param values
     */
    public void bulkUpdate(String hql, Object[] values) {
        this.getHibernateTemplate().bulkUpdate(hql, values);
    }
    
    public void bulkUpdateByParamName(final String hql, final String[] parmaNames, final Object[] values) {
    	this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(hql);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Object[]) {
                            queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                        } else if (values[i] instanceof Collection) {
                            queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                        } else {
	                            queryObject.setParameter(parmaNames[i], values[i]);
	                    }
					}
				}
				return queryObject.executeUpdate();
			}
		});
    }

    /**
     * @param queryString
     * @param values
     * @return
     */
    public int bulkUpdateSQL(final String queryString, final Object[] values) {
        Integer updateCount = (Integer) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query queryObject = session.createSQLQuery(queryString);
                prepareQuery(queryObject);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return new Integer(queryObject.executeUpdate());
            }
        });
        return updateCount.intValue();
    }

	public void bulkUpdateSQLByParamName(final String sql, final String[] parmaNames, final Object[] values) {
		this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(sql);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Object[]) {
							queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
						} else if (values[i] instanceof Collection) {
							queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
						} else {
							queryObject.setParameter(parmaNames[i], values[i]);
						}
					}
				}
				return queryObject.executeUpdate();
			}
		});
	}
    
    /**
     * @param o
     */
    public void delete(Object o) {
        this.getHibernateTemplate().delete(o);
    }

    /**
     * @param col
     */
    public void deleteAll(Collection col) {
        this.getHibernateTemplate().deleteAll(col);
    }

    /**
     * @param entityClass
     * @param id
     */
    public void deleteById(Class entityClass, Serializable id) {
        delete(get(entityClass, id));
    }

    /**
     * @param queryString
     * @param parmaNames
     * @param values
     * @return
     */
    public List findByParamName(final String queryString, final String[] parmaNames, final Object[] values) {
        return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = session.createQuery(queryString);
                prepareQuery(queryObject);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                    	if (values[i] instanceof Object[]) {
                            queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                        } else if (values[i] instanceof Collection) {
                            queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                        } else {
                            queryObject.setParameter(parmaNames[i], values[i]);
                        }
                    }
                }
                return queryObject.list();
            }
        });
    }

    /**
     * @param page
     * @param hql
     * @param parmaNames
     * @param values
     * @param isGroup
     * @return
     */
    public List findByParamName(final PageObject page, final String hql, final String[] parmaNames, final Object[] values, final boolean isGroup) {
    	final String queryString = pageSort(page, hql);
    	return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = null;
                //Count查询
                if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
                    String countQueryString = getCountSql(queryString,isGroup);
                    queryObject = session.createQuery(countQueryString);
                    prepareQuery(queryObject);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                        	if (values[i] instanceof Object[]) {
                                queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                            } else if (values[i] instanceof Collection) {
                                queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                            } else {
                                queryObject.setParameter(parmaNames[i], values[i]);
                            }
                        }
                    }
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
                }
                if (page.getTotalNum() < 1)
                    return null;
                queryObject = session.createQuery(queryString);
                prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                    	if (values[i] instanceof Object[]) {
                            queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                        } else if (values[i] instanceof Collection) {
                            queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                        } else {
                            queryObject.setParameter(parmaNames[i], values[i]);
                        }
                    }
                }

                page.setResultList(queryObject.list());
                return page.getResultList();
            }
        });
    }

    /**
     * @param queryString
     * @param parmaNames
     * @param values
     * @return
     */

	public List findSQLByParamName(final String queryString, final String[] parmaNames, final Object[] values) {
		return findSQLByParamName(queryString,parmaNames,values,null);
	}

    public List findSQLByParamName(final String queryString, final String[] parmaNames, final Object[] values,final Class clazz) {
        return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                SQLQuery queryObject = session.createSQLQuery(queryString);
				if(null != clazz){
					queryObject.addEntity(clazz);
				}
                prepareQuery(queryObject);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                    	if (values[i] instanceof Object[]) {
                            queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                        } else if (values[i] instanceof Collection) {
                            queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                        } else {
                            queryObject.setParameter(parmaNames[i], values[i]);
                        }
                    }
                }
                return queryObject.list();
            }
        });
    }

	public List findSQLToMapByParamName(final String queryString, final String[] parmaNames, final Object[] values) {
		return findSQLToObjectByParamName(queryString,parmaNames,values,null);
	}

	public List findSQLToBeanByParamName(final String queryString, final String[] parmaNames, final Object[] values, final Class clazz) {
		return findSQLToObjectByParamName(queryString,parmaNames,values,clazz);
	}

	private List findSQLToObjectByParamName(final String queryString, final String[] parmaNames, final Object[] values, final Class clazz) {
		return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				if (null != clazz) {
					queryObject.setResultTransformer(Transformers.aliasToBean(clazz));
				} else {
					queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				}
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Object[]) {
							queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
						} else if (values[i] instanceof Collection) {
							queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
						} else {
							queryObject.setParameter(parmaNames[i], values[i]);
						}
					}
				}
				return queryObject.list();
			}
		});
	}

    /**
     * @param page
     * @param sql
     * @param parmaNames
     * @param values
     * @param isGroup
     * @return
     */
	public List findSQLByParamName(final PageObject page, final String sql, final String[] parmaNames, final Object[] values, final boolean isGroup) {
		return findSQLByParamName(page,sql,parmaNames,values,isGroup,null);
	}

	public List findSQLByParamName(final PageObject page, final String sql, final String[] parmaNames, final Object[] values, final boolean isGroup, final Class clazz) {
    	final String queryString = pageSort(page, sql);
    	return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                SQLQuery queryObject = null;
                //Count查询
                if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
                    String countQueryString = getCountSql(queryString,isGroup);
                    queryObject = session.createSQLQuery(countQueryString);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                        	if (values[i] instanceof Object[]) {
                                queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                            } else if (values[i] instanceof Collection) {
                                queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                            } else {
                                queryObject.setParameter(parmaNames[i], values[i]);
                            }
                        }
                    }
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
                }
                if (page.getTotalNum() < 1)
                    return null;
                queryObject = session.createSQLQuery(queryString);
				if (null != clazz) {
					queryObject.addEntity(clazz);
				}
                prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                    	if (values[i] instanceof Object[]) {
                            queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
                        } else if (values[i] instanceof Collection) {
                            queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
                        } else {
                            queryObject.setParameter(parmaNames[i], values[i]);
                        }
                    }
                }
                page.setResultList(queryObject.list());
                return page.getResultList();
            }
        });
    }


	/**
	 * @param page
	 * @param sql
	 * @param parmaNames
	 * @param values
	 * @param isGroup
	 * @return
	 */
	public List findSQLToMapByParamName(final PageObject page, final String sql, final String[] parmaNames, final Object[] values, final boolean isGroup) {
		return findSQLToObjectByParamName(page,sql,parmaNames,values,isGroup,null);
	}

	/**
	 * @param page
	 * @param sql
	 * @param parmaNames
	 * @param values
	 * @param isGroup
	 * @return
	 */
	public List findSQLToBeanByParamName(final PageObject page, final String sql, final String[] parmaNames, final Object[] values, final boolean isGroup,final Class clazz) {
		return findSQLToObjectByParamName(page,sql,parmaNames,values,isGroup,clazz);
	}

	/**
		 * @param page
		 * @param sql
		 * @param parmaNames
		 * @param values
		 * @param isGroup
		 * @return
		 */
	private List findSQLToObjectByParamName(final PageObject page, final String sql, final String[] parmaNames, final Object[] values, final boolean isGroup,final Class clazz) {
		final String queryString = pageSort(page, sql);
		return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = null;
				//Count查询
				if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
					String countQueryString = getCountSql(queryString,isGroup);
					queryObject = session.createSQLQuery(countQueryString);
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							if (values[i] instanceof Object[]) {
								queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
							} else if (values[i] instanceof Collection) {
								queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
							} else {
								queryObject.setParameter(parmaNames[i], values[i]);
							}
						}
					}
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
				}
				if (page.getTotalNum() < 1)
					return null;
				queryObject = session.createSQLQuery(queryString);
				if (null != clazz) {
					queryObject.setResultTransformer(Transformers.aliasToBean(clazz));
				} else {
					queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				}
				prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Object[]) {
							queryObject.setParameterList(parmaNames[i], (Object[]) values[i]);
						} else if (values[i] instanceof Collection) {
							queryObject.setParameterList(parmaNames[i], (Collection) values[i]);
						} else {
							queryObject.setParameter(parmaNames[i], values[i]);
						}
					}
				}
				page.setResultList(queryObject.list());
				return page.getResultList();
			}
		});
	}

    /**
     * @param queryString
     * @param values
     * @return
     */
    public List find(String queryString, Object[] values) {
        return this.getHibernateTemplate().find(queryString, values);
    }
    
    /**
     * @param queryString
     * @param values
     * @param max
     * @return
     */
    public List find(String queryString, Object[] values, Integer max) {
        return this.find(new PageObject(max), queryString, values);
    }

    /**
     * @param page
     * @param queryString
     * @param values
     * @return
     */
    public List find(final PageObject page, final String queryString, final Object[] values) {
        return find(page, queryString, values, false);
    }

    /**
     * @param page
     * @param hql
     * @param values
     * @param isGroup
     * @return
     */
    public List find(final PageObject page, final String hql, final Object[] values, final boolean isGroup) {
    	final String queryString = pageSort(page, hql);
    	return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query queryObject = null;
                //Count查询
                if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
                    String countQueryString = getCountSql(queryString,isGroup);
                    queryObject = session.createQuery(countQueryString);
                    prepareQuery(queryObject);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            queryObject.setParameter(i, values[i]);
                        }
                    }
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
               }
                if (page.getTotalNum() < 1)
                    return null;
                // 实际查询返回分页对象
                queryObject = session.createQuery(queryString);
                prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                page.setResultList(queryObject.list());
                return page.getResultList();
            }
        });
    }

    /**
     * @param sql
     * @param values
     * @return
     */
    public List findSQL(final String sql, final Object[] values) {
        return findSQL(sql, values, null);
    }

    /**
     * @param sql
     * @param values
     * @param clazz
     * @return
     */
    public List findSQL(final String sql, final Object[] values, final Class clazz) {
        return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                SQLQuery queryObject = session.createSQLQuery(sql);
                prepareQuery(queryObject);
                if (null != clazz) {
                    queryObject.addEntity(clazz);
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.list();
            }
        });
    }

	/**
	 * @param sql
	 * @param values
	 * @return
	 */
	public List findSQLToMap(final String sql, final Object[] values) {
		return findSQLToObject(sql, values, null);
	}

	/**
	 * @param sql
	 * @param values
	 * @return
	 */
	public List findSQLToBean(final String sql, final Object[] values, final Class clazz) {
		return findSQLToObject(sql, values, clazz);
	}

	/**
	 * @param sql
	 * @param values
	 * @param clazz
	 * @return
	 */
	private List findSQLToObject(final String sql, final Object[] values, final Class clazz) {
		return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SQLQuery queryObject = session.createSQLQuery(sql);
				prepareQuery(queryObject);
				if (null != clazz) {
					queryObject.setResultTransformer(Transformers.aliasToBean(clazz));
				} else {
					queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				}
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.list();
			}
		});
	}

    /**
     * @param page
     * @param sql
     * @param objects
     * @return
     */
    public List findSQL(final PageObject page, final String sql, final Object[] objects) {
        return findSQL(page, sql, objects, false);
    }

    /**
     * @param page
     * @param sql
     * @param objects
     * @param clazz
     * @return
     */
    public List findSQL(final PageObject page, final String sql, final Object[] objects, final Class clazz) {
        return findSQL(page, sql, objects, false, clazz);
    }

    /**
     * @param page
     * @param sql
     * @param objects
     * @param isGroup
     * @return
     */
    public List findSQL(final PageObject page, final String sql, final Object[] objects, final boolean isGroup) {
        return findSQL(page, sql, objects, isGroup, null);
    }

    /**
     * @param page
     * @param sql
     * @param objects
     * @param isGroup
     * @param clazz
     * @return
     */
    public List findSQL(final PageObject page, final String sql, final Object[] objects, final boolean isGroup, final Class clazz) {
    	final String queryString = pageSort(page, sql);
    	return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                SQLQuery queryObject = null;
                if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
                    String countQueryString = getCountSql(queryString,isGroup);
                    queryObject = session.createSQLQuery(countQueryString);
                    prepareQuery(queryObject);
                    if (objects != null) {
                        for (int i = 0; i < objects.length; i++) {
                            queryObject.setParameter(i, objects[i]);
                        }
                    }
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
                }
                if (page.getTotalNum() < 1)
                    return null;

                queryObject = session.createSQLQuery(queryString);
                if (null != clazz) {
                    queryObject.addEntity(clazz);
                }
                prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
                if (objects != null) {
                    for (int i = 0; i < objects.length; i++) {
                        queryObject.setParameter(i, objects[i]);
                    }
                }
                page.setResultList(queryObject.list());
                return page.getResultList();
            }
        });
    }

	/**
	 * @param page
	 * @param sql
	 * @param objects
	 * @return
	 */
	public List findSQLToMap(final PageObject page, final String sql, final Object[] objects) {
		return findSQLToMap(page, sql, objects, false);
	}

	/**
	 * @param page
	 * @param sql
	 * @param objects
	 * @param clazz
	 * @return
	 */
	public List findSQLToBean(final PageObject page, final String sql, final Object[] objects, final Class clazz) {
		return findSQLToBean(page, sql, objects, false, clazz);
	}

	/**
	 * @param page
	 * @param sql
	 * @param objects
	 * @param isGroup
	 * @return
	 */
	public List findSQLToMap(final PageObject page, final String sql, final Object[] objects, final boolean isGroup) {
		return findSQLToObject(page, sql, objects, isGroup, null);
	}

	/**
	 * @param page
	 * @param sql
	 * @param objects
	 * @param isGroup
	 * @return
	 */
	public List findSQLToBean(final PageObject page, final String sql, final Object[] objects, final boolean isGroup, final Class clazz) {
		return findSQLToObject(page, sql, objects, isGroup, clazz);
	}

	/**
	 * @param page
	 * @param sql
	 * @param objects
	 * @param isGroup
	 * @param clazz
	 * @return
	 */
	private List findSQLToObject(final PageObject page, final String sql, final Object[] objects, final boolean isGroup, final Class clazz) {
		final String queryString = pageSort(page, sql);
		return (List) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SQLQuery queryObject = null;
				if (page.getTotalNum() < 1 || page.getPageClick()==0 || page.getCurrentPageNum() >= page.getPageNum()-1) {
					String countQueryString = getCountSql(queryString,isGroup);
					queryObject = session.createSQLQuery(countQueryString);
					prepareQuery(queryObject);
					if (objects != null) {
						for (int i = 0; i < objects.length; i++) {
							queryObject.setParameter(i, objects[i]);
						}
					}
                    if (!isGroup) {
                        page.setTotalNum(Long.parseLong(queryObject.uniqueResult().toString()));
                    } else {
                        page.setTotalNum(queryObject.list().size());
                    }
				}
				if (page.getTotalNum() < 1)
					return null;

				queryObject = session.createSQLQuery(queryString);
				if (null != clazz) {
					queryObject.setResultTransformer(Transformers.aliasToBean(clazz));
				} else {
					queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				}
				prepareQuery(queryObject, page.getStartLineNum(), page.getPageSize());
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						queryObject.setParameter(i, objects[i]);
					}
				}
				page.setResultList(queryObject.list());
				return page.getResultList();
			}
		});
	}

	/**
	 * @param sql
	 * @param values
	 * @return
	 */
	public Object findUniqueSql(final String sql, final Object[] values){
		return findUniqueSql( sql,  values, null);
	}

    /**
     * @param sql
     * @param values
     * @param clazz
     * @return
     */
    public Object findUniqueSql(final String sql, final Object[] values, final Class clazz) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
            	SQLQuery queryObject = session.createSQLQuery(sql);
                prepareQuery(queryObject);
                if (null != clazz) {
                    queryObject.addEntity(clazz);
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.uniqueResult();
            }
        });
    }

	public Object findUniqueSqlToMap(final String sql, final Object[] values) {
		return findUniqueSqlToObject(sql, values, null);
	}

	public Object findUniqueSqlToBean(final String sql, final Object[] values, final Class clazz) {
		return findUniqueSqlToObject(sql, values, clazz);
	}
	/**
	 * @param sql
	 * @param values
	 * @param clazz
	 * @return
	 */
	private Object findUniqueSqlToObject(final String sql, final Object[] values, final Class clazz) {
		return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SQLQuery queryObject = session.createSQLQuery(sql);
				prepareQuery(queryObject);
				if (null != clazz) {
					queryObject.setResultTransformer(Transformers.aliasToBean(clazz));
				} else {
					queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				}
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.uniqueResult();
			}
		});
	}


	/**
     * @param hql
     * @param values
     * @return
     */
    public Object findUnique(final String hql, final Object[] values) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = session.createQuery(hql);
                prepareQuery(queryObject);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.uniqueResult();
            }
        });
    }
    
    /**
     * @param hql
     * @param values
     * @return
     */
    public List findList(final String hql, final Object[] values) {
        return (List)this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = session.createQuery(hql);
                prepareQuery(queryObject);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.list();
            }
        });
    }

    /**
     * @param queryString
     * @return
     */
	public static String getCountSql(String queryString,Boolean isGroup) {
		queryString = queryString.trim();
		String temp = queryString.toLowerCase();
		int from = temp.indexOf("from ");
		int groupBy = temp.lastIndexOf("group by");
		int orderby = temp.lastIndexOf("order by");
		int brackets = temp.lastIndexOf(")");
		String count = "*";
		if (!isGroup && (groupBy < 0 || groupBy < brackets)) {
			groupBy = temp.length();
		}
		if(orderby < 0 || orderby < brackets){
			orderby = temp.length();
		}
		StringBuffer countSql = new StringBuffer("select count(");
		/*if(isGroup){
			countSql.append("distinct");
			count = queryString.substring(groupBy + "group by".length(),orderby);
			orderby = groupBy;
		}*/
		countSql.append(count).append(") ");
		countSql.append(queryString.substring(from, orderby));
		return countSql.toString();
	}

    /**
     * @param queryObject
     * @param begin
     * @param size
     */
    protected void prepareQuery(Query queryObject, long begin, int size) {
        if (this.getHibernateTemplate().isCacheQueries()) {
            queryObject.setCacheable(true);
            if (this.getHibernateTemplate().getQueryCacheRegion() != null) {
                queryObject.setCacheRegion(this.getHibernateTemplate().getQueryCacheRegion());
            }
        }
        if (this.getHibernateTemplate().getFetchSize() > 0) {
            queryObject.setFetchSize(this.getHibernateTemplate().getFetchSize());
        }
        if (begin > 0) {
            queryObject.setFirstResult((int)begin);
        }
        if (size > 0) {
            queryObject.setMaxResults(size);
        }

        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(getSessionFactory());
        if (sessionHolder != null && sessionHolder.hasTimeout()) {
            queryObject.setTimeout(sessionHolder.getTimeToLiveInSeconds());
        }
    }

    /**
     * @param queryObject
     */
    protected void prepareQuery(Query queryObject) {
       prepareQuery(queryObject,0,0);
    }
    
    protected String pageSort(final PageObject pageObject,String hql){
    	StringBuffer hqlTmep = new StringBuffer(hql);
		if (StringUtils.isNotBlank(pageObject.getSortName())) {
			int orderby = hqlTmep.lastIndexOf("order by");
			int brackets = hqlTmep.lastIndexOf(")");
			if(orderby > brackets){
				hqlTmep.delete(orderby,hqlTmep.length());
			}
			int first = pageObject.getSortName().indexOf("_");
			hqlTmep.append(" order by ")
			.append(pageObject.getSortName().substring(0, first))
			.append(".")
			.append(pageObject.getSortName().substring(first+1))
			.append(" ")
			.append(pageObject.getSortOrder());
		}
		return hqlTmep.toString();
    }
}
