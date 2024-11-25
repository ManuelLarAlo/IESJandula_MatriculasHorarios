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
@Table(name = "Departamento")
public class DepartamentoEntity
{
	@Id
	@Column(length = 100)
	private String nombre;
	
    @OneToMany(mappedBy = "departamentoPropietario")
    private List<AsignaturaEntity> asignaturasPropietarias;
    
    @OneToMany(mappedBy = "departamentoReceptor")
    private List<AsignaturaEntity> asignaturasReceptores;
    
    @OneToMany(mappedBy = "departamento")
    private List<ProfesorEntity> profesores;
}