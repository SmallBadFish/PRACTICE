package com.yin.myproject.practice.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertieUtil {

	/**
	 * �洢�Ѽ��ص������ļ���Ϣ
	 */
	private static Map<String, Properties> _properties = new HashMap<String, Properties>();

	/**
	 * ����ָ���������ļ����ƣ��ļ���׺Ϊproperties
	 * 
	 * @param propsName
	 * @return
	 */
	public static Properties getProps(String propsName) {
		if (_properties.get(propsName) != null) {
			return _properties.get(propsName);
		}
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			@SuppressWarnings("unused")
			String packagePath = PropertieUtil.class.getResource("/").getPath();
			File file = new File(URLDecoder.decode(
					Properties.class.getClassLoader().getResource(propsName + ".properties").getFile(), "utf-8"));
			fis = new FileInputStream(file);
			props.load(fis);
		} catch (Exception e) {

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			_properties.put(propsName, props);
		}
		return props;
	}

	/**
	 * ��ȡָ�������ļ��У�ָ���ֶε�ֵ
	 * 
	 * @param propsName
	 *            �ļ���
	 * @param propName
	 *            �ֶ�
	 * @return
	 */
	public static String getStrProp(String propsName, String propName) {
		Properties props = getProps(propsName);
		if (props != null) {
			String propValue = props.getProperty(propName);
			if (propValue != null && !propValue.isEmpty()) {
				return propValue;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param propsName
	 * @param propName
	 * @return
	 */
	public static int getIntProp(String propsName, String propName) {
		Properties props = getProps(propsName);
		if (props != null) {
			String propValue = props.getProperty(propName);
			if (propValue != null && !propValue.isEmpty()) {
				return Integer.parseInt(propValue);
			}
		}
		return 0;
	}

	public static float getFloatProp(String propsName, String propName) {
		Properties props = getProps(propsName);
		if (props != null) {
			String propValue = props.getProperty(propName);
			if (propValue != null && !propValue.isEmpty()) {
				return Float.parseFloat(propValue);
			}
		}
		return 0.0f;
	}

	public static long getLongProp(String propsName, String propName) {
		Properties props = getProps(propsName);
		if (props != null) {
			String propValue = props.getProperty(propName);
			if (propValue != null && !propValue.isEmpty()) {
				return Long.parseLong(propValue);
			}
		}
		return 0l;
	}
}
