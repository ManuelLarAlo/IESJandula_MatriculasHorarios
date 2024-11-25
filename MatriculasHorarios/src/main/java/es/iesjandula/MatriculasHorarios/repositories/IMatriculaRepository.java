package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.MatriculaEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdMatricula;

@Repository
public interface IMatriculaRepository extends JpaRepository<MatriculaEntity, IdMatricula>
{

}