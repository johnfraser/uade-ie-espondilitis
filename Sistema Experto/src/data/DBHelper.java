package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.sqlite.SQLiteException;

import model.AntecedentesFamiliares;
import model.AntecedentesPaciente;
import model.Diagnostico;
import model.Dolor;
import model.Estudio;
import model.Laboratorio;
import model.Paciente;

public class DBHelper {
	
	 
	public static final int SQLITE_CONSTRAINT_PRIMARYKEY = 1555;
	public static final String SEPARADOR_COMA_SIMPLE = ", ";
	public static final String SEPARADOR_COMA_SIMPLE_A_TEXTO = ", '";
	public static final String SEPARADOR_COMA_TEXTO_A_TEXTO = "', '";
	public static final String SEPARADOR_COMA_TEXTO_A_SIMPLE = "', ";
	
	public static final String DATABASE_NAME = "Spax_DB.db";
	
	public static final String TABLE_Paciente_NAME = "Paciente";
	public static final String TABLE_Paciente_id_paciente = "id_paciente";
	public static final String TABLE_Paciente_Nombres = "Nombres";
	public static final String TABLE_Paciente_Apellidos = "Apellidos";
	public static final String TABLE_Paciente_DNI = "DNI";
	public static final String TABLE_Paciente_Edad = "Edad";
	public static final String TABLE_Paciente_Sexo = "Sexo";
	
	public static final String TABLE_Dolencias_NAME = "Dolencias";
	public static final String TABLE_Dolencias_id_dolor = "id_dolor";
	public static final String TABLE_Dolencias_id_paciente = "id_paciente";
	public static final String TABLE_Dolencias_id_diagnostico = "id_diagnostico";
	public static final String TABLE_Dolencias_mejoria = "mejoria";
	public static final String TABLE_Dolencias_persistencia = "persistencia";
	public static final String TABLE_Dolencias_edadinicio = "edadinicio";
	public static final String TABLE_Dolencias_origen = "origen";
	public static final String TABLE_Dolencias_despiertanoche = "despiertanoche";
	public static final String TABLE_Dolencias_zona = "zona";
	public static final String TABLE_Dolencias_ritmoevac = "ritmoevac";
	public static final String TABLE_Dolencias_aspecto = "aspecto";
	public static final String TABLE_Dolencias_inflamacion = "inflamacion";
	
	public static final String TABLE_Antecedente_NAME = "Antecedente";
	public static final String TABLE_Antecedente_id_antecedente = "id_antecedente";
	public static final String TABLE_Antecedente_id_paciente = "id_paciente";
	public static final String TABLE_Antecedente_tipoant = "tipoant";
	public static final String TABLE_Antecedente_tipoant_paciente = "paciente";
	public static final String TABLE_Antecedente_tipoant_familiares = "familiares";
	public static final String TABLE_Antecedente_enfermedad = "enfermedad";
	
	public static final String TABLE_Diagnostico_NAME = "Diagnostico";
	public static final String TABLE_Diagnostico_id_diagnostico = "id_diagnostico";
	public static final String TABLE_Diagnostico_id_paciente = "id_paciente";
	public static final String TABLE_Diagnostico_dolorlumbar = "dolorlumbar";
	public static final String TABLE_Diagnostico_gradosospecha = "gradosospecha";
	public static final String TABLE_Diagnostico_enfermedad = "enfermedad";
	public static final String TABLE_Diagnostico_derivacion = "derivacion";
	public static final String TABLE_Diagnostico_estudiossolic = "estudiossolic";
	public static final String TABLE_Diagnostico_ultima_modificacion = "ultima_modificacion";

	public static final String TABLE_Estudios_NAME = "Estudios";
	public static final String TABLE_Estudios_id_estudios = "id_estudios";
	public static final String TABLE_Estudios_id_paciente = "id_paciente";
	public static final String TABLE_Estudios_estado = "estado";
	public static final String TABLE_Estudios_tipo = "tipo";
	public static final String TABLE_Estudios_resultado = "resultado";
	
	public static final String TABLE_Laboratorio_NAME = "Laboratorio";
	public static final String TABLE_Laboratorio_id_laboratorio = "id_laboratorio";
	public static final String TABLE_Laboratorio_id_paciente = "id_paciente";
	public static final String TABLE_Laboratorio_ERS = "ERS";
	public static final String TABLE_Laboratorio_PCR = "PCR";
	




