package clips;

import java.sql.Date;
import java.util.ArrayList;

import CLIPSJNI.Environment;
import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import CLIPSJNI.Router;
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
	private Router router;
	
	public ClipsHandler() {
		env = new Environment();
		
		router = new Router("router");
		env.addRouter(router);
		
		env.watch(Environment.FACTS);
		env.watch(Environment.RULES);
		env.watch(Environment.STATISTICS);
		env.watch(Environment.ACTIVATIONS);
		
		env.load("./reglasV3.clp");
	}
	
	public Diagnostico correrConsulta(Consulta consulta) {
		agregarPaciente(consulta.paciente);
		
		agregarDolor(consulta.dolor1);
		agregarDolor(consulta.dolor2);
		
		agregarAntecedentesPaciente(consulta.antecedentes_paciente);
		agregarAntecedentesFamiliares(consulta.antecedentes_familiares);
		
		agregarEstudio(consulta.estudio_gen);
		agregarEstudio(consulta.estudio_rmn);
		agregarEstudio(consulta.estudio_rx);
		
		agregarLaboratorio(consulta.laboratorio);
		
		agregarDiagnostico(consulta.paciente.id_paciente, consulta.dolor1.id_diagnostico, consulta.paciente.ultima_modificacion);
		
		env.run();
		
		MultifieldValue pv = (MultifieldValue) env.eval("(find-all-facts((?J diagnostico))TRUE)");
		System.out.println( pv.toString() );
		try {
			for(int i = 0; i < 10; i++) {
				FactAddressValue fav =(FactAddressValue)pv.get(i);
				System.out.println( fav.toString() );
			}
			
			//FactAddressValue fav =(FactAddressValue)pv.get(0);
			
			//resultado = fav.getFactSlot("resultado").toString();//resultado_primera_etapa
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
			e1.printStackTrace();
		}
		
		// cargar a Diagnostico
		// mostrar 
		//consulta.diagnostico.actualizar(dolor_lumbar, grado_sospecha, enfermedad, derivacion, estudios_solicitados);
		return null;
	}
	
	private void agregarPaciente(Paciente p) {
		String entrada = "(paciente (id_paciente " + p.id_paciente
				+ ") (dni " + p.dni
				+ ") (nombre \"" + p.nombre
				+ "\") (apellido \"" + p.apellido
				+ "\") (sexo " + p.sexo
				+ ") (edad " + p.edad + ") )";
		
		System.out.println("[Paciente]: " + entrada);
		
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
				+ ") (meses_persistencia " + d.meses_persistencia
				+ ") (aspecto " + d.aspecto
				+ ") (inflamacion " + d.inflamacion
				+ ") (ritmo_evacuatorio " + d.ritmo_evacuatorio + ") )";
		
		System.out.println("[Dolor]: " + entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarAntecedentesPaciente(ArrayList<AntecedentesPaciente> a) {
		if(a.size() > 0) {
			String entrada = "(antecedente_paciente (enfermedad";
			
			for(int i = 0; i < a.size(); i++) {
				AntecedentesPaciente p = a.get(i);
				entrada = entrada + " " + p.enfermedad;
			}
			
			entrada = entrada + ") (id_paciente " + a.get(0).id_paciente + ") )";
			
			System.out.println("[Ante Pac]: " + entrada);
			
			env.assertString(entrada);
		}
	}
	
	private void agregarAntecedentesFamiliares(ArrayList<AntecedentesFamiliares> a) {
		if(a.size() > 0) {
			String entrada = "(antecedente_familiar (enfermedad";
			
			for(int i = 0; i < a.size(); i++) {
				AntecedentesFamiliares p = a.get(i);
				entrada = entrada + " " + p.enfermedad;
			}
			
			entrada = entrada + ") (id_paciente " + a.get(0).id_paciente + ") )";
			
			System.out.println(entrada);
			
			env.assertString("[Ante Fam]: " + entrada);
		}
	}
	
	private void agregarEstudio(Estudio e) {
		if(e == null)
			return;
		
		String entrada = "(estudio (id_estudio " + e.id_estudio
				+ ") (id_paciente " + e.id_paciente
				+ ") (estado " + e.estado
				+ ") (tipo_estudio " + e.tipo_analisis
				+ ") (resultado " + e.resultado + ") )";
		
		System.out.println("[Estudio]: " + entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarLaboratorio(Laboratorio l) {
		if(l == null)
			return;
		
		String entrada = "(laboratorio (id_laboratorio " + l.id_laboratorio
				+ ") (id_paciente " + l.id_paciente
				+ ") (ERS " + l.ERS
				+ ") (PCR " + l.PCR + ") )";
		
		System.out.println("[Laboratorio]: " + entrada);
		
		env.assertString(entrada);
	}
	
	private void agregarDiagnostico(int id_paciente, int id_diagnostico, Date fecha) {
		// ( diagnostico  ( id_paciente 104 ) ( id_diagnostico 5 ) ( fecha 23-6-2018 ) )
		String entrada = "(diagnostico (id_paciente " + id_paciente
				+ ") (id_diagnostico " + id_diagnostico
				+ ") (fecha " + fecha + ") )";
		
		System.out.println("[Diagnostico]: " + entrada);
		
		env.assertString(entrada);
	}
	
	public void limpiar() {
		env.reset();
	}
	
}
