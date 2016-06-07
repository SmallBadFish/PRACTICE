package com.yin.myproject.practice.util.ssdb.pool;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yin.myproject.practice.util.ssdb.SSdbException;


/**
 * ������ӣ����Ӷ�д��������ӳع���
 * 
 * 
 */
public class SSdbClient {

    private final static Logger logger = LoggerFactory.getLogger(SSdbClient.class);

    // �������������ӳأ�����д����
    private SSdbPool masterPool;
    // ��ӷ��������ӳأ����������
    private SSdbPool slaverPool;

    public static enum State {
        non, both, master, slaver
    }

    private State runState = State.non;

    public SSdbClient() {
    }

    /**
     * ��ʼ�����ӳع������
     * 
     * @param mjp
     *            �������ӳ�
     * @param sjp
     *            ������ӳ�
     */
    public SSdbClient(SSdbPool mjp, SSdbPool sjp) {
        setMasterPool(mjp);
        setSlaverPool(sjp);
    }

    // ��[����������]���ӳػ��һ������
    private SSdbConnection getMaster() throws SSdbException {
        try {
            return getMasterPool().getConnection();
        } catch (Exception e) {
            throw new SSdbException("get master error :" + e.getMessage());
        }
    }

    // �黹������
    private void returnMaster(SSdbConnection connector) {
        try {
            if (connector != null) {
                getMasterPool().returnResource(connector);
            }
        } catch (Exception e) {
        }
    }

    // ��[��ӷ�����]���ӳػ��һ������
    private SSdbConnection getSlaver() throws SSdbException {
        try {
            return getSlaverPool().getConnection();
        } catch (Exception e) {
            throw new SSdbException("get slaver error :" + e.getMessage());
        }
    }

    // �黹������
    private void returnSlaver(SSdbConnection connector) {
        try {
            if (connector != null) {
                getSlaverPool().returnResource(connector);
            }
        } catch (Exception e) {
        }
    }

    /**
     * ��[��������]�ϱ���ָ��key��valueֵ
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("SSdbClient Master.set error {} , Key:{} ,Value:{} .", e, key, value);
            return false;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]�ϱ���ָ��key��valueֵ��������Ч����seconds��
     * 
     * @param key
     *            ��
     * @param value
     *            ֵ
     * @param seconds
     *            ��Ч�ڣ���
     * @return
     */
    public boolean setExp(String key, String value, int seconds) {
        SSdbConnection jssdb = null;
        try {
            jssdb = getMaster();
            jssdb.setExp(key, value, seconds);
            return true;
        } catch (Exception e) {
            logger.error("SSdbClient Master.setExp error {} , Key:{} ,Value:{} ,Seconds:{} ", e, key, value, seconds);
            return false;
        } finally {
            returnMaster(jssdb);
        }
    }

    /**
     * ��[�ӷ�����]��ȡָ��key��valueֵ���ӷ��������������ӳ�
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        SSdbConnection jssdb = null;
        try {
            jssdb = getSlaver();
            return new String(jssdb.get(key));
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.get error {} , Key:{} ", e, key);
            return null;
        } finally {
            returnSlaver(jssdb);
        }
    }

    /**
     * ��[��������]��ȡָ��key��valueֵ
     * 
     * @param key
     * @return
     */
    public String getFromMaster(String key) {
        SSdbConnection jssdb = null;
        try {
            jssdb = getMaster();
            return new String(jssdb.get(key));
        } catch (Exception e) {
            logger.warn("SSdbClient Master.getFromMaster error {} , Key:{} ", e, key);
            return null;
        } finally {
            returnMaster(jssdb);
        }
    }

