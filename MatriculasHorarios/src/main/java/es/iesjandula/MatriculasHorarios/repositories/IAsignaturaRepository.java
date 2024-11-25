package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.AsignaturaEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdAsignatura;

@Repository
public interface IAsignaturaRepository extends JpaRepository<AsignaturaEntity, IdAsignatura>
{

}