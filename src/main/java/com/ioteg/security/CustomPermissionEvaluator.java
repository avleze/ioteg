package com.ioteg.security;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.ioteg.model.OwnedResource;

public class CustomPermissionEvaluator implements PermissionEvaluator{

	Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		
		if(permission.equals("OWNER") && targetDomainObject instanceof OwnedResource)
		{
			OwnedResource ownedResource = (OwnedResource) targetDomainObject;
			return ownedResource.getOwner().getUsername().equals(authentication.getPrincipal());
		}
		
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return false;
	}

}
