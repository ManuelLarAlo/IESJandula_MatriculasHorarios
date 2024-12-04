package es.iesjandula.MatriculasHorarios.modelsnew.ids;

import java.io.Serializable;

import es.iesjandula.MatriculasHorarios.modelsnew.CursoEtapaGrupo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class IdAsignaturas implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "asignatura_curso", referencedColumnName = "curso", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_etapa", referencedColumnName = "etapa", insertable = false, updatable = false),
        @JoinColumn(name = "asignatura_grupo", referencedColumnName = "grupo", insertable = false, updatable = false)
    })
	private CursoEtapaGrupo cursoEtapaGrupo;
	
	@Column(length = 100)
	private String nombre;
}