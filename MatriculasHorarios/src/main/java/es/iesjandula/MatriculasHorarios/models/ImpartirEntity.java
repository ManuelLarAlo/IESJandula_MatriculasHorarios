package es.iesjandula.MatriculasHorarios.models;



import es.iesjandula.MatriculasHorarios.models.ids.IdImpartir;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "Impartir")
public class ImpartirEntity 
{
	@EmbeddedId
	private IdImpartir idImpartir;
	
	@MapsId(value = "asignatura")
	@ManyToOne
	private AsignaturaEntity asignatura;
	
	@MapsId(value = "profesor")
	@ManyToOne
	private ProfesorEntity profesor;
	
}