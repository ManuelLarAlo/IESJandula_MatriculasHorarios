package es.iesjandula.MatriculasHorarios.modelsnew.ids;

import java.io.Serializable;

import es.iesjandula.MatriculasHorarios.models.AsignaturaEntity;
import es.iesjandula.MatriculasHorarios.modelsnew.AsignaturasEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class IdAlumnoAsignatura implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumns({
        @JoinColumn(name = "asignatura_curso", referencedColumnName = "curso"),
        @JoinColumn(name = "asignatura_etapa", referencedColumnName = "etapa"),
        @JoinColumn(name = "asignatura_grupo", referencedColumnName = "grupo"),
        @JoinColumn(name = "asignatura_nombre", referencedColumnName = "nombre")
    })
    private AsignaturasEntity asignaturas;
	
	@Column
	private int id;
}