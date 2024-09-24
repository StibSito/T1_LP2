package com.prueba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_grado")
public class Grado {

	@Id
	private int id_grado;
	private String descripcion;
	private double precio;
}
