/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;

import net.shopxx.listener.EntityListener;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 基类
 * 
 * http://blog.csdn.net/sdyy321/article/details/40298081
 * <p>
 * @JsonAutoDetect :（作用在类上）来开启/禁止自动检测, 然后在序列化为json格式时有作用
 *  fieldVisibility:字段的可见级别
		ANY:任何级别的字段都可以自动识别
		NONE:所有字段都不可以自动识别
		NON_PRIVATE:非private修饰的字段可以自动识别
		PROTECTED_AND_PUBLIC:被protected和public修饰的字段可以被自动识别
		PUBLIC_ONLY:只有被public修饰的字段才可以被自动识别
		DEFAULT:同PUBLIC_ONLY
	jackson默认的字段属性发现规则如下：
	所有被public修饰的字段->所有被public修饰的getter->所有被public修饰的setter
 * 
 * getterVisibility：get的可见级别
 * setterVisibility：set的可见级别
 * isGetterVisibility：isGetter的可见级别
 * creatorVisibility：creator的可见级别
 * </p>
 *  <p>
 * @MappedSuperclass :
 * 基于代码复用和模型分离的思想，在项目开发中使用JPA的@MappedSuperclass注解将实体类的多个共同属性封装到非实体类中，适合父类和子类的关系
 * <ul>
 *  <li>0、 MappedSuperclass 注解只能标注在类上</li>
    <li>1、标注为@MappedSuperclass的类将不是一个完整的实体类，他不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。</li>
    <li>2、标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。</li>
    <li>3、如果一个标注为@MappedSuperclass的类继承了另外一个实体类或者另外一个同样标注了@MappedSuperclass的类的话，
	    他将可以使用@AttributeOverride或@AttributeOverrides注解重定义其父类(无论是否是实体类)的属性映射到数据库表中的字段。
	    比如可以重定义字段名或长度等属性，使用@AttributeOverride中的子属性@Column进行具体的定义。</li>
	<li> * 注意：对于其父类中标注@Lob注解的属性将不能重载，并且@AttributeOverride里的@Column设置都将不起作用。JPA规范中对@Lob注解并没有说明不能同时标注@Column注解，
	但是在实际使用中Hibernate JPA不支持这种标注方式。</li>
    <li>4、标注为@MappedSuperclass的类其属性最好设置为protected或default类型的，以保证其同一个包下的子类可以直接调用它的属性。 (责任编辑：xszlw.com)</li>
    </ul>
 * </p>
 * @author SHOP++ Team
 * @version 3.0
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE,
setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@EntityListeners(EntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -67188388306700736L;

	/** "ID"属性名称 */
	public static final String ID_PROPERTY_NAME = "id";

	/** "创建日期"属性名称 */
	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

	/** "修改日期"属性名称 */
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}

	/** ID */
	protected Long id;

	/** 创建日期 */ 
	protected Date createDate;

	/** 修改日期 */
	protected Date modifyDate;

	/**
	 * 获取ID
	 * @JsonProperty:<br>
	 * 作用在字段或方法上，用来对属性的序列化/反序列化，可以用来避免遗漏属性，同时提供对属性名称重命名，<br>
	 * 比如在很多场景下Java对象的属性是按照规范的驼峰书写，但是实际展示的却是类似C-style或C++/Microsolft style<br>
	 * 
	 * @DocumentId: 用于标示实体类中的唯一的属性保存在索引文件中，是当进行全文检索时可以这个唯一的属性来区分索引中其他实体对象，一般使用实体类中的主键属性<br>
	 * 
	 * @return ID
	 */
	@JsonProperty
	@DocumentId
	@Id
	// MySQL/SQLServer: @GeneratedValue(strategy = GenerationType.AUTO)
	// Oracle: @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取创建日期
	 * <p>
	 * @Field 标注在类的get属性上，标识一个索引的Field <br>
	 * 属性 :
	 * <ul>
      	  <li>index 指定是否索引，与Lucene相同</li>
	      <li>store 指定是否索引，与Lucene相同</li>
	      <li>name 指定Field的name，默认为类属性的名称</li>
	      <li>analyzer 指定分析器</li>
	    </ul>
	    </p>
	    <p>
		  @DateBridge  因为lucene有些版本现在貌似只能对字符串进行索引【新的版本貌似支持数值索引】，
		  所有date类型需要转换成string，Resolution.SECOND就是解析成秒格式的字符串，有以下参数
		  <ul>
			<li>Resolution.YEAR: yyyy</li>
			<li>Resolution.MONTH: yyyyMM</li>
			<li>Resolution.DAY: yyyyMMdd</li>
			<li>Resolution.HOUR: yyyyMMddHH</li>
			<li>Resolution.MINUTE: yyyyMMddHHmm</li>
			<li>Resolution.SECOND: yyyyMMddHHmmss</li>
			<li>Resolution.MILLISECOND: yyyyMMddHHmmssSSS	</li>						
		  </ul>
	 *  </p>
	 * 
	 * @return 创建日期
	 */
	@JsonProperty   //设置json属性
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false, updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param createDate
	 *            创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取修改日期
	 * 
	 * @return 修改日期
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false)
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 设置修改日期
	 * 
	 * @param modifyDate
	 *            修改日期
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

}