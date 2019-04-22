package com.ioteg.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ioteg.model.validation.UniqueUsername;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "The username can not be null.")
	@NotEmpty(message = "The username can not be empty.")
	@UniqueUsername
	@Column(unique = true)
	private String username;

	@NotNull(message = "The username can not be null.")
	@NotEmpty(message = "The username can not be empty.")
	@Email(message = "The email is not valid.")
	private String email;

	@NotNull(message = "The password can not be null.")
	@NotEmpty(message = "The password can not be empty.")
	private String password;

	private String role;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ChannelType> channels;

	private String mqttApiKey;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Stream.of(new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the channels
	 */
	public List<ChannelType> getChannels() {
		return channels;
	}

	/**
	 * @param channels the channels to set
	 */
	public void setChannels(List<ChannelType> channels) {
		this.channels = channels;
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mqttApiKey
	 */
	public String getMqttApiKey() {
		return mqttApiKey;
	}

	/**
	 * @param mqttApiKey the mqttApiKey to set
	 */
	public void setMqttApiKey(String mqttApiKey) {
		this.mqttApiKey = mqttApiKey;
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

}
