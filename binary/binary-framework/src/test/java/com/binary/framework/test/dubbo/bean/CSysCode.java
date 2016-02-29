package com.binary.framework.test.dubbo.bean;




import com.binary.framework.bean.Condition;


/**
 * condition-table: 系统代码表[SYS_CODE]
 */
public class CSysCode implements Condition {
	private static final long serialVersionUID = 1L;


	/**
	 * condition-field: ID[ID] operate-Equal[=]
	 */
	private Long id;


	/**
	 * condition-field: ID[ID] operate-In[in]
	 */
	private Long[] ids;


	/**
	 * condition-field: ID[ID] operate-GTEqual[>=]
	 */
	private Long startId;

	/**
	 * condition-field: ID[ID] operate-LTEqual[<=]
	 */
	private Long endId;


	/**
	 * condition-field: 代码定义ID[CODE_DEF_ID] operate-Equal[=]
	 */
	private Long codeDefId;


	/**
	 * condition-field: 代码定义ID[CODE_DEF_ID] operate-In[in]
	 */
	private Long[] codeDefIds;


	/**
	 * condition-field: 代码定义ID[CODE_DEF_ID] operate-GTEqual[>=]
	 */
	private Long startCodeDefId;

	/**
	 * condition-field: 代码定义ID[CODE_DEF_ID] operate-LTEqual[<=]
	 */
	private Long endCodeDefId;


	/**
	 * condition-field: 代码[CODE] operate-Like[like]
	 */
	private String code;


	/**
	 * condition-field: 代码[CODE] operate-Equal[=]
	 */
	private String codeEqual;


	/**
	 * condition-field: 代码[CODE] operate-In[in]
	 */
	private String[] codes;


	/**
	 * condition-field: 名称[NAME] operate-Like[like]
	 */
	private String name;


	/**
	 * condition-field: 名称[NAME] operate-Equal[=]
	 */
	private String nameEqual;


	/**
	 * condition-field: 名称[NAME] operate-In[in]
	 */
	private String[] names;


	/**
	 * condition-field: 别名[ALIAS] operate-Like[like]
	 */
	private String alias;


	/**
	 * condition-field: 别名[ALIAS] operate-Equal[=]
	 */
	private String aliasEqual;


	/**
	 * condition-field: 别名[ALIAS] operate-In[in]
	 */
	private String[] aliass;


	/**
	 * condition-field: 英文[EN_NAME] operate-Like[like]
	 */
	private String enName;


	/**
	 * condition-field: 英文[EN_NAME] operate-Equal[=]
	 */
	private String enNameEqual;


	/**
	 * condition-field: 英文[EN_NAME] operate-In[in]
	 */
	private String[] enNames;


	/**
	 * condition-field: 拼音码[PINYIN] operate-Like[like]
	 */
	private String pinyin;


	/**
	 * condition-field: 拼音码[PINYIN] operate-Equal[=]
	 */
	private String pinyinEqual;


	/**
	 * condition-field: 拼音码[PINYIN] operate-In[in]
	 */
	private String[] pinyins;


	/**
	 * condition-field: 上级代码[PARENT_CODE] operate-Like[like]
	 */
	private String parentCode;


	/**
	 * condition-field: 上级代码[PARENT_CODE] operate-Equal[=]
	 */
	private String parentCodeEqual;


	/**
	 * condition-field: 上级代码[PARENT_CODE] operate-In[in]
	 */
	private String[] parentCodes;


	/**
	 * condition-field: 图标[ICON] operate-Like[like]
	 */
	private String icon;


	/**
	 * condition-field: 图标[ICON] operate-Equal[=]
	 */
	private String iconEqual;


	/**
	 * condition-field: 图标[ICON] operate-In[in]
	 */
	private String[] icons;


	/**
	 * condition-field: 颜色[COLOR] operate-Like[like]
	 */
	private String color;


	/**
	 * condition-field: 颜色[COLOR] operate-Equal[=]
	 */
	private String colorEqual;


	/**
	 * condition-field: 颜色[COLOR] operate-In[in]
	 */
	private String[] colors;


	/**
	 * condition-field: 排序[ORDER_NO] operate-Equal[=]
	 */
	private Integer orderNo;


	/**
	 * condition-field: 排序[ORDER_NO] operate-In[in]
	 */
	private Integer[] orderNos;


