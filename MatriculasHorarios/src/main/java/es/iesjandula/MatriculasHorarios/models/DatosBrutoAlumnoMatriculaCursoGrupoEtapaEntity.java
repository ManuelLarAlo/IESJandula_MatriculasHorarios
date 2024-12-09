package es.iesjandula.MatriculasHorarios.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Datos_Bruto_Alumno_Matricula_curso_grupo_etapa")
public class DatosBrutoAlumnoMatriculaCursoGrupoEtapaEntity 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 50)
    private String nombre;
    
    @Column(length = 100)
    private String apellidos;
    
    @Column(length = 100)
    private String asignatura;

    @ManyToOne
    @JsonBackReference
    private CursoEtapaGrupoEntity cursoEtapaGrupo;
}
