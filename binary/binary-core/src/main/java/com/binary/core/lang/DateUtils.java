package com.binary.core.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.binary.core.exception.CoreException;

@SuppressWarnings({"rawtypes"})
public abstract class DateUtils {
	
	
	public static final int MILLIS_SECOND = 1000;
    public static final int MILLIS_MINUTE = 60 * MILLIS_SECOND;
    public static final int MILLIS_HOUR = 60 * MILLIS_MINUTE;
    public static final int MILLIS_DAY = 24 * MILLIS_HOUR;
	
    public final static int SEMI_MONTH = 1001;
    
    private static final int[][] fields = {
        {Calendar.MILLISECOND},
        {Calendar.SECOND},
        {Calendar.MINUTE},
        {Calendar.HOUR_OF_DAY, Calendar.HOUR},
        {Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM},
        {Calendar.MONTH, 1001},
        {Calendar.YEAR},
        {Calendar.ERA}
    };
    private final static int MODIFY_TRUNCATE = 0;
    private final static int MODIFY_ROUND = 1;
    private final static int MODIFY_CEILING= 2;
        
	
	
	
	/**
	 * 动态解析字段格式
	 * @param value
	 * @return
	 */
	public static String parseFormat(String datestr) {
		boolean hasS = datestr.indexOf(".") > 0;
		if(hasS) datestr = datestr.substring(0,datestr.indexOf("."));
		int a = StringUtils.getFrequence(datestr, ':');
		int b = StringUtils.getFrequence(datestr, '-');
		String format = "";
		if(b == 0) {
			if(datestr.indexOf(" ")>0) {
				if(a==0) {
					format = "dd HH";
				}else if(a==1) {
					format = "dd HH:mm";
				}else if(a==2) {
					format = "dd HH:mm:ss";
				}
			}else {
				if(a==0) {
					format = datestr.length()==0 ? null : "HH"; 
				}else if(a==1) {
					format = "HH:mm";
				}else if(a==2) {
					format = "HH:mm:ss";
				}
			}
		}else if(b==1) {
			if(a==0) {
				format = datestr.indexOf(" ")>0&&datestr.substring(datestr.indexOf(" ")+1).length()>0 ? "MM-dd HH" : "yyyy-MM"; 
			}else if(a==1) {
				format = "MM-dd HH:mm";
			}else if(a==2) {
				format = "MM-dd HH:mm:ss";
			}
		}else if(b==2) {
			format = "yyyy-MM-dd";
			if(a==0) {
				if(datestr.indexOf(" ")>0&&datestr.substring(datestr.indexOf(" ")+1).length()>0)format+=" HH";
			}else if(a==1) {
				format += " HH:mm";
			}else if(a==2) {
				format += " HH:mm:ss";
			}
		}
		if(hasS) format += ".S";
		return format;
	}
	
	
	public static int getDaysInMonth(int year, int month) {
		if(month<1 || month>12) throw new CoreException(" is wrong month="+month);
		switch (month) {
			case 1: 
			case 3: 
			case 5: 
			case 7: 
			case 8: 
			case 10: 
			case 12: return 31;
			case 4: 
			case 6: 
			case 9: 
			case 11: return 30;
			case 2: return ((year%4==0 && year%100!=0) || (year%400==0)) ? 29 : 28;
			default: return -1;
		}
	}
	
