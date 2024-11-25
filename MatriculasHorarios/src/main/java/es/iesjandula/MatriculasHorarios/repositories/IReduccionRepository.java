package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.MatriculasHorarios.models.ReduccionEntity;

public interface IReduccionRepository extends JpaRepository<ReduccionEntity, String>
{

}