	/**
	 * condition-field: 排序[ORDER_NO] operate-GTEqual[>=]
	 */
	private Integer startOrderNo;

	/**
	 * condition-field: 排序[ORDER_NO] operate-LTEqual[<=]
	 */
	private Integer endOrderNo;


	/**
	 * condition-field: 备用_1[CUSTOM_1] operate-Like[like]
	 */
	private String custom1;


	/**
	 * condition-field: 备用_2[CUSTOM_2] operate-Like[like]
	 */
	private String custom2;


	/**
	 * condition-field: 备用_3[CUSTOM_3] operate-Like[like]
	 */
	private String custom3;


	/**
	 * condition-field: 备用_4[CUSTOM_4] operate-Equal[=]
	 */
	private Long custom4;


	/**
	 * condition-field: 备用_4[CUSTOM_4] operate-In[in]
	 */
	private Long[] custom4s;


	/**
	 * condition-field: 备用_4[CUSTOM_4] operate-GTEqual[>=]
	 */
	private Long startCustom4;

	/**
	 * condition-field: 备用_4[CUSTOM_4] operate-LTEqual[<=]
	 */
	private Long endCustom4;


	/**
	 * condition-field: 备用_5[CUSTOM_5] operate-Equal[=]
	 */
	private Long custom5;


	/**
	 * condition-field: 备用_5[CUSTOM_5] operate-In[in]
	 */
	private Long[] custom5s;


	/**
	 * condition-field: 备用_5[CUSTOM_5] operate-GTEqual[>=]
	 */
	private Long startCustom5;

	/**
	 * condition-field: 备用_5[CUSTOM_5] operate-LTEqual[<=]
	 */
	private Long endCustom5;


	/**
	 * condition-field: 备用_6[CUSTOM_6] operate-Equal[=]
	 */
	private Long custom6;


	/**
	 * condition-field: 备用_6[CUSTOM_6] operate-In[in]
	 */
	private Long[] custom6s;


	/**
	 * condition-field: 备用_6[CUSTOM_6] operate-GTEqual[>=]
	 */
	private Long startCustom6;

	/**
	 * condition-field: 备用_6[CUSTOM_6] operate-LTEqual[<=]
	 */
	private Long endCustom6;


	/**
	 * condition-field: 有效状态[STATUS] operate-Equal[=]
	 * 1-有效 0-无效
	 */
	private Integer status;


	/**
	 * condition-field: 有效状态[STATUS] operate-In[in]
	 * 1-有效 0-无效
	 */
	private Integer[] statuss;


	/**
	 * condition-field: 有效状态[STATUS] operate-GTEqual[>=]
	 * 1-有效 0-无效
	 */
	private Integer startStatus;

	/**
	 * condition-field: 有效状态[STATUS] operate-LTEqual[<=]
	 * 1-有效 0-无效
	 */
	private Integer endStatus;


	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-Equal[=]
	 * 数据状态：1-正常 0-删除
	 */
	private Integer dataStatus;


	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-In[in]
	 * 数据状态：1-正常 0-删除
	 */
	private Integer[] dataStatuss;


	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-GTEqual[>=]
	 * 数据状态：1-正常 0-删除
	 */
	private Integer startDataStatus;

	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-LTEqual[<=]
	 * 数据状态：1-正常 0-删除
	 */
	private Integer endDataStatus;


	/**
	 * condition-field: 创建时间[CREATE_TIME] operate-Equal[=]
	 * yyyyMMddHHmmss
	 */
	private Long createTime;


	/**
	 * condition-field: 创建时间[CREATE_TIME] operate-In[in]
	 * yyyyMMddHHmmss
	 */
	private Long[] createTimes;


	/**
	 * condition-field: 创建时间[CREATE_TIME] operate-GTEqual[>=]
	 * yyyyMMddHHmmss
	 */
	private Long startCreateTime;

	/**
	 * condition-field: 创建时间[CREATE_TIME] operate-LTEqual[<=]
	 * yyyyMMddHHmmss
	 */
	private Long endCreateTime;


	/**
	 * condition-field: 修改时间[MODIFY_TIME] operate-Equal[=]
	 * yyyyMMddHHmmss
	 */
	private Long modifyTime;


