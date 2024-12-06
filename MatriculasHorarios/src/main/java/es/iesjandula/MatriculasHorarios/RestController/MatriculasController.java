package es.iesjandula.MatriculasHorarios.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import es.iesjandula.MatriculasHorarios.models.CursoEtapaEntity;
import es.iesjandula.MatriculasHorarios.models.CursoEtapaGrupoEntity;
import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity;
import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdCursoEtapa;
import es.iesjandula.MatriculasHorarios.models.ids.IdCursoEtapaGrupo;
import es.iesjandula.MatriculasHorarios.repositories.ICursoEtapaGrupoRepository;
import es.iesjandula.MatriculasHorarios.repositories.ICursoEtapaRepository;
import es.iesjandula.MatriculasHorarios.repositories.IDatosBrutoAlumnoMatricula;
import es.iesjandula.MatriculasHorarios.repositories.IDatosBrutoAlumnoMatriculaCursoEtapaGrupo;
import es.iesjandula.MatriculasHorarios.utils.MatriculasException;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/Matriculas")
public class MatriculasController 
{
	@Autowired
	private ICursoEtapaRepository iCursoRepository;
	
	@Autowired
	private IDatosBrutoAlumnoMatricula iDatosBrutoAlumnoMatricula;
	
	@Autowired
	private ICursoEtapaGrupoRepository iCursoEtapaGrupoRepository;
	
	@Autowired
	private IDatosBrutoAlumnoMatriculaCursoEtapaGrupo iDatosBrutoAlumnoMatriculaCursoEtapaGrupo;
	
	
	@RequestMapping( method = RequestMethod.POST,value = "/upload",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadCursos(
			@RequestPart( value = "csv") MultipartFile csv) throws MatriculasException
	{
		File file=(File) csv;
		try
		{
			Scanner scanner = new Scanner(file);
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				IdCursoEtapa idCursoEtapa         = new IdCursoEtapa();
				CursoEtapaEntity cursoEtapaEntity = new CursoEtapaEntity();
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				idCursoEtapa.setCurso(Integer.parseInt(lineaDelFicheroTroceada[0]));
				idCursoEtapa.setEtapa(lineaDelFicheroTroceada[1]);
				cursoEtapaEntity.setIdCursoEtapa(idCursoEtapa);
				this.iCursoRepository.saveAndFlush(cursoEtapaEntity);
			}
		}
		catch(Exception exception)
		{
			return ResponseEntity.status(500).body("Error de servidor"+exception);
		}
		return ResponseEntity.ok().body(200);
	}
	
