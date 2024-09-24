package com.prueba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_reserva")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num_reserva; 
	private String dni;
	private String nombre_alumno;
	private String email;
	private String telefono;
	private int id_grado;
	private int id_modalidad;
	private String fecha_inscripcion;
	
	@OneToOne
	@JoinColumn(name = "id_grado",updatable = false,insertable = false)
	private Grado objGrado;
	
	@OneToOne
	@JoinColumn(name = "id_modalidad",updatable = false,insertable = false)
	private Modalidad objModalidad;
	
	
}
