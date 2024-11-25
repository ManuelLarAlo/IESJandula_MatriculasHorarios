package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.AlumnoEntity;

@Repository
public interface IAlumnoRepository extends JpaRepository<AlumnoEntity, Integer>
{

}