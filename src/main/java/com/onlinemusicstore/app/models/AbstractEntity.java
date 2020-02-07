package com.onlinemusicstore.app.models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {
	
	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
