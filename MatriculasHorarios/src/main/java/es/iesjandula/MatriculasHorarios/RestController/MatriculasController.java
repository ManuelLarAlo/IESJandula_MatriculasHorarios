package es.iesjandula.MatriculasHorarios.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
	
	
	/**
	 * Endpoint se encarga de subir a la base de datos los cursos
	 * @param csv
	 * @return
	 * @throws MatriculasException
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping( method = RequestMethod.POST,value = "/upload",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadCursos(
			@RequestPart( value = "csv") MultipartFile csv) throws MatriculasException, IllegalStateException, IOException
	{
		Scanner scanner = null;
		File file;
        file = File.createTempFile("temp", ".csv");
        csv.transferTo(file);
		try
		{
			scanner = new Scanner(file);
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
			return ResponseEntity.ok().build();

		}
		catch (FileNotFoundException fileNotFoundException) 
		{
			String error = "Error el archivo no existe";
			log.error(fileNotFoundException.getMessage());
			return ResponseEntity.status(500).body(error);
		}
		finally 
		{
			if(scanner!= null)
			{
				scanner.close();
			}
		}
		
	}
	
	/**
	 * Endpoint que se encarga de subir los alumnos a la base de datos cargados desde el csv
	 * @param curso curso del alumno
	 * @param etapa etapa educativa
	 * @param csv   csv en el cual estan todos los alumnos
	 * @return devuelve 200 si se ha cargado correctamente
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/cargaAlumnos", consumes = "multipart/form-data")
	public ResponseEntity<?> cargaMatriculas(
								@RequestHeader(required = true) int curso,
								@RequestHeader(required = true) String etapa,
								@RequestPart( value = "csv") MultipartFile csv) throws IOException 
	{
		Scanner scanner = null;
		try 
		{
			
			File file;
	        file = File.createTempFile("temp", ".csv");
	        csv.transferTo(file);
			scanner = new Scanner(file);
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				
				IdCursoEtapa idCursoEtapa         = new IdCursoEtapa();
				CursoEtapaEntity cursoEtapaEntity = new CursoEtapaEntity();
				DatosBrutoAlumnoMatriculaEntity datosBrutoAlumnoMatriculaEntity = new DatosBrutoAlumnoMatriculaEntity();
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				//rellenamos el la clave primaria de la tabla CursoEntity
				idCursoEtapa.setCurso(curso);
				idCursoEtapa.setEtapa(etapa);
				cursoEtapaEntity.setIdCursoEtapa(idCursoEtapa);
				datosBrutoAlumnoMatriculaEntity.setNombre(lineaDelFicheroTroceada[0]);
				datosBrutoAlumnoMatriculaEntity.setApellidos(lineaDelFicheroTroceada[1]);
				datosBrutoAlumnoMatriculaEntity.setAsignatura(lineaDelFicheroTroceada[2]);
				datosBrutoAlumnoMatriculaEntity.setCursoEtapa(cursoEtapaEntity);
				this.iDatosBrutoAlumnoMatricula.saveAndFlush(datosBrutoAlumnoMatriculaEntity);
			}
			return ResponseEntity.status(200).body("Alumnos cargados correctamente");
		} 
		catch (FileNotFoundException fileNotFoundException) 
		{
			String error = "Error el archivo no existe";
			log.error(fileNotFoundException.getMessage());
			return ResponseEntity.status(500).body(error);
		}
		finally 
		{
			if(scanner!= null)
			{
				scanner.close();
			}
		}
	}
	
	/**
	 * Endpoint que se encarga de subir los alumnos a la base de datos cargados desde el csv Para la segunda tabla
	 * @param csv   csv en el cual estan todos los alumnos
	 * @return devuelve 200 si se ha cargado correctamente
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/cargaAlumnos2tabla", consumes = "multipart/form-data")
	public ResponseEntity<?> cargaMatriculasGrupo(
								@RequestPart( value = "csv") MultipartFile csv) throws IOException 
	{
		Scanner scanner = null;
		try 
		{
			
			File file;
	        file = File.createTempFile("temp", ".csv");
	        csv.transferTo(file);
			scanner = new Scanner(file);
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				
				DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity datosBrutoAlumnoMatriculaEntity = new DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity();
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				datosBrutoAlumnoMatriculaEntity.setNombre(lineaDelFicheroTroceada[0]);
				datosBrutoAlumnoMatriculaEntity.setApellidos(lineaDelFicheroTroceada[1]);
				datosBrutoAlumnoMatriculaEntity.setAsignatura(lineaDelFicheroTroceada[2]);
				this.iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.saveAndFlush(datosBrutoAlumnoMatriculaEntity);
			}
			return ResponseEntity.status(200).body("Alumnos cargados correctamente");
		} 
		catch (FileNotFoundException fileNotFoundException) 
		{
			String error = "Error el archivo no existe";
			log.error(fileNotFoundException.getMessage());
			return ResponseEntity.status(500).body(error);
		}
		finally 
		{
			if(scanner!= null)
			{
				scanner.close();
			}
		}
	}
	
	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/Cursos")
	public ResponseEntity <?> obtenerCursos()
	{
		List<CursoEtapaEntity>listaCursos = this.iCursoRepository.findAll();
		
		if (listaCursos.isEmpty())
		{
			return ResponseEntity.status(404).body("No se han cargado los cursos");
		}
		
		return ResponseEntity.ok().body(listaCursos);
	}
	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/Alumnos")
	public ResponseEntity <?> obtenerAlumnos()
	{
		List<DatosBrutoAlumnoMatriculaEntity> ListadatosBrutoAlumnoMatriculaList= this.iDatosBrutoAlumnoMatricula.findAll();
		
		if (ListadatosBrutoAlumnoMatriculaList.isEmpty())
		{
			return ResponseEntity.status(404).body("No se ha encontrado ningun Alumno");
		}
		
		return ResponseEntity.status(200).body(ListadatosBrutoAlumnoMatriculaList);
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/Grupos")
	public ResponseEntity <?> obtenerGrupos(
												@RequestHeader(required = true) int curso,
												@RequestHeader(required = true) String etapa
										    ) 
	{
		List<CursoEtapaGrupoEntity>listaGrupo = this.iCursoEtapaGrupoRepository.findByIdCursoEtapaGrupoCursoAndIdCursoEtapaGrupoEtapa(curso, etapa);
		
		if (listaGrupo.isEmpty())
		{
			return ResponseEntity.status(404).body("No se ha encontrado ningun Grupo");
		}
		
		return ResponseEntity.status(200).body(listaGrupo);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/crearGrupo")
	public ResponseEntity<String> crearGrupo(
												@RequestHeader(required = true) int curso,
												@RequestHeader(required = true) String etapa)
	{
		
		CursoEtapaGrupoEntity cursoEtapaGrupo = new CursoEtapaGrupoEntity();
		
		List<CursoEtapaGrupoEntity> listaGrupos = this.iCursoEtapaGrupoRepository.findByIdCursoEtapaGrupoCursoAndIdCursoEtapaGrupoEtapaOrderByIdCursoEtapaGrupoGrupoAsc(curso, etapa);
		
		if (listaGrupos.isEmpty())
		{
			IdCursoEtapaGrupo idCursoEtapaGrupo = new IdCursoEtapaGrupo(curso, etapa, 'A');
			
			cursoEtapaGrupo.setIdCursoEtapaGrupo(idCursoEtapaGrupo);
		}
		else
		{
			 CursoEtapaGrupoEntity ultimoGrupo = listaGrupos.get(listaGrupos.size() - 1);
			
			char ultimaLetra = ultimoGrupo.getIdCursoEtapaGrupo().getGrupo();
			
			ultimaLetra ++;
			//mayor que z solo
			if (ultimaLetra > 'Z')
			{
				return ResponseEntity.status(404).body("No se pueden crear más grupos en este curso y etapa");
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
	public ResponseEntity <?> asignarAlumnosGrupo(@RequestHeader(required = true) int curso,
												  @RequestHeader(required = true) String etapa,
												  @RequestHeader(required = true) char grupo,
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
				if (iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.findById(alumnos.getId()).isEmpty())
				{
					throw new MatriculasException(405, "El alumno no existe");
				}
				IdCursoEtapaGrupo idcursoEtapaGrupo = new IdCursoEtapaGrupo(curso, etapa, grupo);
				CursoEtapaGrupoEntity cursoEtapaGrupo = new CursoEtapaGrupoEntity();
				cursoEtapaGrupo.setIdCursoEtapaGrupo(idcursoEtapaGrupo);
				alumnos.setCursoEtapaGrupo(cursoEtapaGrupo);
				
				iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.saveAndFlush(alumnos);
				
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
	
	@RequestMapping(method = RequestMethod.DELETE ,value = "/Grupos")
	public ResponseEntity<?>borrarGrupo(
											@RequestHeader(required = true)char grupo,
											@RequestHeader(required = true)String etapa,
											@RequestHeader(required = true)Integer curso) throws MatriculasException
	{
		CursoEtapaGrupoEntity cursoEtapaGrupo = new CursoEtapaGrupoEntity();
		IdCursoEtapaGrupo idCursoEtapaGrupo = new IdCursoEtapaGrupo(curso, etapa, grupo);
		cursoEtapaGrupo.setIdCursoEtapaGrupo(idCursoEtapaGrupo);
		
		if (iCursoEtapaGrupoRepository.findById(idCursoEtapaGrupo).isEmpty())
		{
			String error = "No se ha encontrado el grupo";
			log.error(error);
			
			return ResponseEntity.status(500).body(error);
		}
		
		iCursoEtapaGrupoRepository.delete(cursoEtapaGrupo);
		
		return ResponseEntity.status(200).body("El grupo se ha borrado correctamente");
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE ,value = "/Alumnos")
	public ResponseEntity<?>borrarAlumno(
											@RequestBody(required = true) DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity datosBrutoAlumnoMatriculaEntityAlumnoMatriculaCursoGrupoEtapaEntity
			
			)
	{
		iDatosBrutoAlumnoMatriculaCursoEtapaGrupo.borrarGrupo(datosBrutoAlumnoMatriculaEntityAlumnoMatriculaCursoGrupoEtapaEntity.getCursoEtapaGrupo().getIdCursoEtapaGrupo().getCurso(),
															  datosBrutoAlumnoMatriculaEntityAlumnoMatriculaCursoGrupoEtapaEntity.getCursoEtapaGrupo().getIdCursoEtapaGrupo().getEtapa(),
															  datosBrutoAlumnoMatriculaEntityAlumnoMatriculaCursoGrupoEtapaEntity.getCursoEtapaGrupo().getIdCursoEtapaGrupo().getGrupo());
		return ResponseEntity.status(200).body("El alumno se ha borrado del grupo correctamente");
		
	}
}

