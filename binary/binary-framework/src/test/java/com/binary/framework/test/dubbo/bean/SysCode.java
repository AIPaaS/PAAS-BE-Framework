package com.binary.framework.test.dubbo.bean;




import com.binary.framework.bean.EntityBean;


/**
 * mapping-table: 系统代码表[SYS_CODE]
 */
public class SysCode implements EntityBean {
	private static final long serialVersionUID = 1L;


	/**
	 * mapping-field: ID[ID]
	 */
	private Long id;


	/**
	 * mapping-field: 代码定义ID[CODE_DEF_ID]
	 */
	private Long codeDefId;


	/**
	 * mapping-field: 代码[CODE]
	 */
	private String code;


	/**
	 * mapping-field: 名称[NAME]
	 */
	private String name;


	/**
	 * mapping-field: 别名[ALIAS]
	 */
	private String alias;


	/**
	 * mapping-field: 英文[EN_NAME]
	 */
	private String enName;


	/**
	 * mapping-field: 拼音码[PINYIN]
	 */
	private String pinyin;


	/**
	 * mapping-field: 上级代码[PARENT_CODE]
	 */
	private String parentCode;


	/**
	 * mapping-field: 图标[ICON]
	 */
	private String icon;


	/**
	 * mapping-field: 颜色[COLOR]
	 */
	private String color;


	/**
	 * mapping-field: 排序[ORDER_NO]
	 */
	private Integer orderNo;


	/**
	 * mapping-field: 备用_1[CUSTOM_1]
	 */
	private String custom1;


	/**
	 * mapping-field: 备用_2[CUSTOM_2]
	 */
	private String custom2;


	/**
	 * mapping-field: 备用_3[CUSTOM_3]
	 */
	private String custom3;


	/**
	 * mapping-field: 备用_4[CUSTOM_4]
	 */
	private Long custom4;


	/**
	 * mapping-field: 备用_5[CUSTOM_5]
	 */
	private Long custom5;


	/**
	 * mapping-field: 备用_6[CUSTOM_6]
	 */
	private Long custom6;


	/**
	 * mapping-field: 有效状态[STATUS]
	 * 1-有效 0-无效
	 */
	private Integer status;


	/**
	 * mapping-field: 数据状态[DATA_STATUS]
	 * 数据状态：1-正常 0-删除
	 */
	private Integer dataStatus;


	/**
	 * mapping-field: 创建时间[CREATE_TIME]
	 * yyyyMMddHHmmss
	 */
	private Long createTime;


	/**
	 * mapping-field: 修改时间[MODIFY_TIME]
	 * yyyyMMddHHmmss
	 */
	private Long modifyTime;




	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Long getCodeDefId() {
		return this.codeDefId;
	}
	public void setCodeDefId(Long codeDefId) {
		this.codeDefId = codeDefId;
	}


	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getEnName() {
		return this.enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}


	public String getPinyin() {
		return this.pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}


	public String getParentCode() {
		return this.parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}


	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	public String getCustom1() {
		return this.custom1;
	}
	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}


	public String getCustom2() {
		return this.custom2;
	}
	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}


	public String getCustom3() {
		return this.custom3;
	}
	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}


	public Long getCustom4() {
		return this.custom4;
	}
	public void setCustom4(Long custom4) {
		this.custom4 = custom4;
	}


	public Long getCustom5() {
		return this.custom5;
	}
	public void setCustom5(Long custom5) {
		this.custom5 = custom5;
	}


	public Long getCustom6() {
		return this.custom6;
	}
	public void setCustom6(Long custom6) {
		this.custom6 = custom6;
	}


	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getDataStatus() {
		return this.dataStatus;
	}
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}


	public Long getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}


	public Long getModifyTime() {
		return this.modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}


}


