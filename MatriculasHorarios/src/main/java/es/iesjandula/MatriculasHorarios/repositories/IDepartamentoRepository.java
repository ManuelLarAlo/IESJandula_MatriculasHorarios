package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.DepartamentoEntity;

@Repository
public interface IDepartamentoRepository extends JpaRepository<DepartamentoEntity, String>
{

}