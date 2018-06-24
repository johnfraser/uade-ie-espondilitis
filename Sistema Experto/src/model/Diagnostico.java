package model;

import java.sql.Date;
import java.util.Set;

public class Diagnostico 
{
	public int id_diagnostico;
	public int id_paciente;
	
	
	public String dolor_lumbar;
	public static final String DOLOR_LUMBAR_NIL = "nil";
	public static final String DOLOR_LUMBAR_INFLAMATORIO = "inflamatorio";
	public static final String DOLOR_LUMBAR_MECANICO = "mecanico";
	
	public String grado_sospecha;
	public static final String GRADO_SOSPECHA_nil = "nil";
	public static final String GRADO_SOSPECHA_NoHaySospechaSpax = "NoHaySospechaSpax";
	public static final String GRADO_SOSPECHA_HaySospechaSpax = "HaySospechaSpax";
	public static final String GRADO_SOSPECHA_AltaProbSpax = "AltaProbSpax";
	
	public String enfermedad;
	public static final String ENFERMEDAD_nil = "nil";
	public static final String ENFERMEDAD_NoHaySuficienteInformacion = "NoHaySuficienteInformacion";
	public static final String ENFERMEDAD_EII = "EII";
	public static final String ENFERMEDAD_Uveitis = "Uveitis";
	public static final String ENFERMEDAD_EspondilitisAnquilosante = "EspondilitisAnquilosante";
	public static final String ENFERMEDAD_ArtritisReactiva = "ArtritisReactiva";
	public static final String ENFERMEDAD_ArtritisPsoriasica = "ArtritisPsoriasica";
	public static final String ENFERMEDAD_EspondiloartritisIndiferenciada = "EspondiloartritisIndiferenciada";
	public static final String ENFERMEDAD_EspondiloartritisJuvenil = "EspondiloartritisJuvenil";
	public static final String ENFERMEDAD_ProblemaDegenerativo = "ProblemaDegenerativo";
	public static final String ENFERMEDAD_Psoriasis = "Psoriasis";
	
	public String derivacion;
	public static final String DERIVACION_nil = "nil";
	public static final String DERIVACION_Ninguna = "Ninguna";
	public static final String DERIVACION_Reumatologo = "Reumatologo";
	public static final String DERIVACION_Gastroenterologo  = "Gastroenterologo";
	public static final String DERIVACION_Oculista = "Oculista";
	public static final String DERIVACION_MedicoClinico = "MedicoClinico";
	public static final String DERIVACION_Traumatologo = "Traumatologo";
	public static final String DERIVACION_Dermatologo = "Dermatologo";
	
	public String estudios_solicitados;
	public static final String ESTUDIOS_SOLICITADOS_nil = "nil";
	public static final String ESTUDIOS_SOLICITADOS_Ninguno = "Ninguno";
	public static final String ESTUDIOS_SOLICITADOS_ERS_PCR = "ERS-PCR";
	public static final String ESTUDIOS_SOLICITADOS_HLAB27 = "HLAB27";
	public static final String ESTUDIOS_SOLICITADOS_RX = "RX";
	public static final String ESTUDIOS_SOLICITADOS_RMN = "RMN";
	
	public Date ultima_modificacion;
	
	
	public Diagnostico(int id_diagnostico, int id_paciente ) { 
		this.id_diagnostico = id_diagnostico;
		this.id_paciente = id_paciente;
		this.dolor_lumbar = DOLOR_LUMBAR_NIL;
		this.grado_sospecha = GRADO_SOSPECHA_nil;
		this.enfermedad = ENFERMEDAD_nil;
		this.derivacion = DERIVACION_nil;
		this.estudios_solicitados = ESTUDIOS_SOLICITADOS_nil;
		java.util.Date utilDate = new java.util.Date(); 
		this.ultima_modificacion = new java.sql.Date(utilDate.getTime());
	}
	
	public void actualizar(String dolor_lumbar , String grado_sospecha, String enfermedad , String derivacion , String estudios_solicitados ) {
		
		this.dolor_lumbar = dolor_lumbar;
		this.grado_sospecha = grado_sospecha;
		this.enfermedad = enfermedad;
		this.derivacion = derivacion;
		this.estudios_solicitados = estudios_solicitados;
		java.util.Date utilDate = new java.util.Date(); 
		this.ultima_modificacion = new java.sql.Date(utilDate.getTime());
		
	}

}