	/**
	 * condition-field: 修改时间[MODIFY_TIME] operate-In[in]
	 * yyyyMMddHHmmss
	 */
	private Long[] modifyTimes;


	/**
	 * condition-field: 修改时间[MODIFY_TIME] operate-GTEqual[>=]
	 * yyyyMMddHHmmss
	 */
	private Long startModifyTime;

	/**
	 * condition-field: 修改时间[MODIFY_TIME] operate-LTEqual[<=]
	 * yyyyMMddHHmmss
	 */
	private Long endModifyTime;




	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Long[] getIds() {
		return this.ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	public Long getStartId() {
		return this.startId;
	}
	public void setStartId(Long startId) {
		this.startId = startId;
	}


	public Long getEndId() {
		return this.endId;
	}
	public void setEndId(Long endId) {
		this.endId = endId;
	}


	public Long getCodeDefId() {
		return this.codeDefId;
	}
	public void setCodeDefId(Long codeDefId) {
		this.codeDefId = codeDefId;
	}


	public Long[] getCodeDefIds() {
		return this.codeDefIds;
	}
	public void setCodeDefIds(Long[] codeDefIds) {
		this.codeDefIds = codeDefIds;
	}


	public Long getStartCodeDefId() {
		return this.startCodeDefId;
	}
	public void setStartCodeDefId(Long startCodeDefId) {
		this.startCodeDefId = startCodeDefId;
	}


	public Long getEndCodeDefId() {
		return this.endCodeDefId;
	}
	public void setEndCodeDefId(Long endCodeDefId) {
		this.endCodeDefId = endCodeDefId;
	}


	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}


	public String getCodeEqual() {
		return this.codeEqual;
	}
	public void setCodeEqual(String codeEqual) {
		this.codeEqual = codeEqual;
	}


	public String[] getCodes() {
		return this.codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}


	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getNameEqual() {
		return this.nameEqual;
	}
	public void setNameEqual(String nameEqual) {
		this.nameEqual = nameEqual;
	}


	public String[] getNames() {
		return this.names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}


	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getAliasEqual() {
		return this.aliasEqual;
	}
	public void setAliasEqual(String aliasEqual) {
		this.aliasEqual = aliasEqual;
	}


	public String[] getAliass() {
		return this.aliass;
	}
	public void setAliass(String[] aliass) {
		this.aliass = aliass;
	}


	public String getEnName() {
		return this.enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}


	public String getEnNameEqual() {
		return this.enNameEqual;
	}
	public void setEnNameEqual(String enNameEqual) {
		this.enNameEqual = enNameEqual;
	}


	public String[] getEnNames() {
		return this.enNames;
	}
	public void setEnNames(String[] enNames) {
		this.enNames = enNames;
	}


	public String getPinyin() {
		return this.pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}


	public String getPinyinEqual() {
		return this.pinyinEqual;
	}
	public void setPinyinEqual(String pinyinEqual) {
		this.pinyinEqual = pinyinEqual;
	}


	public String[] getPinyins() {
		return this.pinyins;
	}
	public void setPinyins(String[] pinyins) {
		this.pinyins = pinyins;
	}


	public String getParentCode() {
		return this.parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


	public String getParentCodeEqual() {
		return this.parentCodeEqual;
	}
	public void setParentCodeEqual(String parentCodeEqual) {
		this.parentCodeEqual = parentCodeEqual;
	}


	public String[] getParentCodes() {
		return this.parentCodes;
	}
	public void setParentCodes(String[] parentCodes) {
		this.parentCodes = parentCodes;
	}


	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getIconEqual() {
		return this.iconEqual;
	}
	public void setIconEqual(String iconEqual) {
		this.iconEqual = iconEqual;
	}


	public String[] getIcons() {
		return this.icons;
	}
	public void setIcons(String[] icons) {
		this.icons = icons;
	}


	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}


	public String getColorEqual() {
		return this.colorEqual;
	}
	public void setColorEqual(String colorEqual) {
		this.colorEqual = colorEqual;
	}


	public String[] getColors() {
		return this.colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}


	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	public Integer[] getOrderNos() {
		return this.orderNos;
	}
	public void setOrderNos(Integer[] orderNos) {
		this.orderNos = orderNos;
	}


