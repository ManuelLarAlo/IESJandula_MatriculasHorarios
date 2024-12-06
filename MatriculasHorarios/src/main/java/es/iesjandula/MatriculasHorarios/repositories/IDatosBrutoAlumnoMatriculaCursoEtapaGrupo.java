package es.iesjandula.MatriculasHorarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity;

@Repository
public interface IDatosBrutoAlumnoMatriculaCursoEtapaGrupo extends JpaRepository<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity , Integer>
{
	@Query("SELECT a FROM Datos_Bruto_Alumno_Matricula_curso_grupo_etapa a WHERE a.curso = :curso AND a.etapa = :etapa AND (a.grupo = null OR a.grupo = :grupo) ")
	List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity> encontrarAlumnosPorGrupo(@Param("curso") Integer curso,
																				  @Param("etapa") String etapa,
																				  @Param("grupo") char grupo);
	
	
	@Query("SELECT a FROM Datos_Bruto_Alumno_Matricula_curso_grupo_etapa a " +
		       "WHERE a.curso = :curso AND a.etapa = :etapa AND a.grupo IS NULL")
	List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity> encontrarAlumnosSinGrupo(@Param("curso") Integer curso,
																				  @Param("etapa") String etapa
																				 );
	
	@Query("UPDATE Datos_Bruto_Alumno_Matricula_curso_grupo_etapa a " +
		       "SET a.grupo = NULL " +
		       "WHERE a.curso = :curso AND a.etapa = :etapa AND a.grupo = :grupo")
		void borrarGrupo(@Param("curso") Integer curso,
		                 @Param("etapa") String etapa,
		                 @Param("grupo") char grupo);
}