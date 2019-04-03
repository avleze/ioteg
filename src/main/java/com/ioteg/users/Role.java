package com.ioteg.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{

	private static final long serialVersionUID = 9190709637494759982L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected String roleName;
	
	@SuppressWarnings("unused")
	private Role() {
		
	}
	
	/**
	 * @param id
	 * @param rol
	 */
	public Role(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return roleName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.roleName = name;
	}
	
	
}
