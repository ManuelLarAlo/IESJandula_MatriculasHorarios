package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.MatriculasHorarios.models.ProfesorReduccionEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdProfesorReduccion;

public interface IProfesorReduccionRepository extends JpaRepository<ProfesorReduccionEntity, IdProfesorReduccion>
{

}