    /**
     * ��[��������]�ϱ���ָ��key�Ķ���value
     * 
     * @param key
     * @param value
     * @return
     */
    public <T extends Serializable> boolean setPojo(String key, T value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.setPojo(key, value);
            return true;
        } catch (Exception e) {
            logger.error("SSdbClient Master.setPojo error {} , Key:{} ,Value:{}  ", e, key, value.toString());
            return false;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]�ϱ���ָ��key�Ķ���value����Ч��seconds
     * 
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public <T extends Serializable> boolean setPojoExp(String key, T value, int seconds) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.setPojoExp(key, value, seconds);
            return true;
        } catch (Exception e) {
            logger.error("SSdbClient Master.setPojoExp error {} , Key:{} ,seconds:{}  ", e, key, seconds);
            return false;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]���ָ��key��pojo����
     * 
     * @param key
     * @return
     */
    public <T extends Serializable> T getPojo(String key) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.getPojo(key);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.getPojo error {} , Key:{}  ", e, key);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[��������]���ָ��key��pojo����
     * 
     * @param key
     * @return
     */
    public <T extends Serializable> T getPojoFromMaster(String key) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.getPojo(key);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.getPojoFromMaster error {} , Key:{}  ", e, key);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��������key-value��pojo������Ϣ
     * 
     * @param keys
     *            ������key
     * @param values
     *            ������pojo���󣻺�keysһһ��Ӧ
     * @throws Exception
     *             the size of keys and values is not equal..
     */
    public <T extends Serializable> void mSetPojo(List<String> keys, List<T> values) throws Exception {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.mSetPojo(keys, values);
        } catch (Exception e) {
            logger.error("SSdbClient Master.mSetPojo error {} , Keys:{},Values:{} ", e, keys, values);
            throw e;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]������ȡָ��key��pojo����
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public <T extends Serializable> List<T> mGetPojo(List<String> keys) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.mGetPojo(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.mGetPojo error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��������key-value��pojo������Ϣ
     * 
     * @param keys
     *            ������key
     * @param values
     *            ������String����keysһһ��Ӧ
     * @throws Exception
     *             the size of keys and values is not equal..
     */
    public <T extends Serializable> void mSet(List<String> keys, List<String> values) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.mSet(keys, values);
        } catch (Exception e) {
            logger.error("SSdbClient Master.mSet error {} , Keys:{} ", e, keys);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]������ȡָ��key��String
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public Map<String, String> mGet(List<String> keys) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.mGet(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.mGet error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[�ӷ�����]������ȡָ��key��String
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public Map<String, String> mGet(String[] keys) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.mGet(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.mGet error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[�ӷ�����]������ȡָ��key��String
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public List<KeyValueBean> mGetAsList(String[] keys) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.mGetAsList(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.mGetAsList error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[��������]������ȡָ��key��String
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public Map<String, String> mGetMaster(List<String> keys) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.mGet(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.mGetMaster error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]������ȡָ��key��pojo����
     * 
     * @param keys
     *            ָ��������keyֵ����
     * @return
     */
    public <T extends Serializable> List<T> mGetFromMaster(List<String> keys) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.mGetPojo(keys);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.mGetFromMaster error {} , Keys:{} ", e, keys);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    // /**
    // * �ӷ��������Ƿ����ָ��key��ֵ
    // * @param key
    // * @return
    // */
    // public boolean exists(String key) {
    // Jssdb jssdb = null;
    // try {
    // jssdb = getSlaver();
    // return jssdb.exists(key);
    // } catch (Exception e) {
    // return false;
    // } finally {
    // returnSlaver(jssdb);
    // }
    // }
    /**
     * ��[��������]�ϣ����������ָ��key�����ݣ��򱣴�value������������
     * 
     * @param key
     * @param value
     */
    public void setNotExsits(String key, String value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.setNx(key, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.setNotExsits error {} , Key:{} ,Value:{}", e, key, value);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]�ϣ����������ָ��key��pojo�����򱣴�value������������
     * 
     * @param key
     * @param value
     */
    public <T extends Serializable> void setPojoNotExsits(String key, T value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.setPojoNx(key, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.setPojoNotExsits error {} , Key:{} ,Value:{}", e, key, value);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]�ϣ�ʹ key ��Ӧ��ֵ���� delt. ���� delt ����Ϊ����. ���ԭ����ֵ��������(�ַ�����ʽ������), ���ᱻ��ת��������
     * 
     * @param key
     * @param delt
     * @return
     */
    public long increase(String key, long delt) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.increase(key, delt);
        } catch (Exception e) {
            logger.error("SSdbClient Master.increase error {} , Key:{} ,Delt:{}", e, key, delt);
            return -12345678900l;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]��ɾ��ָ��key��ֵ
     * 
     * @param key
     */
    public void delete(String key) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.delete(key);
        } catch (Exception e) {
            logger.error("SSdbClient Master.delete error {} , Key:{}   ", e, key);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]������ָ��map��ָ��key��value����
     * 
     * @param mapperName
     *            map��
     * @param key
     *            ��
     * @param value
     *            ֵ
     */
    public void hSet(String mapperName, String key, String value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.hSet(mapperName, key, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.hSet error {} ,MapperName:{} , Key:{} ,Value:{} ", e, mapperName, key,
                    value);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]��ָ��map���ֵ�map�У����ָ��key�Ķ���ֵ
     * 
     * @param mapperName
     * @param key
     * @return
     */
    public String hGet(String mapperName, String key) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.hGet(mapperName, key);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.hSetPojo error {} ,MapperName:{} , Key:{}  ", e, mapperName, key);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[��������]��ָ��map���ֵ�map�У����ָ��key�Ķ���ֵ
     * 
     * @param mapperName
     *            map����
     * @param key
     * @return
     */
    public String hGetFromMaster(String mapperName, String key) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.hGet(mapperName, key);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.hSetPojo error {} ,MapperName:{} , Key:{}  ", e, mapperName, key);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]������ָ��map��ָ��key��pojo����
     * 
     * @param mapperName
     *            map��
     * @param key
     *            ��
     * @param value
     *            pojo����
     */
    public <T extends Serializable> void hSetPojo(String mapperName, String key, T value) throws SSdbException {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.hSetPojo(mapperName, key, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.hSetPojo error {} ,MapperName:{} , Key:{} ,Value:{}", e, mapperName, key,
                    value.toString());
            throw new SSdbException(" hSetPojo master error :" + e.getMessage());
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]��ָ��map���ֵ�map�У����ָ��key��pojo����
     * 
     * @param mapperName
     *            map����
     * @param key
     * @return
     */
    public <T extends Serializable> T hGetPojo(String mapperName, String key) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.hGetPojo(mapperName, key);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.hGetPojo error {} , MapperName:{} , Key:{} ", e, mapperName, key);
            return null;
        } finally {
            returnSlaver(connector);
        }
    }

    /**
     * ��[��������]��ָ��map���ֵ�map�У����ָ��key��pojo����ֵ
     * 
     * @param mapperName
     *            map����
     * @param key
     * @return
     */
    public <T extends Serializable> T hGetPojoFromMaster(String mapperName, String key) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.hGetPojo(mapperName, key);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.hGetPojo error {} , MapperName:{} , Key:{} ", e, mapperName, key);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]��ָ��map����map��ɾ��ָ��key����Ϣ
     * 
     * @param mapperName
     *            map��
     * @param key
     */
    public void hDelete(String mapperName, String key) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.hDelete(mapperName, key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]ɾ��ָ�����ֵ�map��Ϣ
     * 
     * @param mapperName
     */
    public void hClear(String mapperName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.hClear(mapperName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[��������]�ϣ���������ָ�����ֵ�map��Ϣ��
     * 
     * @param mapName
     *            map����
     * @param keys
     *            ������key
     * @param values
     *            ��Ӧ������value
     */
    public void mHSet(String mapperName, List<String> keys, List<String> values) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.multiHSet(mapperName, keys, values);
        } catch (Exception e) {
            logger.error("SSdbClient Master.mHSet error {} , MapperName:{} , Keys:{} ,Values:{}", e, mapperName, keys,
                    values);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ��[�ӷ�����]��ѯָ�����ֵ�map��Ϣ
     * 
     * @param mapName
     *            map����
     */
    public Map<String, String> hGetAll(String name) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.hGetAll(name);
        } catch (Exception e) {
            logger.warn("SSdbClient Slaver.hGetAll error {} , KeyName:{}", e, name);
            return new HashMap<String, String>(0);
        } finally {
            returnSlaver(connector);
        }
    }

    public long hSize(String name) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.hSize(name);
        } catch (Exception e) {
            logger.error("SSdbClient Slaver.hSize error {} , MapperName:{}", e, name);

        } finally {
            returnSlaver(connector);
        }
        return 0;

    }

    /**
     * ��[��������]��ѯָ�����ֵ�map��Ϣ
     * 
     * @param mapName
     *            map����
     */
    public Map<String, String> hGetAllFromMaster(String name) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.hGetAll(name);
        } catch (Exception e) {
            logger.warn("SSdbClient Master.hGetAll error {} , KeyName:{}", e, name);
            return new HashMap<String, String>(0);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ����������Ӷ���
     * 
     * @param queueName
     *            ������
     * @param value
     *            �ַ�������
     */
    public void qPush(String queueName, String value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.qPush(queueName, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPush error {} , QueueName:{} , Value:{}", e, queueName, value);
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ����������Ӷ���
     * 
     * @param queueName
     *            ������
     * @param value
     *            pojo����
     */
    public <T extends Serializable> void qPushPojo(String queueName, T value) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.qPushPojo(queueName, value);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPushPojo error {} , QueueName:{} , Value:{}", e, queueName,
                    value.toString());

        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ɾ��ָ�����ֵĶ��еĶ���Ԫ�أ�������
     * 
     * @param queueName
     * @return
     */
    public String qPop(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.qPop(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPop error {} , QueueName:{}", e, queueName);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ɾ��ָ�����ֵĶ��еĶ���Ԫ�أ�������
     * 
     * @param queueName
     * @return
     */
    public <T extends Serializable> T qPopPojo(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.qPopPojo(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPopPojo error {} , QueueName:{}", e, queueName);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ɾ��ָ�����ֵĶ��еĶ�βԪ�أ�������
     * 
     * @param queueName
     * @return
     */
    public String qPopBack(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.qPopBack(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPopBack error {} , QueueName:{}", e, queueName);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    /**
     * ɾ��ָ�����ֵĶ��еĶ�βԪ�أ�������
     * 
     * @param queueName
     * @return
     */
    public <T extends Serializable> T qPopBackPojo(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.qPopBackPojo(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qPopBackPojo error {} , QueueName:{}", e, queueName);
            return null;
        } finally {
            returnMaster(connector);
        }
    }

    public long qSize(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.qSize(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Slaver.qSize error {} , QueueName:{}", e, queueName);
            return 0;
        } finally {
            returnSlaver(connector);
        }
    }

    public long qSizeFromMaster(String queueName) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.qSize(queueName);
        } catch (Exception e) {
            logger.error("SSdbClient Master.qSize error {} , QueueName:{}", e, queueName);
            return 0;
        } finally {
            returnMaster(connector);
        }
    }

    public void zSet(String indexName, String indexKey, long score) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.zSet(indexName, indexKey, score);
        } catch (Exception e) {
            logger.error("SSdbClient Master.zSet error {} , IndexName:{},{}={}", e, indexName, indexKey, score);
        } finally {
            returnMaster(connector);
        }
    }

    public void zDelete(String indexName, String indexKey) {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            connector.zDelete(indexName, indexKey);
        } catch (Exception e) {
            logger.error("SSdbClient Master.zDelete error {} , IndexName:{},{}", e, indexName, indexKey);
        } finally {
            returnMaster(connector);
        }
    }

    public long zGet(String indexName, String indexKey) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.zGet(indexName, indexKey);
        } catch (Exception e) {

        } finally {
            returnSlaver(connector);
        }
        return -1;
    }

    public String[] zScan(String indexName, String indexKey, Long score_start, Long score_end, int limit) {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            List<String> rs = connector.zScan(indexName, indexKey, score_start, score_end, limit);
            return rs.toArray(new String[rs.size()]);
        } catch (Exception e) {

        } finally {
            returnSlaver(connector);
        }
        return null;
    }

    public long zSize(String indexName) {
        SSdbConnection connector = null;
        long count = 0;
        try {
            connector = getSlaver();
            count = connector.zSize(indexName);
        } catch (Exception e) {

        } finally {
            returnSlaver(connector);
        }
        return count;
    }

    public String masterInfo() {
        SSdbConnection connector = null;
        try {
            connector = getMaster();
            return connector.hostInfo();
        } catch (Exception e) {
            logger.error("SSdbClient Master.Info error {}", e);
            return "error";
        } finally {
            returnMaster(connector);
        }
    }

    public String slaverInfo() {
        SSdbConnection connector = null;
        try {
            connector = getSlaver();
            return connector.hostInfo();
        } catch (Exception e) {
            return "error";
        } finally {
            returnSlaver(connector);
        }
    }

    public void showInfo() throws SSdbException {
        System.out.println(masterInfo());
        System.out.println(slaverInfo());
    }

    public SSdbPool getMasterPool() {
        return masterPool;
    }

    public void setMasterPool(SSdbPool masterPool) {
        this.masterPool = masterPool;
        this.runState = State.master;
    }

    public SSdbPool getSlaverPool() {
        return slaverPool;
    }

    public void setSlaverPool(SSdbPool slaverPool) {
        this.slaverPool = slaverPool;

        if (runState == State.master) {
            runState = State.both;
        } else {
            runState = State.slaver;
        }

    }
}
