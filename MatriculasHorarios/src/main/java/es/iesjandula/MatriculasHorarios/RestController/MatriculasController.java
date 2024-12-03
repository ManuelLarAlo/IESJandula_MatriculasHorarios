package es.iesjandula.MatriculasHorarios.RestController;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.iesjandula.MatriculasHorarios.models.AlumnoEntity;
import es.iesjandula.MatriculasHorarios.models.CursoEtapaEntity;
import es.iesjandula.MatriculasHorarios.models.MatriculaEntity;
import es.iesjandula.MatriculasHorarios.repositories.IAlumnoRepository;
import es.iesjandula.MatriculasHorarios.repositories.ICursoEtapaRepository;
import es.iesjandula.MatriculasHorarios.repositories.IMatriculaRepository;

@RestController
@RequestMapping(value = "/Matriculas")
public class MatriculasController {
	
	
	@Autowired
	private IMatriculaRepository iMatriculaRepository;
	
	@Autowired
	private IAlumnoRepository iAlumnoRepository;
	
	@Autowired
	private ICursoEtapaRepository iCursoRepository;
	
	
	@RequestMapping( method = RequestMethod.POST,value = "/upload",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadCursos(
			@RequestPart( value = "csv") MultipartFile csv 
			
			)
	{
		
		Scanner scanner;
		
		try
		{
			
			
			scanner = new Scanner(new File("src/main/resources/cursos.csv"));
			// PARSEAMOS LA FECHA CON UN FORMATO ESPECIFICO
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				CursoEtapaEntity cursoEtapaEntity =  new CursoEtapaEntity();
				String line = scanner.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				cursoEtapaEntity.setDatosBrutosAlumnosMatriculados(tokenizer.nextToken());		
				this.iCursoRepository.saveAndFlush(cursoEtapaEntity);
			}

			scanner.close();
		}
		catch(Exception exception)
		{
			log.error("Error de servidor",exception);
			return ResponseEntity.status(500).body("Error de servidor"+exception);
		}
		
		return ResponseEntity.ok().body(200);
	}
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/Cursos")
	public ResponseEntity <?> obtenerCursos() 
	{
		List<CursoEtapaEntity>listaCursos = this.iCursoRepository.findAll();
		return ResponseEntity.status(200).body(listaCursos);
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/Alumnos")
	public ResponseEntity <?> obtenerAlumnos(@RequestBody(required = true) MultipartFile fichero) throws StreamReadException, DatabindException, IOException{
		
		List<AlumnoEntity> alumnos= this.iAlumnoRepository.findAll();
		return ResponseEntity.status(200).body(alumnos);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/cargaAlumnos")
	public void cargaMatriculas(@RequestHeader(required = true) String curso,
								@RequestHeader(required = true) String etapa
			)
	{
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) 
		{
			String linea = scanner.nextLine();
			
			String [] valores = linea.split(",");
			
			Alumno alumno = new Alumno();
			
			alumno.setNombre(valores[1]);
			
			alumno.setNombre(valores[0]);
			
			this.iAlumnoRepository.saveAllAndFlush(alumno);
			
			
			
						
					 			
		}
	}
	

}
