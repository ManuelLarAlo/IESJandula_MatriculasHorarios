package es.iesjandula.MatriculasHorarios.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class IdCursoEtapa implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private int curso;
	
	@Column(length = 50)
	private String etapa;
}