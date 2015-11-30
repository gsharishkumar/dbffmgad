package com.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PatientMetadataPK {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private Entity entity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}

