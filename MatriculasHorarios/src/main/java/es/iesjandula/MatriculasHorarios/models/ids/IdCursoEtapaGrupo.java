package es.iesjandula.MatriculasHorarios.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IdCursoEtapaGrupo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private int curso;
	
	@Column(length = 50)
	private String etapa;
	
	@Column(length = 1)
	private char grupo;
	
}