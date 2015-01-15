/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx;

import java.io.Serializable;

import net.shopxx.entity.Store;

/**
 * 身份信息
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 用户名 */
	private String username;
	
	/** 企业号*/
	private String entcode;
	
	/** 店铺*/
	private Store store;

	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username, String entcode, Store store) {
		this.id = id;
		this.username = username;
		this.entcode = entcode;
		this.store = store;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
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
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取企业号
	 * @return
	 */
	public String getEntcode() {
		return entcode;
	}

	/**
	 * 设置企业号
	 * @param entcode
	 */
	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}

	/**
	 * 获取店铺信息
	 * @return
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * 设置店铺信息
	 * @param store
	 */
	public void setStore(Store store) {
		this.store = store;
	}
	
	@Override
	public String toString() {
		return username;
	}

}