package com.ioteg.model;

import com.ioteg.users.User;


public interface OwnedResource {
	public User getOwner();
	public void setOwner(User user);
}
