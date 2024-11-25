package es.iesjandula.MatriculasHorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.CursoEtapaEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdCursoEtapa;

@Repository
public interface ICursoEtapaRepository extends JpaRepository<CursoEtapaEntity, IdCursoEtapa>
{

}