package cn.lynu.lyq.signin.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ognl.DefaultTypeConverter;

public class DateTypeConverter extends DefaultTypeConverter {

	@Override
	@SuppressWarnings("rawtypes")
	public Object convertValue(Map context, Object value, Class toType) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (toType == Date.class) {// 当字符串向Date类型转换时
				// These codes are functional in Struts 2.0.11
//				String[] params = (String[]) value;// request.getParameterValues()
//				return dateFormat.parse(params[0]);
				
				// lyq modified for Struts 2.5.5 (actually value is a String) 
				if(value.getClass().isArray()){
					String[] params = (String[]) value;
					return dateFormat.parse(params[0]);
				}else{
					return dateFormat.parse((String)value);
				}
				//lyq modified end
				
			} else if (toType == String.class) {// 当Date转换成字符串时
				Date date = (Date) value;
				return dateFormat.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
