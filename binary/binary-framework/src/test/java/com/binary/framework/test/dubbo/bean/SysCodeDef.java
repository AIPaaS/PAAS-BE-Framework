package com.binary.framework.test.dubbo.bean;




import com.binary.framework.bean.EntityBean;


/**
 * mapping-table: 代码定义表[SYS_CODE_DEF]
 */
public class SysCodeDef implements EntityBean {
	private static final long serialVersionUID = 1L;


	/**
	 * mapping-field: ID[ID]
	 */
	private Long id;


	/**
	 * mapping-field: 定义代码[DEF_CODE]
	 * 代码维一标识
	 */
	private String defCode;


	/**
	 * mapping-field: 代码名称[CODE_NAME]
	 */
	private String codeName;


	/**
	 * mapping-field: 代码类型[CODE_TYPE]
	 * 1=NUMBER 2=VARCHAR
	 */
	private Integer codeType;


	/**
	 * mapping-field: 代码描述[CODE_DESC]
	 */
	private String codeDesc;


	/**
	 * mapping-field: 是否可维护[IS_MAINTAIN]
	 * 1-可维护 0-不可维护
	 */
	private Integer isMaintain;


	/**
	 * mapping-field: 有效状态[STATUS]
	 * 1-有效 0-无效
	 */
	private Integer status;


	/**
	 * mapping-field: 数据状态[DATA_STATUS]
	 * 1-正常 0-删除
	 */
	private Integer dataStatus;


	/**
	 * mapping-field: 创建人[CREATOR]
	 */
	private String creator;


	/**
	 * mapping-field: 修改人[MODIFIER]
	 */
	private String modifier;


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


	public String getDefCode() {
		return this.defCode;
	}
	public void setDefCode(String defCode) {
		this.defCode = defCode;
	}


	public String getCodeName() {
		return this.codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}


	public Integer getCodeType() {
		return this.codeType;
	}
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
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


	public String getCreator() {
		return this.creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getModifier() {
		return this.modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
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


