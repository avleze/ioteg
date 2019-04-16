package com.ioteg.users;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ioteg.model.ChannelType;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@UniqueUsername
	@Column(unique = true)
	private String username;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotEmpty
	private String password;

	@ManyToMany
	private Set<Role> roles;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ChannelType> channels;

	private String mqttApiKey;

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
	public User(@JsonProperty("id") Long id, @NotNull @NotEmpty @JsonProperty("username") String username,
			@JsonProperty("email") String email, @NotNull @NotEmpty @JsonProperty("password") String password,
			@JsonProperty("roles") Set<Role> roles) {
		super();
		if (roles == null)
			roles = new HashSet<>();

		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
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
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
