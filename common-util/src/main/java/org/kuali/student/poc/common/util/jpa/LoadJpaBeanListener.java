package org.kuali.student.poc.common.util.jpa;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

public class LoadJpaBeanListener implements ApplicationListener,
		ApplicationContextAware {

	private Map<String, List<Object>> preloadMap;
	private ApplicationContext applicationContext;
	private LoadJpaBean loadJpaBean;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			for (Entry<String, List<Object>> entry : preloadMap.entrySet()) {
				List<Object> beanList = entry.getValue();
				EntityManagerFactory emf = EntityManagerFactoryUtils
						.findEntityManagerFactory(applicationContext, entry
								.getKey());
				EntityManager em = SharedEntityManagerCreator
						.createSharedEntityManager(emf);
				try {
					loadJpaBean.loadData(beanList, em);
				} catch (Exception e) {
					//Log here, need to catch because if errors are thrown then the transaction is left hanging for some reason
				}
			}
		}
	}

	/**
	 * @return the preloadMap
	 */
	public Map<String, List<Object>> getPreloadMap() {
		return preloadMap;
	}

	/**
	 * @param preloadMap
	 *            the preloadMap to set
	 */
	public void setPreloadMap(Map<String, List<Object>> preloadMap) {
		this.preloadMap = preloadMap;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return the loadJpaBean
	 */
	public LoadJpaBean getLoadJpaBean() {
		return loadJpaBean;
	}

	/**
	 * @param loadJpaBean
	 *            the loadJpaBean to set
	 */
	public void setLoadJpaBean(LoadJpaBean loadJpaBean) {
		this.loadJpaBean = loadJpaBean;
	}

}
