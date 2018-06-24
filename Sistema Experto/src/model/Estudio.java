package model;

public class Estudio {
	
	public int id_estudio;
	public int id_paciente;
	public String tipo_analisis;
	public static final String ESTUDIO_GEN = "GEN";
	public static final String ESTUDIO_RX = "RX";
	public static final String ESTUDIO_RMN = "RMN";
	
	public String estado;
	public static final String ESTADO_NINGUNO = "Ninguno";
	public static final String ESTADO_SOLICITADO = "Solicitado";
	public static final String ESTADO_REALIZADO = "Realizado";
	
	public String resultado;
	public static final String RESULTADO_PENDIENTE = "Pendiente";
	public static final String RESULTADO_NORMAL = "Normal";
	public static final String RESULTADO_SACROILITIS = "Sacroilitis";
	public static final String RESULTADO_POSITIVO = "Positivo";
	public static final String RESULTADO_NEGATIVO = "Negativo";
	
	
	public Estudio (int id_paciente, int id_estudio, String tipo_analisis ) {
		this.id_estudio = id_estudio;
		this.id_paciente = id_paciente;
		this.tipo_analisis = tipo_analisis;
		this.estado = ESTADO_NINGUNO;
		this.resultado =  RESULTADO_PENDIENTE;	
	}
	
}
