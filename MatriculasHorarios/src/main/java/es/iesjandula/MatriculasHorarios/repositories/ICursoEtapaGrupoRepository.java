package es.iesjandula.MatriculasHorarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.CursoEtapaGrupoEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdCursoEtapaGrupo;

@Repository
public interface ICursoEtapaGrupoRepository extends JpaRepository<CursoEtapaGrupoEntity, IdCursoEtapaGrupo>
{
	List<CursoEtapaGrupoEntity> findByIdCursoEtapaGrupoCursoAndIdCursoEtapaGrupoEtapa(int curso, String etapa);
	
	List<CursoEtapaGrupoEntity> findByIdCursoEtapaGrupoCursoAndIdCursoEtapaGrupoEtapaOrderByIdCursoEtapaGrupoGrupoAsc(int curso, String etapa);
}