	/**
	 * Endpoint que se encarga de subir los alumnos a la base de datos cargados desde el csv
	 * @param curso
	 * @param etapa
	 * @param csv
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/cargaAlumnos", consumes = "multipart/form-data")
	public void cargaMatriculas(
								@RequestHeader(required = true) int curso,
								@RequestHeader(required = true) String etapa,
								@RequestPart( value = "csv") MultipartFile csv) 
	{
		IdCursoEtapa idCursoEtapa         = new IdCursoEtapa();
		CursoEtapaEntity cursoEtapaEntity = new CursoEtapaEntity();
		DatosBrutoAlumnoMatriculaEntity datosBrutoAlumnoMatriculaEntity = new DatosBrutoAlumnoMatriculaEntity();
		try 
		{
			File file=(File) csv;
			//rellenamos el la clave primaria de la tabla CursoEntity
			idCursoEtapa.setCurso(curso);
			idCursoEtapa.setEtapa(etapa);
			cursoEtapaEntity.setIdCursoEtapa(idCursoEtapa);
			Scanner scanner = new Scanner(file);
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				datosBrutoAlumnoMatriculaEntity.setNombre(lineaDelFicheroTroceada[0]);
				datosBrutoAlumnoMatriculaEntity.setApellidos(lineaDelFicheroTroceada[1]);
				datosBrutoAlumnoMatriculaEntity.setAsignatura(lineaDelFicheroTroceada[2]);
				datosBrutoAlumnoMatriculaEntity.setCursoEtapa(cursoEtapaEntity);
				this.iDatosBrutoAlumnoMatricula.saveAndFlush(datosBrutoAlumnoMatriculaEntity); 	
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/Cursos")
	public ResponseEntity <?> obtenerCursos() throws MatriculasException
	{
		List<CursoEtapaEntity>listaCursos = this.iCursoRepository.findAll();
		
		if (listaCursos.isEmpty())
		{
			throw new MatriculasException(404, "No se ha encontrado ningun Curso");
		}
		
		return ResponseEntity.status(200).body(listaCursos);
	}
	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/Alumnos")
	public ResponseEntity <?> obtenerAlumnos()throws MatriculasException
	{
		List<DatosBrutoAlumnoMatriculaEntity> ListadatosBrutoAlumnoMatriculaList= this.iDatosBrutoAlumnoMatricula.findAll();
		
		if (ListadatosBrutoAlumnoMatriculaList.isEmpty())
		{
			throw new MatriculasException(404, "No se ha encontrado ningun Alumno");
		}
		
		return ResponseEntity.status(200).body(ListadatosBrutoAlumnoMatriculaList);
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/Grupos")
	public ResponseEntity <?> obtenerGrupos(@RequestHeader(required = true) int curso,
			   @RequestHeader(required = true) String etapa) throws MatriculasException
	{
		List<CursoEtapaGrupoEntity>listaGrupo = this.iCursoEtapaGrupoRepository.findGrupoByCursoAndEtapa(curso, etapa);
		
		if (listaGrupo.isEmpty())
		{
			throw new MatriculasException(404, "No se ha encontrado ningun grupo");
		}
		
		return ResponseEntity.status(200).body(listaGrupo);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/crearGrupo")
	public ResponseEntity<String> crearGrupo(@RequestHeader(required = true) int curso,
						   @RequestHeader(required = true) String etapa) throws MatriculasException
	{
		
		CursoEtapaGrupoEntity cursoEtapaGrupo = new CursoEtapaGrupoEntity();
		
		List<CursoEtapaGrupoEntity> listaGrupos = this.iCursoEtapaGrupoRepository.findGrupoByCursoAndEtapaOrderByGrupoAsc(curso, etapa);
		
		if (listaGrupos.isEmpty())
		{
			IdCursoEtapaGrupo idCursoEtapaGrupo = new IdCursoEtapaGrupo(curso, etapa, 'A');
			
			cursoEtapaGrupo.setIdCursoEtapaGrupo(idCursoEtapaGrupo);
		}
		else
		{
			cursoEtapaGrupo = listaGrupos.get(0);
			
			char ultimaLetra = cursoEtapaGrupo.getIdCursoEtapaGrupo().getGrupo();
			
			ultimaLetra ++;
			
			if (ultimaLetra >= 'Z')
			{
				throw new MatriculasException(404, "No se pueden crear más grupos en este curso y etapa");
			}
			
			IdCursoEtapaGrupo idCursoEtapaGrupo = new IdCursoEtapaGrupo(curso, etapa, ultimaLetra);
			
			cursoEtapaGrupo.setIdCursoEtapaGrupo(idCursoEtapaGrupo);
		}
		
		iCursoEtapaGrupoRepository.saveAndFlush(cursoEtapaGrupo);
		
		return ResponseEntity.status(200).body("El grupo se ha creado correctamente");
			
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/GruposAlumnos")
	public ResponseEntity <?> obtenerAlumnosGrupo(@RequestHeader(required = true) int curso,
												  @RequestHeader(required = true) String etapa,
												  @RequestHeader(required = true) char grupo)throws MatriculasException
	{
		try
		{
			List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity>listaAlumnos = this.iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.encontrarAlumnosPorGrupo(curso, etapa, grupo);
			
			if (listaAlumnos.isEmpty())
			{
				throw new MatriculasException(404, "No se ha encontrado ningun Alumno");
			}
			return ResponseEntity.status(200).body(listaAlumnos);
		}
		catch (MatriculasException matriculasException)
		{
			String error = "Error del servidor";
			log.error(error);
			return ResponseEntity.status(500).body(error+matriculasException);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT,value = "/GruposAlumnos")
	public ResponseEntity <?> asignarAlumnosGrupo(@RequestBody(required = true) int curso,
												  @RequestBody(required = true) String etapa,
												  @RequestBody(required = true) char grupo,
												  @RequestBody(required = true) List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity> listaAlumnosAsignados )throws MatriculasException
	{
		
		try
		{
			if (listaAlumnosAsignados.isEmpty())
			{
				throw new MatriculasException(404, "No se ha seleccionado ningun alumno");
			}
			
			for ( DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity alumnos : listaAlumnosAsignados)
			{
				
				Optional <DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity> alumno = iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.findById(alumnos.getId());
				
				iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.saveAndFlush(alumno.get());
				
			}
			return ResponseEntity.status(200).body("Se han asignado los alumnos correctamente");
		}
		catch (MatriculasException matriculasException)
		{
			String error = "Error del servidor";
			log.error(error);
			return ResponseEntity.status(500).body(error+matriculasException);
		}
		
	}
	
	/*
	 * Endpoint para obtener todos los alumnos de un grupo
	 */
	@RequestMapping(method = RequestMethod.GET ,value = "/AlumnosGrupo")
	public ResponseEntity<?>obetenerAlumnosGrupo(
													@RequestParam(required = true)char grupo,
													@RequestParam(required = true)String etapa,
													@RequestParam(required = true)Integer curso
			)
	{
		try 
		{
			List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity>AlumnosGrupoList = iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.encontrarAlumnosPorGrupo(curso, etapa, grupo);
			if (AlumnosGrupoList.isEmpty())
			{
				throw new MatriculasException(404, "No se ha seleccionado ningun alumno");
			}
			return ResponseEntity.status(200).body(AlumnosGrupoList);
		} 
		catch (MatriculasException matriculasException)
		{
			String error = "Error del servidor";
			log.error(error);
			return ResponseEntity.status(500).body(error+matriculasException);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET ,value = "/AlumnosSinGrupo")
	public ResponseEntity<?>obetenerAlumnosSinGrupo(
													
													@RequestParam(required = true)String etapa,
													@RequestParam(required = true)Integer curso
			)
	{
		try 
		{
			List<DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity>AlumnosGrupoList = iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.encontrarAlumnosSinGrupo(curso, etapa);
			if (AlumnosGrupoList.isEmpty())
			{
				throw new MatriculasException(404, "No se ha seleccionado ningun alumno");
			}
			return ResponseEntity.status(200).body(AlumnosGrupoList);
		} 
		catch (MatriculasException matriculasException)
		{
			String error = "Error del servidor";
			log.error(error);
			return ResponseEntity.status(500).body(error+matriculasException);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE ,value = "/Alumnos")
	public ResponseEntity<?>borrarGrupo(
											@RequestParam(required = true)char grupo,
											@RequestParam(required = true)String etapa,
											@RequestParam(required = true)Integer curso
			
			)
	{
		iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.borrarGrupo(curso,etapa,grupo);
		return ResponseEntity.status(200).body("El grupo se ha borrado correctamente");
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE ,value = "/Alumnos")
	public ResponseEntity<?>borrarAlumno(
											@RequestBody(required = true) DatosBrutoAlumnoMatriculaEntity datosBrutoAlumnoMatriculaEntity
			
			)
	{
		iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.borrarGrupo(curso,etapa,grupo);
		return ResponseEntity.status(200).body("El grupo se ha borrado correctamente");
		
	}
}

