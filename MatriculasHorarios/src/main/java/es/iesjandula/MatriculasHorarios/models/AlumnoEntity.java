package es.iesjandula.MatriculasHorarios.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Alumno")
public class AlumnoEntity 
{
	@Id
	private int id;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 100)
	private String apellidos;

}