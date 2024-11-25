package es.iesjandula.MatriculasHorarios.RestController;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import es.iesjandula.MatriculasHorarios.models.MatriculaEntity;
import es.iesjandula.MatriculasHorarios.repositories.IAlumnoRepository;
import es.iesjandula.MatriculasHorarios.repositories.IMatriculaRepository;

@RestController
@RequestMapping(value = "/Matriculas")
public class MatriculasController {
	
	
	@Autowired
	private IMatriculaRepository iMatriculaRepository;
	
	@Autowired
	private IAlumnoRepository iAlumnoRepository;
	
	@RequestMapping(method = RequestMethod.GET,value = "/Cursos")
	public List<MatriculaEntity> getCursos() {
		
		return iMatriculaRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/Alumnos")
	public void cargarAlumnos(@RequestBody(required = true) MultipartFile fichero) throws StreamReadException, DatabindException, IOException{
		
		List<AlumnoEntity> alumnos= new ObjectMapper().readValue(fichero.getInputStream(), new TypeReference<List<AlumnoEntity>>(){});
		iAlumnoRepository.saveAllAndFlush(alumnos);
	}

}
