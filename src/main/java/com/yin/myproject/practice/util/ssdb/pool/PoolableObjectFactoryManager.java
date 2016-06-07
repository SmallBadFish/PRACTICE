package com.yin.myproject.practice.util.ssdb.pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool.PoolableObjectFactory;

/**
 * �����������Ϣ����
 * 
 * @author XunzhiYin
 *
 */
public class PoolableObjectFactoryManager {

	/**
	 * �����������Ϣ�б�
	 */
	private List<PoolableObjectFactory> factorys = new ArrayList<PoolableObjectFactory>();

	/* ��ǰ��������λ�� */
	private int currentConnectionIndex = 0;

	public PoolableObjectFactoryManager(String hostPortTimeOut) {
		String[] hosters = hostPortTimeOut.split(";");
		// �޳��ظ��ķ�������Ϣ
        Set<String> filter = new HashSet<String>();
		for (String hoster : hosters) {
			if (StringUtils.isNotBlank(hoster)) {
				String[] vals = hoster.trim().split(":");
				String host = vals[0];
				if (StringUtils.isBlank(host)) {
					continue;
				}
				// ���ö˿ڣ�Ĭ�϶˿�8888
				int port = getIntValue(vals[1], Protocol.DEFAULT_PORT);
				// ���ó�ʱ��Ĭ��4500����
				int timeout = getIntValue(vals[2], Protocol.DEFAULT_TIMEOUT);
				if (filter.contains(host + "_" + port)) {
					continue;
				}
				try {
					SSdbFactory factory = new SSdbFactory(host, port, timeout);
					// �����ķ�����������Ϣ���뵽�б�
					factorys.add(factory);
					filter.add(host + "_" + port);
					System.out.println("create host config :" + host + ":" + port + "|" + timeout);
				} catch (Exception e) {
				}
			} else {
				System.out.println("initial ssdb warring: " + "null host setting.");
			}
		}
	}

	/**
	 * ��ȡ��һ��������������Ϣ ������ѯ��ʽ
	 * 
	 * @return
	 */
	public synchronized PoolableObjectFactory getNext() {
		if (currentConnectionIndex > factorys.size() - 1) {
			currentConnectionIndex = 0;
		}
		PoolableObjectFactory temp = factorys.get(currentConnectionIndex);
		currentConnectionIndex++;
		return temp;
	}

	private int getIntValue(String v, int dft) {
		try {
			if (StringUtils.isNumeric(v.trim())) {
				return Integer.parseInt(v);
			}
		} catch (Exception e) {

		}
		return dft;
	}
}
