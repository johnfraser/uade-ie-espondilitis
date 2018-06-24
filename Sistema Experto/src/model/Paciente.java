package model;

import java.sql.Date;

public class Paciente {

	public int id_paciente;
	public int dni;
	public String nombre;
	public String apellido;
	public Integer edad;
	public String sexo;
	public static final String SEXO_MASCULINO = "masculino";
	public static final String SEXO_FEMENINO = "femenino";
	
	public Date ultima_modificacion;// (de cuando fue guardado el perfil de paciente)
	
	public Paciente(int id_paciente, int dni, String nombre, String apellido, int edad, String sexo) {
		
		this.id_paciente = id_paciente;
		this.dni = dni;
		this.nombre =  nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.sexo = sexo;
		java.util.Date utilDate = new java.util.Date(); 
		this.ultima_modificacion = new java.sql.Date(utilDate.getTime());

	}
	
}
