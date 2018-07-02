package clips;

import java.sql.Date;
import java.util.ArrayList;

import CLIPSJNI.Environment;
import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import CLIPSJNI.Router;

/*
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;
import net.sf.clipsrules.jni.Router;
*/
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
		System.out.println("\n--- CLIPS INIT ---\n");
		env = new Environment();

		router = new ClipsRouter();
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
		
		if ( consulta.dolor2 != null) {
			agregarDolor(consulta.dolor2);
		}
		
		if ( consulta.antecedentes_paciente != null) {
			agregarAntecedentesPaciente(consulta.antecedentes_paciente);
		}
		
		if ( consulta.antecedentes_familiares != null) {
			agregarAntecedentesFamiliares(consulta.antecedentes_familiares);
		}

		if ( consulta.estudio_gen != null) {
			agregarEstudio(consulta.estudio_gen);
		}
		
		if ( consulta.estudio_rmn != null) {
			agregarEstudio(consulta.estudio_rmn);
		}
		if ( consulta.estudio_rx != null) {
			agregarEstudio(consulta.estudio_rx);
		}

		if ( consulta.laboratorio != null) {
			agregarLaboratorio(consulta.laboratorio);
		}

		agregarDiagnostico(consulta.paciente.id_paciente, consulta.dolor1.id_diagnostico,
				consulta.paciente.ultima_modificacion);
		
		Diagnostico diag = new Diagnostico(consulta.dolor1.id_diagnostico, consulta.paciente.id_paciente);
		
		System.out.println("\n--- CLIPS START ---\n");
		env.run();
		System.out.println("\n--- CLIPS DONE ---\n");

		MultifieldValue pv = (MultifieldValue) env.eval("(find-all-facts((?J diagnostico))TRUE)");
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);

			diag.actualizar(
					fav.getFactSlot("dolorlumbar").toString(),
					fav.getFactSlot("gradosospecha").toString(), 
					fav.getFactSlot("enfermedad").toString(),
					fav.getFactSlot("derivacion").toString(),
					fav.getFactSlot("estudiossolicitados").toString() );
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
			e1.printStackTrace();
		}

		return diag;
	}

	private void agregarPaciente(Paciente p) {
		String entrada = "(paciente (id_paciente " + p.id_paciente + ") (dni " + p.dni + ") (nombre \"" + p.nombre
				+ "\") (apellido \"" + p.apellido + "\") (sexo " + p.sexo + ") (edad " + p.edad + ") )";

		env.assertString(entrada);
	}

	private void agregarDolor(Dolor d) {
		if (d == null)
			return;

		StringBuilder e = new StringBuilder();

		e.append("(dolor (id_diagnostico " + d.id_diagnostico);
		e.append(") (id_dolor " + d.id_dolor);
		if (!d.zona.isEmpty())
			e.append(") (zona " + d.zona);
		if (!d.origen_dolor.isEmpty())
			e.append(") (origen_dolor " + d.origen_dolor);
		if (!d.lodespiertanoche.isEmpty())
			e.append(") (lodespiertanoche " + d.lodespiertanoche);
		if (!d.mejoria.isEmpty())
			e.append(") (mejoria " + d.mejoria);
		if (d.edad_inicio_dolor != 0)
			e.append(") (edad_inicio_dolor " + d.edad_inicio_dolor);
		if (d.meses_persistencia != 0)
			e.append(") (meses_persistencia " + d.meses_persistencia);
		if (!d.aspecto.isEmpty())
			e.append(") (aspecto " + d.aspecto);
		if (!d.aspecto.isEmpty())
			e.append(") (inflamacion " + d.inflamacion);
		if (!d.ritmo_evacuatorio.isEmpty())
			e.append(") (ritmo_evacuatorio " + d.ritmo_evacuatorio);
		e.append(") )");

		env.assertString(e.toString());
	}

	private void agregarAntecedentesPaciente(ArrayList<AntecedentesPaciente> a) {
		if (a.size() > 0) {
			StringBuilder entrada = new StringBuilder("(antecedente_paciente (enfermedad");

			for (int i = 0; i < a.size(); i++) {
				AntecedentesPaciente p = a.get(i);
				entrada.append(" " + p.enfermedad);
			}

			entrada.append(") (id_paciente " + a.get(0).id_paciente + ") )");

			env.assertString(entrada.toString());
		}
	}

	private void agregarAntecedentesFamiliares(ArrayList<AntecedentesFamiliares> a) {
		if (a.size() > 0) {
			StringBuilder entrada = new StringBuilder("(antecedente_familiar (enfermedad");

			for (int i = 0; i < a.size(); i++) {
				AntecedentesFamiliares p = a.get(i);
				entrada.append(" " + p.enfermedad);
			}

			entrada.append(") (id_paciente " + a.get(0).id_paciente + ") )");

			env.assertString(entrada.toString());
		}
	}

	private void agregarEstudio(Estudio e) {
		if (e == null)
			return;

		String entrada = "(estudio (id_estudio " + e.id_estudio + ") (id_paciente " + e.id_paciente + ") (estado "
				+ e.estado + ") (tipo_estudio " + e.tipo_analisis + ") (resultado " + e.resultado + ") )";

		env.assertString(entrada);
	}

	private void agregarLaboratorio(Laboratorio l) {
		if (l == null)
			return;

		String entrada = "(laboratorio (id_laboratorio " + l.id_laboratorio + ") (id_paciente " + l.id_paciente
				+ ") (ERS " + l.ERS + ") (PCR " + l.PCR + ") )";

		env.assertString(entrada);
	}

	private void agregarDiagnostico(int id_paciente, int id_diagnostico, Date fecha) {
		String entrada = "(diagnostico (id_paciente " + id_paciente + ") (id_diagnostico " + id_diagnostico
				+ ") (fecha " + fecha + ") )";

		env.assertString(entrada);
	}

	public void limpiar() {
		env.reset();
	}

}
