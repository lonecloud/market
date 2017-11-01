package cn.lonecloud.market.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 复制类
 * 
 * @Title: BeanCopyUtil.java
 * @Description:
 * @author lonecloud
 */
public class BeanCopyUtil {
	/**
	 * 获取空字符串
	 * 
	 * @Description:
	 * @param source
	 * @return
	 */
	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 拷贝
	 * 
	 * @Description:
	 * @param src
	 * @param target
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	/**
	 * 拷贝
	 * 
	 * @Description:
	 * @param src
	 * @param target 排除属性
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target,String[] exclude){
    	String[] propertyNames = getNullPropertyNames(src);
    	List<String> list=new ArrayList<>();
    	if (propertyNames!=null) {
    		List<String> asList = Arrays.asList(propertyNames);
    		list.addAll(asList);
    	}
		
    	if (exclude!=null) {
			for (String string : exclude) {
				list.add(string);
			}
    	}
    	String[] result = new String[list.size()];
		list.toArray(result);
    	BeanUtils.copyProperties(src, target,result);
    }
}
