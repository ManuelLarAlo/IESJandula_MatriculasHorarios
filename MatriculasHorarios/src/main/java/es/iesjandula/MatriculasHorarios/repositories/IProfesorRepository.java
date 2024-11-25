package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.MatriculasHorarios.models.ProfesorEntity;

public interface IProfesorRepository extends JpaRepository<ProfesorEntity, String>
{

}