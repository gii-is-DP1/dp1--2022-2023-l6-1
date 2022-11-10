package org.springframework.samples.petclinic.playZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "playzone")
public class PlayZone extends BaseEntity{
	
	@Min(value = 1)
	@Max(value = 20)
	@Column(name = "numFila")
	private Integer numFila;
	
	@Min(value = 1)
	@Max(value = 7)
	@Column(name = "numColumna")
	private Integer numColumna;
}
