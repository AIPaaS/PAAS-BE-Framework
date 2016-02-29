package com.binary.framework.test.dubbo.bean;




import com.binary.framework.bean.Condition;


/**
 * condition-table: 代码定义表[SYS_CODE_DEF]
 */
public class CSysCodeDef implements Condition {
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
	 * condition-field: 定义代码[DEF_CODE] operate-Like[like]
	 * 代码维一标识
	 */
	private String defCode;


	/**
	 * condition-field: 定义代码[DEF_CODE] operate-Equal[=]
	 * 代码维一标识
	 */
	private String defCodeEqual;


	/**
	 * condition-field: 定义代码[DEF_CODE] operate-In[in]
	 * 代码维一标识
	 */
	private String[] defCodes;


	/**
	 * condition-field: 代码名称[CODE_NAME] operate-Like[like]
	 */
	private String codeName;


	/**
	 * condition-field: 代码名称[CODE_NAME] operate-Equal[=]
	 */
	private String codeNameEqual;


	/**
	 * condition-field: 代码名称[CODE_NAME] operate-In[in]
	 */
	private String[] codeNames;


	/**
	 * condition-field: 代码类型[CODE_TYPE] operate-Equal[=]
	 * 1=NUMBER 2=VARCHAR
	 */
	private Integer codeType;


	/**
	 * condition-field: 代码类型[CODE_TYPE] operate-In[in]
	 * 1=NUMBER 2=VARCHAR
	 */
	private Integer[] codeTypes;


	/**
	 * condition-field: 代码类型[CODE_TYPE] operate-GTEqual[>=]
	 * 1=NUMBER 2=VARCHAR
	 */
	private Integer startCodeType;

	/**
	 * condition-field: 代码类型[CODE_TYPE] operate-LTEqual[<=]
	 * 1=NUMBER 2=VARCHAR
	 */
	private Integer endCodeType;


	/**
	 * condition-field: 代码描述[CODE_DESC] operate-Like[like]
	 */
	private String codeDesc;


	/**
	 * condition-field: 是否可维护[IS_MAINTAIN] operate-Equal[=]
	 * 1-可维护 0-不可维护
	 */
	private Integer isMaintain;


	/**
	 * condition-field: 是否可维护[IS_MAINTAIN] operate-In[in]
	 * 1-可维护 0-不可维护
	 */
	private Integer[] isMaintains;


	/**
	 * condition-field: 是否可维护[IS_MAINTAIN] operate-GTEqual[>=]
	 * 1-可维护 0-不可维护
	 */
	private Integer startIsMaintain;

	/**
	 * condition-field: 是否可维护[IS_MAINTAIN] operate-LTEqual[<=]
	 * 1-可维护 0-不可维护
	 */
	private Integer endIsMaintain;


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
	 * 1-正常 0-删除
	 */
	private Integer dataStatus;


	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-In[in]
	 * 1-正常 0-删除
	 */
	private Integer[] dataStatuss;


	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-GTEqual[>=]
	 * 1-正常 0-删除
	 */
	private Integer startDataStatus;

	/**
	 * condition-field: 数据状态[DATA_STATUS] operate-LTEqual[<=]
	 * 1-正常 0-删除
	 */
	private Integer endDataStatus;


	/**
	 * condition-field: 创建人[CREATOR] operate-Like[like]
	 */
	private String creator;


	/**
	 * condition-field: 创建人[CREATOR] operate-Equal[=]
	 */
	private String creatorEqual;


	/**
	 * condition-field: 创建人[CREATOR] operate-In[in]
	 */
	private String[] creators;


	/**
	 * condition-field: 修改人[MODIFIER] operate-Like[like]
	 */
	private String modifier;


	/**
	 * condition-field: 修改人[MODIFIER] operate-Equal[=]
	 */
	private String modifierEqual;


	/**
	 * condition-field: 修改人[MODIFIER] operate-In[in]
	 */
	private String[] modifiers;


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


	public String getDefCode() {
		return this.defCode;
	}
	public void setDefCode(String defCode) {
		this.defCode = defCode;
	}


	public String getDefCodeEqual() {
		return this.defCodeEqual;
	}
	public void setDefCodeEqual(String defCodeEqual) {
		this.defCodeEqual = defCodeEqual;
	}


	public String[] getDefCodes() {
		return this.defCodes;
	}
	public void setDefCodes(String[] defCodes) {
		this.defCodes = defCodes;
	}


	public String getCodeName() {
		return this.codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}


	public String getCodeNameEqual() {
		return this.codeNameEqual;
	}
	public void setCodeNameEqual(String codeNameEqual) {
		this.codeNameEqual = codeNameEqual;
	}


	public String[] getCodeNames() {
		return this.codeNames;
	}
	public void setCodeNames(String[] codeNames) {
		this.codeNames = codeNames;
	}


	public Integer getCodeType() {
		return this.codeType;
	}
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}


	public Integer[] getCodeTypes() {
		return this.codeTypes;
	}
	public void setCodeTypes(Integer[] codeTypes) {
		this.codeTypes = codeTypes;
	}


	public Integer getStartCodeType() {
		return this.startCodeType;
	}
	public void setStartCodeType(Integer startCodeType) {
		this.startCodeType = startCodeType;
	}


	public Integer getEndCodeType() {
		return this.endCodeType;
	}
	public void setEndCodeType(Integer endCodeType) {
		this.endCodeType = endCodeType;
	}


	public String getCodeDesc() {
		return this.codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}


	public Integer getIsMaintain() {
		return this.isMaintain;
	}
	public void setIsMaintain(Integer isMaintain) {
		this.isMaintain = isMaintain;
	}


	public Integer[] getIsMaintains() {
		return this.isMaintains;
	}
	public void setIsMaintains(Integer[] isMaintains) {
		this.isMaintains = isMaintains;
	}


	public Integer getStartIsMaintain() {
		return this.startIsMaintain;
	}
	public void setStartIsMaintain(Integer startIsMaintain) {
		this.startIsMaintain = startIsMaintain;
	}


	public Integer getEndIsMaintain() {
		return this.endIsMaintain;
	}
	public void setEndIsMaintain(Integer endIsMaintain) {
		this.endIsMaintain = endIsMaintain;
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


	public String getCreator() {
		return this.creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getCreatorEqual() {
		return this.creatorEqual;
	}
	public void setCreatorEqual(String creatorEqual) {
		this.creatorEqual = creatorEqual;
	}


	public String[] getCreators() {
		return this.creators;
	}
	public void setCreators(String[] creators) {
		this.creators = creators;
	}


	public String getModifier() {
		return this.modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public String getModifierEqual() {
		return this.modifierEqual;
	}
	public void setModifierEqual(String modifierEqual) {
		this.modifierEqual = modifierEqual;
	}


	public String[] getModifiers() {
		return this.modifiers;
	}
	public void setModifiers(String[] modifiers) {
		this.modifiers = modifiers;
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


