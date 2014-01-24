package whoya.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import whoya.whoyaList;
import whoya.whoyaMap;

public class Common {

	/**
	 * Vo형식의 클래스를 whoyaMap형식으로 변환.
	 * @param list
	 * @return
	 */
	public static List<whoyaMap> ConverObjectToWhoyaMap(List<?> list) {
		try {
			List<whoyaMap> resultList = new ArrayList<whoyaMap>();
			
			for ( int i = 0; list != null && i < list.size(); i++ ) {
				Object obj = list.get(i);
				Class<?> superClass = obj.getClass();
				whoyaMap resultMap = new whoyaMap();
				while ( superClass != null ) {
					// Field[] fields = obj.getClass().getFields(); //private field는 나오지
					// 않음.
					Field[] superFields = superClass.getDeclaredFields();
					for ( int j = 0; j < superFields.length; j++ ) {
						superFields[j].setAccessible(true);
						resultMap.put(superFields[j].getName(), superFields[j].get(obj));
					}
					
					superClass = superClass.getSuperclass();
				}
				
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * whoyaMap형식의 클래스를 Vo형식으로 변환.
	 * @param obj
	 * @return
	 */
	public static Object convertWhoyaMapToObject(whoyaMap map, Object objClass){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator<?> itr = map.keySet().iterator();
        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Class<?> superClass = objClass.getClass();
				while ( superClass != null ) {
					Method[] methods = superClass.getDeclaredMethods();
	                for(int i=0;i<=methods.length-1;i++){
	                    if(methodString.equals(methods[i].getName())){
	                        methods[i].invoke(objClass, map.get(keyAttribute));
	                    }
	                }
					
					superClass = superClass.getSuperclass();
				}
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objClass;
    }
}
