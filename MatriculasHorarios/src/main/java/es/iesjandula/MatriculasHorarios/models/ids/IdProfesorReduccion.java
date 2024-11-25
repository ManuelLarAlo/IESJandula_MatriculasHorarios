package es.iesjandula.MatriculasHorarios.models.ids;

import es.iesjandula.MatriculasHorarios.models.ProfesorEntity;
import es.iesjandula.MatriculasHorarios.models.ReduccionEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IdProfesorReduccion 
{
	@ManyToOne
	private ProfesorEntity profesor;
	
	@ManyToOne
	private ReduccionEntity reduccion;
}