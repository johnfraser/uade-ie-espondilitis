package model;

public class Dolor {
	
	public static final String ZONA_nil = "nil";
	public static final String ZONA_columnalumbar = "columnalumbar";
	public static final String ZONA_columnadorsal = "columnadorsal";	
	public static final String ZONA_columnacervical = "columnacervical";
	public static final String ZONA_articular = "articular";
	public static final String ZONA_intestinal = "intestinal";
	public static final String ZONA_ocular = "ocular";
	public static final String ZONA_piel = "piel";
	
	public static final String ORIGEN_DOLOR_nil = "nil";
	public static final String ORIGEN_DOLOR_levantopeso = "levantopeso";
	public static final String ORIGEN_DOLOR_muchoejercicio = "muchoejercicio";
	public static final String ORIGEN_DOLOR_malapostura = "malapostura";
	public static final String ORIGEN_DOLOR_desconocido = "desconocido";
	
	public static final String DESPIERTA_nil= "nil";
	public static final String DESPIERTA_no = "no";
	public static final String DESPIERTA_si = "si";
	
	public static final String MEJORIA_nil= "nil";
	public static final String MEJORIA_enreposo = "enreposo";
	public static final String MEJORIA_conactividad = "conactividad";
	
	public static final String ASPECTO_Normal= "Normal";
	public static final String ASPECTO_Colorado = "Colorado";
	
	public static final String INFLAMACION_Ninguna = "Ninguna";
	public static final String INFLAMACION_Poca = "Poca";
	public static final String INFLAMACION_Mucha = "Mucha";
	
	public static final String RITMO_EVACUATORIO_Normal = "Normal";
	public static final String RITMO_EVACUATORIO_Diarrea = "Diarrea";
	public static final String RITMO_EVACUATORIO_Moco = "Moco";
	public static final String RITMO_EVACUATORIO_Pus = "Pusa";
	public static final String RITMO_EVACUATORIO_Sangre = "Sangre";
	
	public int id_paciente;
	public int id_diagnostico;
	public int id_dolor;
	public String zona;
	public String origen_dolor;
	public String lodespiertanoche;
	public String mejoria;
	public int edad_inicio_dolor;
	public int meses_persistencia;
	public String aspecto;
	public String inflamacion;
	public String ritmo_evacuatorio;
	
	public Dolor (int id_paciente, int id_diagnostico, int id_dolor , String zona, String origen_dolor,  String lodespiertanoche, String mejoria, int edad_inicio_dolor , int meses_persistencia, String aspecto ,String inflamacion, String ritmo_evacuatorio ) {
		
		this.id_paciente = id_paciente;
		this.id_diagnostico = id_diagnostico;
		this.id_dolor = id_dolor;
		this.zona = zona;
		this.origen_dolor = origen_dolor;
		this.lodespiertanoche = lodespiertanoche;
		this.mejoria = mejoria;
		this.edad_inicio_dolor = edad_inicio_dolor;
		this.meses_persistencia = meses_persistencia;
		this.aspecto = aspecto;
		this.inflamacion = inflamacion;
		this.ritmo_evacuatorio = ritmo_evacuatorio;
		
		
	}
	
	

}
