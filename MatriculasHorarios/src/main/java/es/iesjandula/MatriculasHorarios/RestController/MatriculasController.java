package es.iesjandula.MatriculasHorarios.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import es.iesjandula.MatriculasHorarios.models.CursoEtapaEntity;
import es.iesjandula.MatriculasHorarios.models.DatosBrutoAlumnoMatriculaEntity;
import es.iesjandula.MatriculasHorarios.models.ids.IdCursoEtapa;
import es.iesjandula.MatriculasHorarios.repositories.ICursoEtapaRepository;
import es.iesjandula.MatriculasHorarios.repositories.IDatosBrutoAlumnoMatricula;

@RestController
@RequestMapping(value = "/Matriculas")
public class MatriculasController 
{
	@Autowired
	private ICursoEtapaRepository iCursoRepository;
	
	@Autowired
	private IDatosBrutoAlumnoMatricula iDatosBrutoAlumnoMatricula;
	
	
	@RequestMapping( method = RequestMethod.POST,value = "/upload",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadCursos(
			@RequestPart( value = "csv") MultipartFile csv 
			
			)
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
								@RequestPart( value = "csv") MultipartFile csv 
			)
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
	public ResponseEntity <?> obtenerCursos() 
	{
		List<CursoEtapaEntity>listaCursos = this.iCursoRepository.findAll();
		return ResponseEntity.status(200).body(listaCursos);
	}
	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/Alumnos")
	public ResponseEntity <?> obtenerAlumnos()
	{
		List<DatosBrutoAlumnoMatriculaEntity> datosBrutoAlumnoMatriculaList= this.iDatosBrutoAlumnoMatricula.findAll();
		return ResponseEntity.status(200).body(datosBrutoAlumnoMatriculaList);
	}	
	

}

