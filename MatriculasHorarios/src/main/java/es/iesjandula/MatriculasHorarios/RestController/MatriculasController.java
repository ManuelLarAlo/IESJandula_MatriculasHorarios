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
		
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(new File("src/main/resources/cursos.csv"));
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				IdCursoEtapa idCursoEtapa         = new IdCursoEtapa();
				CursoEtapaEntity cursoEtapaEntity = new CursoEtapaEntity();
				String line = scanner.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				idCursoEtapa.setCurso(Integer.parseInt(tokenizer.nextToken()));
				idCursoEtapa.setEtapa(tokenizer.nextToken());
				cursoEtapaEntity.setIdCursoEtapa(idCursoEtapa);
				this.iCursoRepository.saveAndFlush(cursoEtapaEntity);
			}
		}
		catch(Exception exception)
		{
			
			return ResponseEntity.status(500).body("Error de servidor"+exception);
		}
		finally 
		{
			scanner.close();
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
		Scanner scanner = null;
		IdCursoEtapa idCursoEtapa         = new IdCursoEtapa();
		CursoEtapaEntity cursoEtapaEntity = new CursoEtapaEntity();
		DatosBrutoAlumnoMatriculaEntity datosBrutoAlumnoMatriculaEntity = new DatosBrutoAlumnoMatriculaEntity();
		try 
		{
			//rellenamos el la clave primaria de la tabla CursoEntity
			idCursoEtapa.setCurso(curso);
			idCursoEtapa.setEtapa(etapa);
			cursoEtapaEntity.setIdCursoEtapa(idCursoEtapa);
			scanner = new Scanner(new File("src/main/resources/alumnos.csv"));
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				
				String line = scanner.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				datosBrutoAlumnoMatriculaEntity.setNombre(tokenizer.nextToken());
				datosBrutoAlumnoMatriculaEntity.setApellidos(tokenizer.nextToken());
				datosBrutoAlumnoMatriculaEntity.setAsignatura(tokenizer.nextToken());
				datosBrutoAlumnoMatriculaEntity.setCursoEtapa(cursoEtapaEntity);
				this.iDatosBrutoAlumnoMatricula.saveAndFlush(datosBrutoAlumnoMatriculaEntity); 	
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			scanner.close();
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

