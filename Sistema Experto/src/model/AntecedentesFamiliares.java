package model;

public class AntecedentesFamiliares {
	
	public int id_paciente;
	public int id_antecedentes_familiares;
	public String enfermedad;
	
	public static final String ENFERMEDAD_EnfermedadCrohn = "EnfermedadCrohn";
	public static final String ENFERMEDAD_ColitisUlcerosa = "ColitisUlcerosa";
	public static final String ENFERMEDAD_Uveitis = "Uveitis";
	public static final String ENFERMEDAD_EspondilitisAnquilosante = "EspondilitisAnquilosante";
	public static final String ENFERMEDAD_ArtritisReactiva = "ArtritisReactiva";
	public static final String ENFERMEDAD_ArtritisPsoriasica = "ArtritisPsoriasica";
	public static final String ENFERMEDAD_Psoriasis = "Psoriasis";
	public static final String ENFERMEDAD_EspondiloartritisIndiferenciada = "EspondiloartritisIndiferenciada";
	public static final String ENFERMEDAD_EspondiloartritisJuvenil = "EspondiloartritisJuvenil";
	public static final String ENFERMEDAD_ArtritisReumatoide = "ArtritisReumatoide";

	public AntecedentesFamiliares(int id_paciente, int id_antecedentes_familiares, String enfermedad ){
		
		this.id_paciente = id_paciente;
		this.id_antecedentes_familiares = id_antecedentes_familiares;
		this.enfermedad = enfermedad;
		
		
	}


}
