package model;

public class Consulta {
	
	public Paciente paciente;
	public Dolor dolor1;
	public Dolor dolor2;
	public Laboratorio laboratorio;
	public Estudio estudio_gen;
	public Estudio estudio_rx;
	public Estudio estudio_rmn;
	public AntecedentesPaciente antecedentes_paciente;
	public AntecedentesFamiliares antecedentes_familiares;
	public Diagnostico diagnostico;
	
	public Consulta() {
		
		this.paciente = null;
		this.dolor1 = null;
		this.dolor2 = null;
		this.laboratorio = null;
		this.estudio_gen = null;
		this.estudio_rx = null;
		this.estudio_rmn = null;
		this.antecedentes_paciente = null;
		this.antecedentes_familiares = null;
		this.diagnostico = null;
	}
	
	public void CrearPaciente(int id_paciente, int dni, String nombre, String apellido, int edad, String sexo) {
		
		this.paciente = new Paciente(id_paciente, dni, nombre, apellido, edad, sexo);
		
	}
	
	public void CrearDolor1(int id_paciente, int id_diagnostico, int id_dolor , String zona, String origen_dolor,  String lodespiertanoche, String mejoria, int edad_inicio_dolor , int meses_persistencia, String aspecto ,String inflamacion, String ritmo_evacuatorio) {
		
		this.dolor1 = new Dolor(id_paciente, id_diagnostico, id_dolor, zona, origen_dolor, lodespiertanoche, mejoria, edad_inicio_dolor, meses_persistencia, aspecto, inflamacion, ritmo_evacuatorio);
		
	}
	
	public void CrearDolor2(int id_paciente, int id_diagnostico, int id_dolor , String zona, String origen_dolor,  String lodespiertanoche, String mejoria, int edad_inicio_dolor , int meses_persistencia, String aspecto ,String inflamacion, String ritmo_evacuatorio) {
		
		this.dolor2 = new Dolor(id_paciente, id_diagnostico, id_dolor, zona, origen_dolor, lodespiertanoche, mejoria, edad_inicio_dolor, meses_persistencia, aspecto, inflamacion, ritmo_evacuatorio);
		
	}
	
	public void CrearLaboratorio(int id_laboratorio, int id_paciente, float ERS, float PCR) {
		
		this.laboratorio = new Laboratorio(id_laboratorio, id_paciente, ERS, PCR);
		
	}
	
	public void CrearEstudio_Gen(int id_paciente, int id_estudio, String tipo_analisis) {
		
		this.estudio_gen = new Estudio(id_paciente, id_estudio, tipo_analisis);
		
	}
	
	public void CrearEstudio_RX(int id_paciente, int id_estudio, String tipo_analisis) {
		
		this.estudio_rx = new Estudio(id_paciente, id_estudio, tipo_analisis);
	}
	
	public void CrearEstudio_RMN(int id_paciente, int id_estudio, String tipo_analisis) {
		
		this.estudio_rmn = new Estudio(id_paciente, id_estudio, tipo_analisis);
		
	}
	
	public void CrearAntecedentesPaciente(int id_paciente, int id_antecedentes_paciente, String enfermedad ) {
		
		this.antecedentes_paciente = new AntecedentesPaciente(id_paciente, id_antecedentes_paciente, enfermedad);
		
	}
	
	public void CrearAntecedentesFamiliares(int id_paciente, int id_antecedentes_familiares, String enfermedad ) {
		
		this.antecedentes_familiares = new AntecedentesFamiliares(id_paciente, id_antecedentes_familiares, enfermedad);
	}
	
	public void CrearAntecedentesDiagnostico(int id_diagnostico, int id_paciente) {
		
		this.diagnostico = new Diagnostico(id_diagnostico, id_paciente);
		
	}
	


}
