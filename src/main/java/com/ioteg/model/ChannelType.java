package com.ioteg.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * ConfigurableEventList class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */

@Entity
public class ChannelType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull
	@NotEmpty
	private String channelName;

	@Valid
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<ConfigurableEventType> configurableEventTypes;

	@SuppressWarnings("unused")
	private ChannelType() {

	}

	/**
	 * @param channel
	 * @param configurableEventTypes
	 */
	public ChannelType(@JsonProperty("id") Long id, @NotNull @NotEmpty @JsonProperty("channelName") String channelName,
			@Valid @JsonProperty("configurableEventTypes") List<ConfigurableEventType> configurableEventTypes) {
		super();
		this.id = id;
		this.channelName = channelName;
		this.configurableEventTypes = configurableEventTypes;
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
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the configurableEventTypes
	 */
	public List<ConfigurableEventType> getConfigurableEventTypes() {
		return configurableEventTypes;
	}

	/**
	 * @param configurableEventTypes the configurableEventTypes to set
	 */
	public void setConfigurableEventTypes(List<ConfigurableEventType> configurableEventTypes) {
		this.configurableEventTypes = configurableEventTypes;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ChannelType)
			return ((ChannelType) obj).getId().equals(this.id);
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