	public DBHelper() {
		// TODO Auto-generated constructor stub
	}

	
	public Connection getNewConnection() {
		
		Connection connection = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME );
		}
		catch (Exception e){	
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
		}

		return connection;
		
	}
	
	
	
	
	
	
	//Inserts en tablas	
	public int InsertPaciente ( Paciente row) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Paciente_NAME = "Paciente";
	public static final String TABLE_Paciente_id_paciente = "id_paciente";
	public static final String TABLE_Paciente_Nombres = "Nombres";
	public static final String TABLE_Paciente_Apellidos = "Apellidos";
	public static final String TABLE_Paciente_DNI = "DNI";
	public static final String TABLE_Paciente_Edad = "Edad";
	public static final String TABLE_Paciente_Sexo = "Sexo";
					 */
						
						insertRowSQL =  
								"INSERT INTO Paciente (id_paciente,Nombres,Apellidos,DNI,Edad,Sexo) " +
								"VALUES ("
								+ String.valueOf(row.id_paciente)
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.nombre
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.apellido
								+ SEPARADOR_COMA_TEXTO_A_SIMPLE
								+ String.valueOf(row.dni) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.edad)
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.sexo
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
					

				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
		
	}

	public int InsertDolor ( Dolor row) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
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
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Dolencias_NAME  
								+ "("
								+ TABLE_Dolencias_id_dolor
								+ ","
								+ TABLE_Dolencias_id_paciente
								+ ","
								+ TABLE_Dolencias_id_diagnostico
								+ ","
								+ TABLE_Dolencias_mejoria	
								+ ","
								+ TABLE_Dolencias_persistencia
								+ ","
								+ TABLE_Dolencias_edadinicio
								+ ","
								+ TABLE_Dolencias_origen
								+ ","
								+ TABLE_Dolencias_despiertanoche
								+ ","
								+ TABLE_Dolencias_zona
								+ ","
								+ TABLE_Dolencias_ritmoevac
								+ ","
								+ TABLE_Dolencias_aspecto
								+ ","
								+ TABLE_Dolencias_inflamacion
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_dolor) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_diagnostico)
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.mejoria
								+ SEPARADOR_COMA_TEXTO_A_SIMPLE
								+ String.valueOf( row.meses_persistencia )
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf( row.edad_inicio_dolor )
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.origen_dolor
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.lodespiertanoche
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.zona
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.ritmo_evacuatorio
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.aspecto
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.inflamacion
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
		
	}
	
	public int InsertDiagnostico ( Diagnostico row) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Diagnostico_NAME = "Diagnostico";
	public static final String TABLE_Diagnostico_id_diagnostico = "id_diagnostico";
	public static final String TABLE_Diagnostico_id_paciente = "id_paciente";
	public static final String TABLE_Diagnostico_dolorlumbar = "dolorlumbar";
	public static final String TABLE_Diagnostico_gradosospecha = "gradosospecha";
	public static final String TABLE_Diagnostico_enfermedad = "enfermedad";
	public static final String TABLE_Diagnostico_derivacion = "derivacion";
	public static final String TABLE_Diagnostico_estudiossolic = "estudiossolic";
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Diagnostico_NAME  
								+ "("
								+ TABLE_Diagnostico_id_diagnostico
								+ ","
								+ TABLE_Diagnostico_id_paciente
								+ ","
								+ TABLE_Diagnostico_dolorlumbar
								+ ","
								+ TABLE_Diagnostico_gradosospecha	
								+ ","
								+ TABLE_Diagnostico_enfermedad
								+ ","
								+ TABLE_Diagnostico_derivacion
								+ ","
								+ TABLE_Diagnostico_estudiossolic
								+ ","
								+ TABLE_Diagnostico_ultima_modificacion
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_diagnostico) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.dolor_lumbar
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.grado_sospecha
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.enfermedad
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.derivacion
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.estudios_solicitados
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.ultima_modificacion
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
	}

	public int InsertAntecedentePaciente ( AntecedentesPaciente row) {


		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Antecedente_NAME = "Antecedente";
	public static final String TABLE_Antecedente_id_antecedente = "id_antecedente";
	public static final String TABLE_Antecedente_id_paciente = "id_paciente";
	public static final String TABLE_Antecedente_tipoant = "tipoant";
	public static final String TABLE_Antecedente_enfermedad = "enfermedad";
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Antecedente_NAME  
								+ "("
								+ TABLE_Antecedente_id_antecedente
								+ ","
								+ TABLE_Antecedente_id_paciente
								+ ","
								+ TABLE_Antecedente_tipoant
								+ ","
								+ TABLE_Antecedente_enfermedad	
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_antecedentes_paciente) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ TABLE_Antecedente_tipoant_paciente
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.enfermedad
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
	}
		
	public int InsertAntecedenteFamiliares ( AntecedentesFamiliares row) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Antecedente_NAME = "Antecedente";
	public static final String TABLE_Antecedente_id_antecedente = "id_antecedente";
	public static final String TABLE_Antecedente_id_paciente = "id_paciente";
	public static final String TABLE_Antecedente_tipoant = "tipoant";
	public static final String TABLE_Antecedente_enfermedad = "enfermedad";
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Antecedente_NAME  
								+ "("
								+ TABLE_Antecedente_id_antecedente
								+ ","
								+ TABLE_Antecedente_id_paciente
								+ ","
								+ TABLE_Antecedente_tipoant
								+ ","
								+ TABLE_Antecedente_enfermedad	
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_antecedentes_familiares) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ TABLE_Antecedente_tipoant_familiares
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.enfermedad
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
		
	}

	public int InsertEstudio( Estudio row ) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Estudios_NAME = "Estudios";
	public static final String TABLE_Estudios_id_estudios = "id_estudios";
	public static final String TABLE_Estudios_id_paciente = "id_paciente";
	public static final String TABLE_Estudios_estado = "estado";
	public static final String TABLE_Estudios_tipo = "tipo";
	public static final String TABLE_Estudios_resultado = "resultado";
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Estudios_NAME  
								+ "("
								+ TABLE_Estudios_id_estudios
								+ ","
								+ TABLE_Estudios_id_paciente
								+ ","
								+ TABLE_Estudios_estado
								+ ","
								+ TABLE_Estudios_tipo	
								+ ","
								+ TABLE_Estudios_resultado
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_estudio) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE_A_TEXTO
								+ row.estado
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.tipo_analisis
								+ SEPARADOR_COMA_TEXTO_A_TEXTO
								+ row.resultado  
								+ "');";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
	}
	
	public int InsertLaboratorio( Laboratorio row ) {

		Connection connection;
		Statement statement;
		String insertRowSQL;
		int counter=0;

		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {
					
					/*
	public static final String TABLE_Laboratorio_NAME = "Laboratorio";
	public static final String TABLE_Laboratorio_id_estudios = "id_laboratorio";
	public static final String TABLE_Laboratorio_id_paciente = "id_paciente";
	public static final String TABLE_Laboratorio_ERS = "ERS";
	public static final String TABLE_Laboratorio_PCR = "PCR";
					 */
						
						insertRowSQL =  "INSERT INTO " + TABLE_Laboratorio_NAME  
								+ "("
								+ TABLE_Laboratorio_id_laboratorio
								+ ","
								+ TABLE_Laboratorio_id_paciente
								+ ","
								+ TABLE_Laboratorio_ERS	
								+ ","
								+ TABLE_Laboratorio_PCR
								+ ") "
								+
								"VALUES ("
								+ String.valueOf(row.id_laboratorio) 
								+ SEPARADOR_COMA_SIMPLE
								+ String.valueOf(row.id_paciente) 
								+ SEPARADOR_COMA_SIMPLE
								+ Float.toString(row.ERS)
								+ SEPARADOR_COMA_SIMPLE
								+ Float.toString(row.PCR) 
								+ ");";		
						statement.executeUpdate(insertRowSQL);
						counter++;
				}
				connection.commit();
				statement.close();	
			}
			connection.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//clave duplicada
			SQLiteException exception = ( SQLiteException) e;
			if ( exception.getResultCode().code == SQLITE_CONSTRAINT_PRIMARYKEY  ) {
//				lbStatus.setText("ERROR: El codigo ya existe");
//			}else {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
		
		return counter;
	}
	
	
	
	//Select de tablas
	
	public Paciente getPaciente( int parm_dni  ) {

		Connection connection;
		Statement statement;
		String selectSQL;
		ResultSet resultSet = null;
		Paciente resultado=null;

		selectSQL = "SELECT * from " + TABLE_Paciente_NAME + " WHERE DNI = " + String.valueOf(parm_dni) + " ;";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(selectSQL);
					if ( resultSet != null ) {
						
						while (  resultSet.next() ) {
							
							int id_paciente = Integer.valueOf(resultSet.getString(TABLE_Paciente_id_paciente));
							String nombre  = resultSet.getString(TABLE_Paciente_Nombres);
							String apellido  = resultSet.getString(TABLE_Paciente_Apellidos);
							int dni = Integer.valueOf(resultSet.getString(TABLE_Paciente_DNI));
							int edad = Integer.valueOf(resultSet.getString(TABLE_Paciente_Edad));
							String sexo  = resultSet.getString(TABLE_Paciente_Sexo);
							resultado = new Paciente(id_paciente, dni, nombre, apellido, edad, sexo);
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public Diagnostico getDiagnostico( int parm_id_diagnostico  ) {

		Connection connection;
		Statement statement;
		String selectSQL;
		ResultSet resultSet = null;
		Diagnostico resultado=null;

		selectSQL = "SELECT * from " + TABLE_Diagnostico_NAME + " WHERE id_diagnostico ='" + String.valueOf(parm_id_diagnostico) + "'; + ;";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(selectSQL);
					if ( resultSet != null ) {
						
						while (  resultSet.next() ) {
							
							/*
		this.id_diagnostico = id_diagnostico;
		this.id_paciente = id_paciente;
		this.dolor_lumbar = DOLOR_LUMBAR_NIL;
		this.grado_sospecha = GRADO_SOSPECHA_nil;
		this.enfermedad = ENFERMEDAD_nil;
		this.derivacion = DERIVACION_nil;
		this.estudios_solicitados = ESTUDIOS_SOLICITADOS_nil;
		java.util.Date utilDate = new java.util.Date(); 
		this.ultima_modificacion = new java.sql.Date(utilDate.getTime()); 
							 
							 */
							
							
							int id_diagnostico = Integer.valueOf(resultSet.getString(TABLE_Diagnostico_id_diagnostico));
							int id_paciente = Integer.valueOf(resultSet.getString(TABLE_Diagnostico_id_paciente));
							String dolor_lumbar  = resultSet.getString(TABLE_Diagnostico_dolorlumbar);
							String grado_sospecha  = resultSet.getString(TABLE_Diagnostico_gradosospecha);
							String enfermedad = resultSet.getString(TABLE_Diagnostico_enfermedad);
							String derivacion = resultSet.getString(TABLE_Diagnostico_derivacion);
							String estudios_solicitados = resultSet.getString(TABLE_Diagnostico_estudiossolic);
							//java.sql.Date ultima_modificacion = resultSet.getDate(TABLE_Diagnostico_ultima_modificacion);
							java.sql.Date ultima_modificacion = java.sql.Date.valueOf(resultSet.getString(TABLE_Diagnostico_ultima_modificacion));
							resultado = new Diagnostico(id_diagnostico, id_paciente, dolor_lumbar, grado_sospecha, enfermedad, derivacion, estudios_solicitados, ultima_modificacion);
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public Estudio getEstudioGen (int parm_id_paciente ) {
		
		Connection connection;
		Statement statement;
		String selectSQL;
		ResultSet resultSet = null;
		Estudio resultado=null;

		selectSQL = "SELECT * from "
		+ TABLE_Estudios_NAME 
		+ " WHERE " 
		+ TABLE_Estudios_id_paciente 
		+" = " 
		+ String.valueOf(parm_id_paciente)
		+ " AND "
		+ TABLE_Estudios_tipo
		+" = " 
		+"'"+ Estudio.ESTUDIO_GEN + "'"
		+ " AND "
		+ TABLE_Estudios_estado
		+" = " 
		+"'"+ Estudio.ESTADO_REALIZADO + "'"
		+ " ; ";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(selectSQL);
					if ( resultSet != null ) {
						
						while (  resultSet.next() ) {
							
							int id_paciente = Integer.valueOf(resultSet.getString(TABLE_Estudios_id_paciente));
							int id_estudio = Integer.valueOf(resultSet.getString(TABLE_Estudios_id_estudios));
							String tipo_analisis = resultSet.getString(TABLE_Estudios_tipo);
							String estado = resultSet.getString(TABLE_Estudios_estado);
							String resultado_estudio =  resultSet.getString(TABLE_Estudios_resultado);
							
							resultado = new Estudio(id_paciente, id_estudio, tipo_analisis, estado,resultado_estudio);
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	//Updates
	
	public void UpdatePaciente(Paciente row) {

		Connection connection;
		Statement statement;
		String setSystemParamSQL;
		
		setSystemParamSQL = "UPDATE "+ TABLE_Paciente_NAME  
				+ " set "
				+ TABLE_Paciente_Nombres + " ='" + row.nombre + "', "
				+ TABLE_Paciente_Apellidos + " ='" + row.apellido + "', "
				+ TABLE_Paciente_DNI + " = " + String.valueOf(row.dni) + ", "
				+ TABLE_Paciente_Edad + " = " + String.valueOf(row.edad) + ", "
				+ TABLE_Paciente_Sexo + " ='" + row.sexo + "' "
				+ "WHERE "
				+ TABLE_Paciente_id_paciente + " = " + String.valueOf(row.id_paciente)
				+" ;";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					statement.executeUpdate(setSystemParamSQL);
					connection.commit();
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//Utilidades
	public int getUltimoId(String tabla, String campo) {

		Connection connection;
		Statement statement;
		String selectSQL;
		ResultSet resultSet = null;
		int resultado=-1;

		selectSQL = "SELECT MAX( " + campo + ") FROM " + tabla + " ;";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(selectSQL);
					if ( resultSet != null ) {
						
						while (  resultSet.next() ) {			
							resultado = resultSet.getInt(1);
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	public int getUltimoId(String tabla, String campo, int cantidad_condiciones ,  String camp_condiciones[], String valor_condicion[]) {

		Connection connection;
		Statement statement;
		String selectSQL;
		ResultSet resultSet = null;
		int resultado=-1;
		int j = 0;

		selectSQL = "SELECT MAX( " + campo + ") FROM " + tabla + " WHERE ";
		
		for (int i = 0 ; i < cantidad_condiciones ; i ++) {
			
			selectSQL = selectSQL.concat(camp_condiciones[i] + " = " + valor_condicion[i] );
			
			j = i + 1;
			
			if ( j < cantidad_condiciones ) {
				
				selectSQL = selectSQL.concat(" AND ");
			}
		}
		
		selectSQL = selectSQL.concat(" ;");

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(selectSQL);
					if ( resultSet != null ) {
						
						while (  resultSet.next() ) {			
							resultado = resultSet.getInt(1);
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	public ArrayList<AntecedentesPaciente> getAntecedentesPaciente(int parm_id_paciente ){
		
		Connection connection;
		Statement statement;
		String validateCodeSQL;
		ResultSet resultSet = null;
		ArrayList<AntecedentesPaciente> rows=null;

		validateCodeSQL = "SELECT * FROM "
		+ TABLE_Antecedente_NAME 
		+ " WHERE " 
		+ TABLE_Antecedente_id_paciente 
		+ " = " 
		+ String.valueOf(parm_id_paciente)
		+ " AND "
		+ TABLE_Antecedente_tipoant 
		+ " = '" 
		+ TABLE_Antecedente_tipoant_paciente
		+ "' ;";

		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(validateCodeSQL);
					if ( resultSet != null ) {
						
						rows = new ArrayList<AntecedentesPaciente>();
						
						while (  resultSet.next() ) {
							
							rows.add(new AntecedentesPaciente(
									resultSet.getInt(TABLE_Antecedente_id_paciente),
									resultSet.getInt(TABLE_Antecedente_id_antecedente),
									resultSet.getString(TABLE_Antecedente_enfermedad)
									)
								);
							
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
		
		
		
	}
	
	public ArrayList<AntecedentesFamiliares> getAntecedentesFamiliares(int parm_id_paciente ){
		
		Connection connection;
		Statement statement;
		String validateCodeSQL;
		ResultSet resultSet = null;
		ArrayList<AntecedentesFamiliares> rows=null;

		validateCodeSQL = "SELECT * FROM "
		+ TABLE_Antecedente_NAME 
		+ " WHERE " 
		+ TABLE_Antecedente_id_paciente 
		+ " = " 
		+ String.valueOf(parm_id_paciente)
		+ " AND "
		+ TABLE_Antecedente_tipoant 
		+ " = '" 
		+ TABLE_Antecedente_tipoant_familiares
		+ "' ;";
		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(validateCodeSQL);
					if ( resultSet != null ) {
						
						rows = new ArrayList<AntecedentesFamiliares>();
						
						while (  resultSet.next() ) {
							
							rows.add(new AntecedentesFamiliares(
									resultSet.getInt(TABLE_Antecedente_id_paciente),
									resultSet.getInt(TABLE_Antecedente_id_antecedente),
									resultSet.getString(TABLE_Antecedente_enfermedad)
									)
								);
							
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
		
		
		
	}
	
	public ArrayList<Diagnostico> getDiagnosticosAnteriores(int parm_id_paciente){
		
		Connection connection;
		Statement statement;
		String validateCodeSQL;
		ResultSet resultSet = null;
		ArrayList<Diagnostico> rows=null;

		validateCodeSQL = "SELECT * FROM "
		+ TABLE_Diagnostico_NAME 
		+ " WHERE " 
		+ TABLE_Diagnostico_id_paciente 
		+ " = " 
		+ String.valueOf(parm_id_paciente)
		/*+ " AND "
		+ TABLE_Antecedente_tipoant 
		+ " = '" 
		+ TABLE_Antecedente_tipoant_familiares
		*/
		+ " ORDER BY " + TABLE_Diagnostico_id_diagnostico
		+ " ;";
		try {
			connection = getNewConnection();
			if ( connection != null) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				if ( statement != null ) {
					resultSet =	statement.executeQuery(validateCodeSQL);
					if ( resultSet != null ) {
						
						rows = new ArrayList<Diagnostico>();
						//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						
						while (  resultSet.next() ) {
							/*
							this.id_diagnostico = id_diagnostico;
							this.id_paciente = id_paciente;
							this.dolor_lumbar = DOLOR_LUMBAR_NIL;
							this.grado_sospecha = GRADO_SOSPECHA_nil;
							this.enfermedad = ENFERMEDAD_nil;
							this.derivacion = DERIVACION_nil;
							this.estudios_solicitados = ESTUDIOS_SOLICITADOS_nil;
							java.util.Date utilDate = new java.util.Date(); 
							this.ultima_modificacion = new java.sql.Date(utilDate.getTime());
							*/
							
							java.sql.Date date = java.sql.Date.valueOf(resultSet.getString(TABLE_Diagnostico_ultima_modificacion));
							
							rows.add(new Diagnostico(
									resultSet.getInt(TABLE_Diagnostico_id_diagnostico),
									resultSet.getInt(TABLE_Diagnostico_id_paciente),
									resultSet.getString(TABLE_Diagnostico_dolorlumbar),
									resultSet.getString(TABLE_Diagnostico_gradosospecha),
									resultSet.getString(TABLE_Diagnostico_enfermedad),
									resultSet.getString(TABLE_Diagnostico_derivacion),
									resultSet.getString(TABLE_Diagnostico_estudiossolic),
									date
									 )
								);
							
						}
						resultSet.close();
					}
					statement.close();
				}
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
	
	
	public void deleteAntecedentes(int parm_id_paciente) {
		
		Connection connection = null;
		Statement statement = null;
		String deleteSQL;
		deleteSQL = "DELETE FROM "
				+ TABLE_Antecedente_NAME 
				+ " WHERE " 
				+ TABLE_Antecedente_id_paciente 
				+ " = " 
				+ String.valueOf(parm_id_paciente)
				+ " ;";
		try {

			connection = getNewConnection();

			if (connection!=null) {

				connection.setAutoCommit(false);
				statement = connection.createStatement();
				
				if ( statement != null ) {

						statement.executeUpdate(deleteSQL);
						connection.commit();
				}
					
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if ( statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try { 
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public void test() {
		
		
	//	Paciente p = new Paciente(2, 30123123, "Juan", "Perez2", 30, "Masculino");
		
	//	InsertPaciente(p);
		
		Paciente b = getPaciente(30303303);
		
		System.out.println(b.id_paciente);
		System.out.println(b.nombre);
		System.out.println(b.apellido);
		System.out.println(b.dni);
		System.out.println(b.edad);
		System.out.println(b.sexo);
		
		/*
		int i = -1;
		i = getUltimoId(TABLE_Diagnostico_NAME, TABLE_Diagnostico_id_paciente);
		System.out.println(i);
		*/
	/*	
		b.nombre = "Cambiado"; 
		b.apellido ="Cambiado";
		b.dni = 30303303;
		b.edad = 99;
		b.sexo = "masculino";
		
		UpdatePaciente(b);
	*/	
	/*	
		int i = -1;
		String condiciones[] = {"id_paciente","id_diagnostico"};
		String valores[] = {"1","1"};
		
		i = getUltimoId(TABLE_Dolencias_NAME, TABLE_Dolencias_id_dolor , 2 ,condiciones, valores );
		System.out.println(i);
		*/
		
	//	ArrayList<AntecedentesPaciente> a1 = getAntecedentesPaciente(1);
	//	ArrayList<AntecedentesFamiliares> a2 = getAntecedentesFamiliares(1);
		
	//	deleteAntecedentes(1);
		
	}
	
	
	
	
	
	
	
	

}
