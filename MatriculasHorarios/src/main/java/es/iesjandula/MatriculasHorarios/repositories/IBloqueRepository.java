package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.BloqueEntity;

@Repository
public interface IBloqueRepository extends JpaRepository<BloqueEntity, String>
{

}