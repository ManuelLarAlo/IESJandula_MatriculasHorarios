package es.iesjandula.MatriculasHorarios.models;

import java.util.List;

import es.iesjandula.MatriculasHorarios.models.ids.IdAsignatura;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Asignatura")
public class AsignaturaEntity 
{
	@EmbeddedId
	private IdAsignatura id;
	
	@ManyToOne
	@JoinColumn(name="departamento_propietario", referencedColumnName = "nombre")
	private DepartamentoEntity departamentoPropietario;
	
	@ManyToOne
	@JoinColumn(name="departamento_receptor", referencedColumnName = "nombre")
	private DepartamentoEntity departamentoReceptor;
	
	@ManyToOne
	@JoinColumn(name="bloque_id", referencedColumnName="id")
	private BloqueEntity bloqueId;
	
	@OneToMany(mappedBy = "asignatura")
	private List<MatriculaEntity> matriculas;
}	