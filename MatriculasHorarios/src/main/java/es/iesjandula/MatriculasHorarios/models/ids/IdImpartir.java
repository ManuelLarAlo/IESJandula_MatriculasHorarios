package es.iesjandula.MatriculasHorarios.models.ids;

import es.iesjandula.MatriculasHorarios.models.AsignaturaEntity;
import es.iesjandula.MatriculasHorarios.models.ProfesorEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IdImpartir 
{
	@ManyToOne
	private AsignaturaEntity asignatura;

	@ManyToOne
	private ProfesorEntity profesor;
}