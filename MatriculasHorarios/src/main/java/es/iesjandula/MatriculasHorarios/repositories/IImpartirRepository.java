package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.MatriculasHorarios.models.ImpartirEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdImpartir;

public interface IImpartirRepository extends JpaRepository<ImpartirEntity, IdImpartir>
{

}