	public Integer getStartOrderNo() {
		return this.startOrderNo;
	}
	public void setStartOrderNo(Integer startOrderNo) {
		this.startOrderNo = startOrderNo;
	}


	public Integer getEndOrderNo() {
		return this.endOrderNo;
	}
	public void setEndOrderNo(Integer endOrderNo) {
		this.endOrderNo = endOrderNo;
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


	public Long[] getCustom4s() {
		return this.custom4s;
	}
	public void setCustom4s(Long[] custom4s) {
		this.custom4s = custom4s;
	}


	public Long getStartCustom4() {
		return this.startCustom4;
	}
	public void setStartCustom4(Long startCustom4) {
		this.startCustom4 = startCustom4;
	}


	public Long getEndCustom4() {
		return this.endCustom4;
	}
	public void setEndCustom4(Long endCustom4) {
		this.endCustom4 = endCustom4;
	}


	public Long getCustom5() {
		return this.custom5;
	}
	public void setCustom5(Long custom5) {
		this.custom5 = custom5;
	}


	public Long[] getCustom5s() {
		return this.custom5s;
	}
	public void setCustom5s(Long[] custom5s) {
		this.custom5s = custom5s;
	}


	public Long getStartCustom5() {
		return this.startCustom5;
	}
	public void setStartCustom5(Long startCustom5) {
		this.startCustom5 = startCustom5;
	}


	public Long getEndCustom5() {
		return this.endCustom5;
	}
	public void setEndCustom5(Long endCustom5) {
		this.endCustom5 = endCustom5;
	}


	public Long getCustom6() {
		return this.custom6;
	}
	public void setCustom6(Long custom6) {
		this.custom6 = custom6;
	}


	public Long[] getCustom6s() {
		return this.custom6s;
	}
	public void setCustom6s(Long[] custom6s) {
		this.custom6s = custom6s;
	}


	public Long getStartCustom6() {
		return this.startCustom6;
	}
	public void setStartCustom6(Long startCustom6) {
		this.startCustom6 = startCustom6;
	}


	public Long getEndCustom6() {
		return this.endCustom6;
	}
	public void setEndCustom6(Long endCustom6) {
		this.endCustom6 = endCustom6;
	}


	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer[] getStatuss() {
		return this.statuss;
	}
	public void setStatuss(Integer[] statuss) {
		this.statuss = statuss;
	}


	public Integer getStartStatus() {
		return this.startStatus;
	}
	public void setStartStatus(Integer startStatus) {
		this.startStatus = startStatus;
	}


	public Integer getEndStatus() {
		return this.endStatus;
	}
	public void setEndStatus(Integer endStatus) {
		this.endStatus = endStatus;
	}


	public Integer getDataStatus() {
		return this.dataStatus;
	}
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}


	public Integer[] getDataStatuss() {
		return this.dataStatuss;
	}
	public void setDataStatuss(Integer[] dataStatuss) {
		this.dataStatuss = dataStatuss;
	}


	public Integer getStartDataStatus() {
		return this.startDataStatus;
	}
	public void setStartDataStatus(Integer startDataStatus) {
		this.startDataStatus = startDataStatus;
	}


	public Integer getEndDataStatus() {
		return this.endDataStatus;
	}
	public void setEndDataStatus(Integer endDataStatus) {
		this.endDataStatus = endDataStatus;
	}


	public Long getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}


	public Long[] getCreateTimes() {
		return this.createTimes;
	}
	public void setCreateTimes(Long[] createTimes) {
		this.createTimes = createTimes;
	}


	public Long getStartCreateTime() {
		return this.startCreateTime;
	}
	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}


	public Long getEndCreateTime() {
		return this.endCreateTime;
	}
	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}


	public Long getModifyTime() {
		return this.modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Long[] getModifyTimes() {
		return this.modifyTimes;
	}
	public void setModifyTimes(Long[] modifyTimes) {
		this.modifyTimes = modifyTimes;
	}


	public Long getStartModifyTime() {
		return this.startModifyTime;
	}
	public void setStartModifyTime(Long startModifyTime) {
		this.startModifyTime = startModifyTime;
	}


	public Long getEndModifyTime() {
		return this.endModifyTime;
	}
	public void setEndModifyTime(Long endModifyTime) {
		this.endModifyTime = endModifyTime;
	}


}


