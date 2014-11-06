package com.sd.core.common.parse;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;

/**
 * [JSON解析管理类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 * 
 **/
public class JsonMananger {

	private final String tag = JsonMananger.class.getSimpleName();
	
	/** jsonMapper对象，采用jackson解析 **/ 
	private ObjectMapper jsonMapper;
	/** JsonMananger实例对象 **/ 
	private static JsonMananger instance;
	
	/**
	 * 构造方法
	 */
	private JsonMananger() {
		if (jsonMapper == null) {
			jsonMapper = new ObjectMapper();
			jsonMapper.getSerializationConfig().setSerializationInclusion(Inclusion.ALWAYS);  
			jsonMapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
		}
	}

	/**
	 * 得到单例模式JsonMananger对象
	 * @return
	 */
	public static JsonMananger getInstance() {
		if (instance == null) {
			synchronized (JsonMananger.class) {
				if (instance == null) {
					instance = new JsonMananger();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 将json字符串转换成java对象
	 * @param json
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> T jsonToBean(String json, Class<T> cls) throws HttpException {
        try {
        	return jsonMapper.readValue(json, cls);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage());
        }
	}

	/**
	 * 将bean对象转化成json字符串
	 * @param obj
	 * @return
	 * @throws HttpException 
	 */
	public String beanToJson(Object obj) throws HttpException{
		String result = "";
		try {
			result = jsonMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			throw new HttpException(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new HttpException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(e.getMessage());
		}
		NLog.e(tag, "beanToJson: " + result);
		return result;
	}
	
	/**
	 * 得到ObjectMapper解析对象
	 * @return
	 */
	public ObjectMapper getJsonMapper() {
		return jsonMapper;
	}
}
