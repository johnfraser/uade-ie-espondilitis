package model;

public class Laboratorio {
	
	public int id_laboratorio;
	public int id_paciente;
	public float ERS;
	public float PCR;
	
	public Laboratorio(int id_laboratorio, int id_paciente, float ERS, float PCR) {
	
		this.id_laboratorio = id_laboratorio;
		this.id_paciente = id_paciente;
		this.ERS = ERS;
		this.PCR = PCR;
		
	}
	
}
