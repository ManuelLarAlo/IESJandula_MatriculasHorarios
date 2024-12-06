package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaEntity;

@Repository
public interface IDatosBrutoAlumnoMatricula extends JpaRepository<DatosBrutoAlumnoMatriculaEntity, Integer>
{

}