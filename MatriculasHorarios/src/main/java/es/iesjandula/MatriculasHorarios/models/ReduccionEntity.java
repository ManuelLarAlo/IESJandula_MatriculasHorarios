package es.iesjandula.MatriculasHorarios.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reduccion")
public class ReduccionEntity 
{
	@Id
	@Column(length = 100)
	private String nombre;
	
	@Column(nullable = false)
	private int horas;
	
	@Column(nullable = true)
	private boolean decideDireccion;
	
	@OneToMany(mappedBy = "reduccion")
	private List<ProfesorReduccionEntity> profesorReducciones;
}