package com.binary.jdbc.adapter;

import com.binary.jdbc.JdbcType;




/**
 * SQL函数
 */
public interface SqlFunction {
	
	
	/**
	 * 获取所对应的JdbcType
	 * @return
	 */
	public JdbcType getJdbcType();
	
	
	
	/** 数学函数 ***********************************************************************************************/
	
	/**
	 * 1. 绝对值
	 * @param value
	 * @return
	 */
	public String abs(String value);
	
	
	
	
	/**
	 * 2. 取整(大)
	 * @param value
	 * @return
	 */
	public String ceil(String value);
	
	
	
	
	/**
	 * 3. 取整(小)
	 * @param value
	 * @return
	 */
	public String floor(String value);
	
	
	
	
	/**
	 * 4. 取整(截取)
	 * @param value
	 * @return
	 */
	public String trunc(String value);
	
	
	/**
	 * 5. 四舍五入
	 */
	public String round(String value);
	

	
	/**
	 * 6. e为底的幂
	 * @param value
	 * @return
	 */
	public String exp(String value);
	
	
	
	/**
	 * 7. 取e为底的对数
	 * @param value
	 * @return
	 */
	public String log(String value);
	
	
	
	/**
	 * 8. 取10为底对数
	 * @param value
	 * @return
	 */
	public String log10(String value);
	
	
	
	
	/**
	 * 9. 取平方
	 * @return
	 */
	public String square(String value);
	
	
	
	/**
	 * 10. 取平方根
	 * @param value
	 * @return
	 */
	public String sqrt(String value);
	
	
	
	/**
	 * 11. 求任意数为底的幂
	 */
	public String power(String n, String e);
	
	
	
	
	/**
	 * 12. 取随机数
	 * @return
	 */
	public String random();
	
	
	
	
	/**
	 * 13. 取符号
	 * @param value
	 * @return
	 */
	public String sign(String value);
	
	
	
	
	/** 三角函数 *********************************************************************************/
	
	/**
	 * 14. 圆周率
	 * @return
	 */
	public String pi() ;
	
	
	
	/**
	 * 15. 正弦、余弦、正切
	 * @param value
	 * @return
	 */
	public String sin(String value);
	public String cos(String value);
	public String tan(String value);
	
	
	/**
	 * 16. 反正弦、反余弦、反正切
	 * @param value
	 * @return
	 */
	public String asin(String value);
	public String acos(String value);
	public String atan(String value);
	
	
	
	/**
	 * 17. 弧度->角度、角度->弧度
	 */
	public String degrees(String value);
	public String radians(String value);
	
	
	
	
	
	
	/** 字符函数 ************************************************************************************************************/
	
	/**
	 * 20. 处理null值
	 * @param field
	 * @param value
	 * @return
	 */
	public String isNull(String field, String value);
	
	
	
	
	/**
	 * 21. 取ascii码
	 * @param value
	 * @return
	 */
	public String ascii(String value);
	
	
	
	
	/**
	 * 22. 将数值转换成字符
	 * @param value
	 * @return
	 */
	public String character(String value);
	
	
	
	
	/**
	 * 23. 连接
	 * @param vs
	 * @return
	 */
	public String link(String... vs);
	
	
	
	/**
	 * 24. 子串位置
	 * @param parent
	 * @param sub
	 * @param count
	 * @return
	 */
	public String charindex(String parent, String sub, String count);
	
	

	
	/**
	 * 25. 求子串
	 * @param parent
	 * @param start
	 * @param length
	 * @return
	 */
	public String substring(String parent, String start, String length);
	
	
	
	
	/**
	 * 26. 子串替换
	 * @param parent
	 * @param oldsub
	 * @param newsub
	 * @return
	 */
	public String replace(String parent, String oldsub, String newsub);
	
	
	
	
	/**
	 * 27. 长度
	 * @param value
	 * @return
	 */
	public String length(String value);
	
	
	
	
	/**
	 * 28. 大小写转换
	 * @param value
	 * @return
	 */
	public String lower(String value);
	public String upper(String value);
	
	
	
	
	/**
	 * 32. 删除空格
	 * @param value
	 * @return
	 */
	public String ltrim(String value);
	public String rtrim(String value);
	public String trim(String value);
	
	
	
	
	/** 日期函数 ***********************************************************************************************/
	
	
	/**
	 * 35. 系统时间
	 */
	public String getDate();
	
	
	/**
	 * 获取针对当前数据库日期格式
	 */
	public String getFormat(SqlDateFormat format);
	
	
	/**
	 * 将日期转换成字符串
	 * @param datestring
	 * @param format
	 * @return
	 */
	public String toChar(String datestring, SqlDateFormat format);
	public String toDate(String str, SqlDateFormat format);
	
	
	public String decode(String ...vs);

}
