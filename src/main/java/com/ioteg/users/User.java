package com.ioteg.users;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

	@ManyToMany
	private Set<Role> roles;

	@SuppressWarnings("unused")
	private User() {

	}

	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param isAccountNonExpired
	 * @param isAccountNonLocked
	 * @param isCredentialsNonExpired
	 * @param isEnabled
	 * @param roles
	 */
	public User(@JsonProperty("id") long id, @NotNull @NotEmpty @JsonProperty("username") String username,
			@NotNull @NotEmpty @JsonProperty("password") String password,
			@JsonProperty("isAccountNonExpired") boolean isAccountNonExpired,
			@JsonProperty("isAccountNonLocked") boolean isAccountNonLocked,
			@JsonProperty("isCredentialsNonExpired") boolean isCredentialsNonExpired,
			@JsonProperty("isEnabled") boolean isEnabled, Set<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

}
