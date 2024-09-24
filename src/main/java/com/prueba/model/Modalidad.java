package com.prueba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_modalidad")
public class Modalidad {

	@Id
	private int id_modalidad;
	private String descripcion;
}
