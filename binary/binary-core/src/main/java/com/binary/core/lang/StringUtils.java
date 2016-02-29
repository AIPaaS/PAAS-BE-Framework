package com.binary.core.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.binary.core.exception.CoreException;

@SuppressWarnings({"rawtypes"})
public abstract class StringUtils {
	
	
	public static final String EMPTY = "";
	
	public static final int PAD_LIMIT = 8192;
	
	
		
	/**
	 * 判断是否是空字符串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
	
	/**
	 * 判断字符是否是空白字符串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	
	/**
	 * 相当于trim，如果为null则返回""
	 * @param str
	 * @return
	 */
	public static String clean(String str) {
        return str == null ? EMPTY : str.trim();
    }
	
	
	/**
	 * trim，如果为null则返回null
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
        return str == null ? null : str.trim();
    }
	
	
	/**
	 * 相当于equals, 如果两者都为null则返回true
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
	
	
	/**
	 * 相当于equalsIgnoreCase, 如果两者都为null则返回true
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
	
	
	/**
	 * 忽略大小写查找字符位置
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int indexOfIgnoreCase(String str, String searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }
	
	
	/**
	 * 忽略大小写查找字符位置
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return -1;
    }
	
	
	/**
	 * 忽略大小写查找字符位置
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int lastIndexOfIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(str, searchStr, str.length());
    }
	
	
	/**
	 * 忽略大小写查找字符位置
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public static int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos > (str.length() - searchStr.length())) {
            startPos = str.length() - searchStr.length();
        }
        if (startPos < 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }

        for (int i = startPos; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return -1;
    }
	
	
	public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }
	
	
	public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }
	
	
	public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }
	
	
	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }
	
	public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }
	
	public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(padding(pads, padChar));
    }
	
	
	public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }
	
	
	public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }
	
	public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }
	
	
	public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }
	
	public static int length(String str) {
        return str == null ? 0 : str.length();
    }
	
	public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }
	
	public static String upperCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(locale);
    }
	
	public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }
	
	
	public static String lowerCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(locale);
    }
	
	
	public static boolean isNumber(String str) {
        if (isEmpty(str)) return false;
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * 计算subString在parentString出现的次数
	 */
	public static int getFrequence(String str, char c) {
		if(str == null) return 0;
		int count = 0;
		int len = str.length();
		for(int i=0; i<len; i++) {
			if(str.charAt(i) == c) count ++ ;
		}
		return count;
	}
	
	
	public static Integer toInteger(String str) {
		return str==null||(str=str.trim()).length()==0 ? null : Integer.valueOf(str);
	}
	
	public static Long toLong(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  Long.valueOf(str);
	}
	
	public static Short toShort(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  Short.valueOf(str);
	}
	
	public static Byte toByte(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  Byte.valueOf(str);
	}
	
	
	public static Double toDouble(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  Double.valueOf(str);
	}
	
	
	public static Float toFloat(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  Float.valueOf(str);
	}
	
	public static Character toCharacter(String str) {
		return isEmpty(str) ? null : Character.valueOf(str.charAt(0));
	}
	
	public static Boolean toBoolean(String str) {
		if(str==null) return null;
		boolean ba = str!=null && (str=str.trim()).length()>0 && !str.equals("0") && !str.equals("F") && !str.equalsIgnoreCase("false");
		return Boolean.valueOf(ba);
	}
	
	
	public static BigInteger toBigInteger(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  new BigInteger(str);
	}
	
	
	public static BigDecimal toBigDecimal(String str) {
		return str==null||(str=str.trim()).length()==0 ? null :  new BigDecimal(str);
	}
	
	public static java.util.Date toDate(String str) {
		return toDate(str, null);
	}
	
	public static java.util.Date toDate(String str, String format) {
		if(isEmpty(str)) return null;
		if(format == null) format = DateUtils.parseFormat(str);
		try {
			java.util.Date d = new SimpleDateFormat(format).parse(str);
			return d;
		}catch(ParseException e) {
			throw new CoreException(e);
		}
	}
	
	public static java.sql.Date toSqlDate(String str) {
		return toSqlDate(str, null);
	}
	
	public static java.sql.Date toSqlDate(String str, String format) {
		Date date = toDate(str, format);
		if(date == null) return null;
		return new java.sql.Date(date.getTime());
	}
	
	public static Time toTime(String str) {
		return toTime(str, null);
	}
	
	public static Time toTime(String str, String format) {
		Date date = toDate(str, format);
		if(date == null) return null;
		return new java.sql.Time(date.getTime());
	}
	