	public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }
	
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }
    
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    
    
    public static Date setYears(Date date, int amount) {
        return set(date, Calendar.YEAR, amount);
    }

    
    public static Date setMonths(Date date, int amount) {
        return set(date, Calendar.MONTH, amount);
    }

    
    public static Date setDays(Date date, int amount) {
        return set(date, Calendar.DAY_OF_MONTH, amount);
    }

    
    public static Date setHours(Date date, int amount) {
        return set(date, Calendar.HOUR_OF_DAY, amount);
    }

    
    public static Date setMinutes(Date date, int amount) {
        return set(date, Calendar.MINUTE, amount);
    }
    
    
    public static Date setSeconds(Date date, int amount) {
        return set(date, Calendar.SECOND, amount);
    }

    
    public static Date setMilliseconds(Date date, int amount) {
        return set(date, Calendar.MILLISECOND, amount);
    } 
    
    
    public static Date set(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(calendarField, amount);
        return c.getTime();
    }   
	
    public static Date round(Date date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_ROUND);
        return gval.getTime();
    }

    public static Calendar round(Calendar date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar rounded = (Calendar) date.clone();
        modify(rounded, field, MODIFY_ROUND);
        return rounded;
    }

    public static Date round(Object date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        if (date instanceof Date) {
            return round((Date) date, field);
        } else if (date instanceof Calendar) {
            return round((Calendar) date, field).getTime();
        } else {
            throw new CoreException("Could not round " + date);
        }
    }

    public static Date truncate(Date date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_TRUNCATE);
        return gval.getTime();
    }

    public static Calendar truncate(Calendar date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar truncated = (Calendar) date.clone();
        modify(truncated, field, MODIFY_TRUNCATE);
        return truncated;
    }

    public static Date truncate(Object date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        if (date instanceof Date) {
            return truncate((Date) date, field);
        } else if (date instanceof Calendar) {
            return truncate((Calendar) date, field).getTime();
        } else {
            throw new CoreException("Could not truncate " + date);
        }
    }
    
    public static Date ceiling(Date date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_CEILING);
        return gval.getTime();
    }

    public static Calendar ceiling(Calendar date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        Calendar ceiled = (Calendar) date.clone();
        modify(ceiled, field, MODIFY_CEILING);
        return ceiled;
    }

    public static Date ceiling(Object date, int field) {
        if (date == null) {
            throw new CoreException("The date must not be null");
        }
        if (date instanceof Date) {
            return ceiling((Date) date, field);
        } else if (date instanceof Calendar) {
            return ceiling((Calendar) date, field).getTime();
        } else {
            throw new CoreException("Could not find ceiling of for type: " + date.getClass());
        }
    }

    private static void modify(Calendar val, int field, int modType) {
        if (val.get(Calendar.YEAR) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        
        if (field == Calendar.MILLISECOND) {
            return;
        }
        Date date = val.getTime();
        long time = date.getTime();
        boolean done = false;
        int millisecs = val.get(Calendar.MILLISECOND);
        if (MODIFY_TRUNCATE == modType || millisecs < 500) {
            time = time - millisecs;
        }
        if (field == Calendar.SECOND) {
            done = true;
        }
        int seconds = val.get(Calendar.SECOND);
        if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
            time = time - (seconds * 1000L);
        }
        if (field == Calendar.MINUTE) {
            done = true;
        }
        int minutes = val.get(Calendar.MINUTE);
        if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
            time = time - (minutes * 60000L);
        }
        if (date.getTime() != time) {
            date.setTime(time);
            val.setTime(date);
        }
        boolean roundUp = false;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j] == field) {
                    if (modType == MODIFY_CEILING || (modType == MODIFY_ROUND && roundUp)) {
                        if (field == DateUtils.SEMI_MONTH) {
                            if (val.get(Calendar.DATE) == 1) {
                                val.add(Calendar.DATE, 15);
                            } else {
                                val.add(Calendar.DATE, -15);
                                val.add(Calendar.MONTH, 1);
                            }
                        } else if (field == Calendar.AM_PM) {
                            if (val.get(Calendar.HOUR_OF_DAY) == 0) {
                                val.add(Calendar.HOUR_OF_DAY, 12);
                            } else {
                                val.add(Calendar.HOUR_OF_DAY, -12);
                                val.add(Calendar.DATE, 1);
                            }
                        } else {
                            val.add(fields[i][0], 1);
                        }
                    }
                    return;
                }
            }
            int offset = 0;
            boolean offsetSet = false;
            switch (field) {
                case DateUtils.SEMI_MONTH:
                    if (fields[i][0] == Calendar.DATE) {
                        offset = val.get(Calendar.DATE) - 1;
                        if (offset >= 15) {
                            offset -= 15;
                        }
                        roundUp = offset > 7;
                        offsetSet = true;
                    }
                    break;
                case Calendar.AM_PM:
                    if (fields[i][0] == Calendar.HOUR_OF_DAY) {
                        offset = val.get(Calendar.HOUR_OF_DAY);
                        if (offset >= 12) {
                            offset -= 12;
                        }
                        roundUp = offset >= 6;
                        offsetSet = true;
                    }
                    break;
            }
            if (!offsetSet) {
                int min = val.getActualMinimum(fields[i][0]);
                int max = val.getActualMaximum(fields[i][0]);
                offset = val.get(fields[i][0]) - min;
                roundUp = offset > ((max - min) / 2);
            }
            if (offset != 0) {
                val.set(fields[i][0], val.get(fields[i][0]) - offset);
            }
        }
        throw new CoreException("The field " + field + " is not supported");
    }
	
	
	
	public static String toString(Calendar c) {
		return toString(c, null);
	}
	public static String toString(Calendar c, String format) {
		return toString(c.getTime(), format);
	}
	public static String toString(java.util.Date d) {
		return toString(d, null);
	}
	public static String toString(java.util.Date d, String format) {
		if(d==null) return null;
		if(format==null) {
			if(d instanceof Timestamp) {
				format="yyyy-MM-dd HH:mm:ss.S";
			}else {
				format="yyyy-MM-dd HH:mm:ss";
			}
		}
		return new SimpleDateFormat(format).format(d);
	}
	
	
	public static Integer toInteger(Date v) {
		return v==null ? null : (int)v.getTime();
	}
	
	public static Long toLong(Date v) {
		return v==null ? null : v.getTime();
	}
	
	public static Short toShort(Date v) {
		return v==null ? null : (short)v.getTime();
	}
	
	public static Byte toByte(Date v) {
		return v==null ? null : (byte)v.getTime();
	}
	
	
	public static Double toDouble(Date v) {
		return v==null ? null : (double)v.getTime();
	}
	
	
	public static Float toFloat(Date v) {
		return v==null ? null : (float)v.getTime();
	}
	
	public static Character toCharacter(Date v) {
		return null;
	}
	
	public static Boolean toBoolean(Date v) {
		return v != null;
	}
	
	public static BigInteger toBigInteger(Date v) {
		return v==null ? null : new BigInteger(String.valueOf(v.getTime()));
	}
	
	public static BigDecimal toBigDecimal(Date v) {
		return v==null ? null : new BigDecimal(String.valueOf(v.getTime()));
	}
	
		
	public static java.sql.Date toSqlDate(Date v) {
		return v==null ? null : new java.sql.Date(v.getTime());
	}
	
	
	public static Time toTime(Date v) {
		return v==null ? null : new java.sql.Time(v.getTime());
	}
	
	
	public static Timestamp toTimestamp(Date v) {
		return v==null ? null : new java.sql.Timestamp(v.getTime());
	}
	
	
	public static Calendar toCalendar(Date v) {
		if(v==null) return null;
		Calendar c = Calendar.getInstance();
		c.setTime(v);
		return c;
	}
	
	
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, Date v, String format) {
		return v==null ? null : EnumUtils.valueOf(enumType, Conver.to(v, String.class, format));
	}
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, Calendar v, String format) {
		return v==null ? null : EnumUtils.valueOf(enumType, Conver.to(v, String.class, format));
	}
	
	
	public static Object toAny(Date v, Class toclass) {
		return toAny(v, toclass, null);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Object toAny(Date v, Class toclass, String format) {
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
			return toString(v, format);
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return v;
		}else if(toclass==java.sql.Date.class) {
			return toSqlDate(v);
		}else if(toclass==java.sql.Time.class) {
			return toTime(v);
		}else if(toclass==java.sql.Timestamp.class) {
			return toTimestamp(v);
		}else if(Calendar.class.isAssignableFrom(toclass)) {
			return toCalendar(v);
		}else if(Enum.class.isAssignableFrom(toclass)) {
			return toEnum(toclass, v, format);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	
	public static Integer toInteger(Calendar v) {
		return v==null ? null : (int)v.getTimeInMillis();
	}
	
	public static Long toLong(Calendar v) {
		return v==null ? null : v.getTimeInMillis();
	}
	
	public static Short toShort(Calendar v) {
		return v==null ? null : (short)v.getTimeInMillis();
	}
	
	public static Byte toByte(Calendar v) {
		return v==null ? null : (byte)v.getTimeInMillis();
	}
	
	
	public static Double toDouble(Calendar v) {
		return v==null ? null : (double)v.getTimeInMillis();
	}
	
	
	public static Float toFloat(Calendar v) {
		return v==null ? null : (float)v.getTimeInMillis();
	}
	
	public static Character toCharacter(Calendar v) {
		return null;
	}
	
	public static Boolean toBoolean(Calendar v) {
		return v != null;
	}
	
	public static BigInteger toBigInteger(Calendar v) {
		return v==null ? null : new BigInteger(String.valueOf(v.getTime()));
	}
	
	public static BigDecimal toBigDecimal(Calendar v) {
		return v==null ? null : new BigDecimal(String.valueOf(v.getTime()));
	}
	
	public static java.util.Date toDate(Calendar v) {
		return v==null ? null : v.getTime();
	}
	
	
	public static java.sql.Date toSqlDate(Calendar v) {
		return v==null ? null : new java.sql.Date(v.getTimeInMillis());
	}
	
	
	public static Time toTime(Calendar v) {
		return v==null ? null : new java.sql.Time(v.getTimeInMillis());
	}
	
	
	public static Timestamp toTimestamp(Calendar v) {
		return v==null ? null : new java.sql.Timestamp(v.getTimeInMillis());
	}
	
	
	public static Object toAny(Calendar v, Class toclass) {
		return toAny(v, toclass, null);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Object toAny(Calendar v, Class toclass, String format) {
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
			return toString(v, format);
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return toDate(v);
		}else if(toclass==java.sql.Date.class) {
			return toSqlDate(v);
		}else if(toclass==java.sql.Time.class) {
			return toTime(v);
		}else if(toclass==java.sql.Timestamp.class) {
			return toTimestamp(v);
		}else if(Calendar.class.isAssignableFrom(toclass)) {
			return v;
		}else if(Enum.class.isAssignableFrom(toclass)) {
			return toEnum(toclass, v, format);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	
	
}




