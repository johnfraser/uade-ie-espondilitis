package clips;

import CLIPSJNI.Environment;
import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import model.AntecedentesFamiliares;
import model.AntecedentesPaciente;
import model.Consulta;
import model.Diagnostico;
import model.Dolor;
import model.Estudio;
import model.Laboratorio;
import model.Paciente;

public class ClipsHandler {
	
	private Environment env;
	
	public ClipsHandler() {
		env = new Environment();
		
		env.watch(Environment.FACTS);
		env.watch(Environment.RULES);
		env.watch(Environment.STATISTICS);
		env.watch(Environment.ACTIVATIONS);
		
		env.load("./reglasV3.clp");
	}
	
	public boolean correrConsulta(Consulta consulta) {
		agregarPaciente(consulta.paciente);
		
		agregarDolor(consulta.dolor1);
		agregarDolor(consulta.dolor2);
		
		//agregarAntecedentesPaciente(consulta.antecedentes_paciente);
		//agregarAntecedentesFamiliares(consulta.antecedentes_familiares);
		
		agregarEstudio(consulta.estudio_gen);
		agregarEstudio(consulta.estudio_rmn);
		agregarEstudio(consulta.estudio_rx);
		
		agregarLaboratorio(consulta.laboratorio);
		
		env.run();
		
		
		// TODO
		MultifieldValue pv = (MultifieldValue) env.eval("(find-all-facts((?J diagnostico))TRUE)");
		
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			//resultado = fav.getFactSlot("resultado").toString();//resultado_primera_etapa
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
			//e1.printStackTrace();
		}
		
		// cargar a Diagnostico
		// mostrar 
		//consulta.diagnostico.actualizar(dolor_lumbar, grado_sospecha, enfermedad, derivacion, estudios_solicitados);
		return true;
	}
	
	private void agregarPaciente(Paciente p) {
		String entrada = "(paciente (id_paciente " + p.id_paciente
				+ ") (dni " + p.dni
				+ ") (nombre \"" + p.nombre
				+ "\") (apellido \"" + p.apellido
				+ "\") (sexo " + p.sexo
				+ ") (edad " + p.edad + ") )";
		
		System.out.println(entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarDolor(Dolor d) {
		if(d == null)
			return;
		
		// TODO: filtrar segun caso de dolor
		String entrada = "(dolor (id_diagnostico " + d.id_diagnostico
				+ ") (id_dolor " + d.id_dolor
				+ ") (zona " + d.zona
				+ ") (origen_dolor " + d.origen_dolor
				+ ") (lodespiertanoche " + d.lodespiertanoche
				+ ") (mejoria " + d.mejoria
				+ ") (edad_inicio_dolor " + d.edad_inicio_dolor
				+ ") (meses_persistencia "+ d.meses_persistencia + ") )";
		
		System.out.println(entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarAntecedentesPaciente(AntecedentesPaciente a) {
		// TODO: for each antecedente
		// el objeto debería tener varios antecedentes
	}
	
	private void agregarAntecedentesFamiliares(AntecedentesFamiliares a) {
		// TODO: for each antecedente
		// el objeto debería tener varios antecedentes
	}
	
	private void agregarEstudio(Estudio e) {
		String entrada = "(estudio (id_estudio " + e.id_estudio
				+ ") (id_paciente " + e.id_paciente
				+ ") (estado " + e.estado
				+ ") (tipo_estudio " + e.tipo_analisis
				+ ") (resultado " + e.resultado + ") )";
		
		System.out.println(entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarLaboratorio(Laboratorio l) {
		// TODO: 
	}
	
	public void limpiar() {
		env.reset();
	}
	
}
