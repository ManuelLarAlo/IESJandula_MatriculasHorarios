package es.iesjandula.MatriculasHorarios.modelsnew;

import es.iesjandula.MatriculasHorarios.modelsnew.ids.IdAlumnoAsignatura;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class AlumnoAsignatura 
{

	@EmbeddedId
	private IdAlumnoAsignatura idAlumnoAsignatura;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "asignatura_curso", referencedColumnName = "curso", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_etapa", referencedColumnName = "etapa", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_grupo", referencedColumnName = "grupo", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
	})
	private AsignaturasEntity Asignaturas;
	
	@ManyToOne
	@JoinColumn
	@MapsId(value = "id")
	private DatosBrutoAlumnoMatriculasEntity datosBrutoAlumnoMatriculas;
	
}
