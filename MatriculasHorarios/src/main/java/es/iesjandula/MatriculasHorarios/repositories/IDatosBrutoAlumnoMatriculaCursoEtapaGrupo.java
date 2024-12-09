package es.iesjandula.MatriculasHorarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity;
import jakarta.transaction.Transactional;

@Repository
public interface IDatosBrutoAlumnoMatriculaCursoEtapaGrupo extends JpaRepository<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity , Integer>
{
	@Query("SELECT a FROM DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity a " +
		       "WHERE (a.cursoEtapaGrupo.idCursoEtapaGrupo.curso IS NULL OR a.cursoEtapaGrupo.idCursoEtapaGrupo.curso = :curso)" +
		       "AND (a.cursoEtapaGrupo.idCursoEtapaGrupo.etapa IS NULL OR a.cursoEtapaGrupo.idCursoEtapaGrupo.etapa = :etapa)" +
		       "AND (a.cursoEtapaGrupo.idCursoEtapaGrupo.grupo IS NULL OR a.cursoEtapaGrupo.idCursoEtapaGrupo.grupo = :grupo)")
		List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity> encontrarAlumnosPorGrupo(
		    @Param("curso") Integer curso,
		    @Param("etapa") String etapa,
		    @Param("grupo") char grupo
		);
	
	@Modifying
	@Transactional
	@Query("UPDATE DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity a " +
		       "SET a.cursoEtapaGrupo.idCursoEtapaGrupo.grupo = NULL " +
		       "WHERE a.cursoEtapaGrupo.idCursoEtapaGrupo.curso = :curso " +
		       "AND a.cursoEtapaGrupo.idCursoEtapaGrupo.etapa = :etapa " +
		       "AND a.cursoEtapaGrupo.idCursoEtapaGrupo.grupo = :grupo")
		void borrarGrupo(
		    @Param("curso") Integer curso,
		    @Param("etapa") String etapa,
		    @Param("grupo") char grupo
		);

}