	public static Timestamp toTimestamp(String str) {
		return toTimestamp(str, null);
	}
	
	public static Timestamp toTimestamp(String str, String format) {
		Date date = toDate(str, format);
		if(date == null) return null;
		return new java.sql.Timestamp(date.getTime());
	}
	
	public static Calendar toCalendar(String str) {
		return toCalendar(str, null);
	}
	
	public static Calendar toCalendar(String str, String format) {
		java.util.Date d = toDate(str, format);
		if(d == null) return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}
	
	
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, String v) {
		return v==null ? null : EnumUtils.valueOf(enumType, v);
	}
	
	
	public static Object toAny(String str, Class toclass) {
		return toAny(str, null, toclass);
	}
	
	@SuppressWarnings("unchecked")
	public static Object toAny(String v, String format, Class toclass) {
		if(v==null || toclass==null) return null;
		if(toclass==int.class || toclass==Integer.class) {
			return toInteger(v);
		}else if(toclass==long.class || toclass==Long.class) {
			return toLong(v);
		}else if(toclass==short.class || toclass==Short.class) {
			return toShort(v);
		}else if(toclass==byte.class || toclass==Byte.class) {
			return toByte(v);
		}else if(toclass==double.class || toclass==Double.class) {
			return toDouble(v);
		}else if(toclass==float.class || toclass==Float.class) {
			return toFloat(v);
		}else if(toclass==char.class || toclass==Character.class) {
			return toCharacter(v);
		}else if(toclass==boolean.class || toclass==Boolean.class) {
			return toBoolean(v);
		}else if(toclass==String.class) {
			return v;
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return toDate(v, format);
		}else if(toclass==java.sql.Date.class) {
			return toSqlDate(v, format);
		}else if(toclass==java.sql.Time.class) {
			return toTime(v, format);
		}else if(toclass==java.sql.Timestamp.class) {
			return toTimestamp(v, format);
		}else if(Calendar.class.isAssignableFrom(toclass)) {
			return toCalendar(v, format);
		}else if(Enum.class.isAssignableFrom(toclass)) {
			return toEnum(toclass, v);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	public static StringLinker parseExpression(String exp) {
		return parseExpression(exp, true);
	}
	
	public static StringLinker parseExpression(String exp, boolean validate) {
		return parseExpression(exp, null, null, true);
	}
	
	/**
	 * 解析类似带${}参数表达式
	 * @param exp: 表达式
	 * @param lregex: 左'括号', 缺省为：${
	 * @param r: 右'括号', 缺省为：}
	 * @param validate: 是否验证左右括号对称性, 缺省为true
	 * @return 
	 */
	public static StringLinker parseExpression(String exp, String lregex, String r, boolean validate) {
		if(isEmpty(lregex)) lregex = "[$][\\s]*[{]";
		if(isEmpty(r)) r = "}";
		String[] subs = exp.split(lregex);
		String[] array = new String[subs.length];
		String[] keys = new String[subs.length-1];
		for(int i=0; i<subs.length; i++) {
			if(i == 0) {
				array[0] = subs[0];
				continue ;
			}
			int index = subs[i].indexOf(r);
			if(validate && index<0) throw new CoreException(" is not fond "+lregex+" paired sign:'"+r+"'!");
			keys[i-1] = subs[i].substring(0,index).trim();
			array[i] = subs[i].substring(index+r.length());
		}
		return new StringLinker(array, keys);
	}
	
	/**
	 * 获取字串sub在parent中出现的次数
	 * @param parent
	 * @param sub
	 * @return
	 */
	public static int getFrequence(String parent, String sub) {
		if(sub==null || sub.length()==0) throw new CoreException(" the sub is empty argument! ");
		int count = 0;
		int index = 0;
		for(int i=0; ; i++) {
			if(i == 0) {
				index = parent.indexOf(sub);
			}else {
				index = parent.indexOf(sub, index+sub.length());
			}
			if(index == -1) break;
			count ++ ;
		}
		return count;
	}
	
	/**
	 * 提取被leftLimit、rightLimit所包住的字符串
	 * @param str
	 * @param leftLimit
	 * @param rightLimit
	 * @return
	 */
	public static StringMap parseOutlimit(String str, String leftLimit, String rightLimit) {
		if(leftLimit==null || leftLimit.length()==0) throw new CoreException(" is null parseOutlimit.leftLimit! ");
		if(rightLimit==null || rightLimit.length()==0) throw new CoreException(" is null parseOutlimit.rightLimit! ");
		if(leftLimit.equals(rightLimit)) return parseOutlimit(str, leftLimit);
		String s = "";
		int lc = getFrequence(str, leftLimit);
		int rc = getFrequence(str, rightLimit);
		if(lc != rc) throw new CoreException(" leftLimit:'"+leftLimit+"'.count != rightLimit:'"+rightLimit+"'.count by String:'"+str+"' ! ");
		int li = -1;
		int ri = -1;
		List<String> arrayList = new ArrayList<String>();
		int leftcount = 0;
		while((li=str.indexOf(leftLimit)) > -1) {
			leftcount ++ ;
			s += str.substring(0, li+leftLimit.length())+rightLimit;
			str = str.substring(li+leftLimit.length());
			String key = "";
			while(leftcount > 0) {
				ri = str.indexOf(rightLimit);
				if((li=str.indexOf(leftLimit))>-1 && li<ri) {
					key += str.substring(0, li+leftLimit.length());
					str = str.substring(li+leftLimit.length());
					leftcount ++ ;
				}else {
					key += str.substring(0, str.indexOf(rightLimit));
					str = str.substring(ri+rightLimit.length());
					leftcount -- ;
					if(leftcount > 0) key += rightLimit;
				}
			}
			arrayList.add(key);
		}
		s += str;
		String[] array = new String[arrayList.size()];
		arrayList.toArray(array);
		return new StringMap(s, array);
	}
	
	
	
	/**
	 * 提取被leftLimit、rightLimit所包住的字符串
	 * @param str
	 * @param limit
	 * @return
	 */
	public static StringMap parseOutlimit(String str, String limit) {
		if(limit==null || limit.length()==0) throw new CoreException(" is null parseOutlimit.limit! ");
		String s = "";
		int lc = getFrequence(str, limit);
		if(lc % 2 != 0) throw new CoreException(" limit:'"+limit+"'.count is not even by String:'"+str+"' ! ");
		int index = -1;
		List<String> arrayList = new ArrayList<String>();
		while((index=str.indexOf(limit)) > -1) {
			s += str.substring(0, index+limit.length())+limit;
			str = str.substring(index+limit.length());
			String key = str.substring(0, str.indexOf(limit));
			arrayList.add(key);
			str = str.substring(str.indexOf(limit)+limit.length());
		}
		s += str;
		String[] array = new String[arrayList.size()];
		arrayList.toArray(array);
		return new StringMap(s, array);
	}
	
	
	
	/**
	 * 将被parseOutlimit解析出来的子串再返回去
	 * @param sm
	 * @param limit
	 * @return
	 */
	public static String fillOutLimit(StringMap sm, String limit) {
		return fillOutLimit(sm.getString(), sm.getKeys(), limit, limit);
	}
	
	
	
	/**
	 * 将被parseOutlimit解析出来的子串再返回去
	 * @param str
	 * @param subs
	 * @param limit
	 * @return
	 */
	public static String fillOutLimit(String str, String[] subs, String limit) {
		return fillOutLimit(str, subs, limit, limit);
	}
	
	
	
	/**
	 * 将被parseOutlimit解析出来的子串再返回去
	 * @param sm
	 * @param limit
	 * @return
	 */
	public static String fillOutLimit(StringMap sm, String leftLimit, String rightLimit) {
		return fillOutLimit(sm.getString(), sm.getKeys(), leftLimit, rightLimit);
	}
	
	
	
	/**
	 * 将被parseOutlimit解析出来的子串再返回去
	 * @param str
	 * @param subs
	 * @param limit
	 * @return
	 */
	public static String fillOutLimit(String str, String[] subs, String leftLimit, String rightLimit) {
		if(leftLimit==null || leftLimit.length()==0) throw new CoreException(" is null fillOutLimit.leftLimit! ");
		if(rightLimit==null || rightLimit.length()==0) throw new CoreException(" is null fillOutLimit.rightLimit! ");
		int lc = getFrequence(str, leftLimit);
		int ks = leftLimit.equals(rightLimit) ? subs.length*2 : subs.length;
		if(lc != ks) throw new CoreException(" the limit.count != subs.length ! ");
		String parent = "";
		int index = -1;
		for(int i=0; (index=str.indexOf(leftLimit))>-1; i++) {
			parent += str.substring(0, index+leftLimit.length())+subs[i]+rightLimit;
			str = str.substring(index+leftLimit.length());
			str = str.substring(str.indexOf(rightLimit)+rightLimit.length());
		}
		parent += str;
		return parent;
	}
	
	
	
	
	
}






