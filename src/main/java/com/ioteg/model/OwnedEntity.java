package com.ioteg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OwnedEntity implements OwnedResource{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	
	@OneToOne
	private User owner;
	
	@Override
	public User getOwner() {
		return owner;
	}

	@Override
	public void setOwner(User user) {
		this.owner = user;
	}
}
