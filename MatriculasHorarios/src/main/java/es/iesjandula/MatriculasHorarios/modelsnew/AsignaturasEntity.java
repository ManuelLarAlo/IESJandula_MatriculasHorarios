package es.iesjandula.MatriculasHorarios.modelsnew;

import java.util.List;

import es.iesjandula.MatriculasHorarios.models.DepartamentoEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdAsignatura;
import es.iesjandula.MatriculasHorarios.modelsnew.ids.IdAsignaturas;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
@Table(name = "Asignaturas")
public class AsignaturasEntity 
{
	@EmbeddedId
	private IdAsignaturas id;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "asignatura_curso", referencedColumnName = "curso", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_etapa", referencedColumnName = "etapa", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_grupo", referencedColumnName = "grupo", insertable = false, updatable = false)
    })
	private CursoEtapaGrupo cursoEtapaGrupo;
}	