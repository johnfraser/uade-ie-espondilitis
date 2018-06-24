package model;

public class AntecedentesPaciente {
	
	public int id_paciente;
	public int id_antecedentes_paciente;
	public String enfermedad;
	
	public static final String ENFERMEDAD_Colitis = "Colitis";
	public static final String ENFERMEDAD_Dactilitis = "Dactilitis";
	public static final String ENFERMEDAD_Entesitis = "Entesitis";
	public static final String ENFERMEDAD_Uveitis = "Uveitis";
	public static final String ENFERMEDAD_InfeccionGastrointestinal = "InfeccionGastrointestinal";
	public static final String ENFERMEDAD_InfeccionUrogenital = "InfeccionUrogenital";
	public static final String ENFERMEDAD_Psoriasis = "Psoriasis";
	public static final String ENFERMEDAD_EnfermedadCrohn = "EnfermedadCrohn";
	public static final String ENFERMEDAD_ColitisUlcerosa = "ColitisUlcerosa";
	
	public AntecedentesPaciente(int id_paciente, int id_antecedentes_paciente, String enfermedad ){
		
		this.id_paciente = id_paciente;
		this.id_antecedentes_paciente = id_antecedentes_paciente;
		this.enfermedad = enfermedad;
		
		
	}
	

}
