(deftemplate paciente
	(slot id_paciente (type INTEGER))
	(slot dni (type INTEGER))
	(slot nombre)
	(slot apellido)
	(slot sexo (type SYMBOL)
			(default masculino)
		   (allowed-values masculino femenino))
	(slot edad (type INTEGER))
)
(deftemplate dolor
	(slot id_diagnostico)
	(slot id_dolor)
	(slot zona(type SYMBOL)
		(allowed-values nil
						columnalumbar 
						columnadorsal 
						columnacervical
						articular
						intestinal
						ocular
						piel)
		(default nil))
	(slot origen_dolor(type SYMBOL)
		(allowed-values nil
						levantopeso
						muchoejercicio
						malapostura
						desconocido)
		(default nil))
	(slot lodespiertanoche (type SYMBOL)
		(default no)
		(allowed-values no
						si )
					)
	(slot mejoria(type SYMBOL)
		(allowed-values nil
						enreposo
						conactividad )						
		(default nil))
	(slot edad_inicio_dolor ( type INTEGER ) )
	(slot meses_persistencia(type INTEGER))
	(slot aspecto (type SYMBOL )
			( default Normal )
			( allowed-values Colorado Normal )
	)
	(slot inflamacion (type SYMBOL ) ( allowed-values Mucha Poca Ninguna ) (default Ninguna ) )	
	(slot ritmo_evacuatorio ( type SYMBOL ) ( allowed-values Normal Diarrea Moco Pus Sangre ) (default Normal ) )
)
(deftemplate antecedente_paciente
	(slot id_paciente (type INTEGER))
	(multislot enfermedad
		(type SYMBOL)
		(allowed-values 
			Colitis
			Dactilitis
			Entesitis
			Uveitis
			InfeccionGastrointestinal
			InfeccionUrogenital
			Psoriasis
			EnfermedadCrohn
			ColitisUlcerosa)
			)
)
(deftemplate antecedente_familiar
	(slot id_paciente
		(type INTEGER))
	(multislot enfermedad
		(type SYMBOL)
		(allowed-values 
			EnfermedadCrohn
			ColitisUlcerosa
			Uveitis
			EspondilitisAnquilosante
			ArtritisReactiva
			ArtritisPsoriasica
			Psoriasis
			EspondiloartritisIndiferenciada
            EspondiloartritisJuvenil
            ArtritisReumatoide )
     )
)
(deftemplate laboratorio
	(slot id_laboratorio
		(type INTEGER))
	(slot id_paciente
		(type INTEGER))
	(slot ERS
		(type FLOAT))
	(slot PCR
		(type FLOAT))
)
(deftemplate estudio
	(slot id_estudio
		(type INTEGER))
	(slot id_paciente
		(type INTEGER))
	(slot estado
		(type SYMBOL)
		(default Ninguno)
		(allowed-values Ninguno Solicitado Realizado))
	(slot tipo_estudio
		(default nil)
		(type SYMBOL)
		(allowed-values nil GEN RX RMN)
		)
	(slot resultado
		(type SYMBOL)
		(default nil)
		(allowed-values nil Pendiente Normal Sacroilitis Positivo Negativo)
	)
)

(deftemplate diagnostico
	(slot id_paciente)
	(slot id_diagnostico)
	(slot fecha)
	(slot dolorlumbar (type SYMBOL)
		(default nil )
		(allowed-values 
				nil
				notiene
				inflamatorio
				mecanico
				)
	)
	(slot gradosospecha (type SYMBOL)
		(default NoHaySospechaSpax)
		(allowed-values 
				NoHaySospechaSpax
				HaySospechaSpax
				AltaProbSpax	
				)
				)
				
	(slot enfermedad(type SYMBOL)
		(default NoHaySuficienteInformacion)
		(allowed-values 
					NoHaySuficienteInformacion
					EII 
					Uveitis
					EspondilitisAnquilosante
					ArtritisReactiva
					ArtritisPsoriasica
                    EspondiloartritisIndiferenciada
					EspondiloartritisJuvenil
					ProblemaDegenerativo
					Psoriasis
					)
				)
	(slot derivacion(type SYMBOL)
		(default MedicoClinico)
		(allowed-values
					Ninguna
					Reumatologo
					Gastroenterologo
					Oculista
					MedicoClinico
					Traumatologo
					Dermatologo
					)
					)

	(slot estudiossolicitados(type SYMBOL)
	(allowed-values 
					Ninguno
					ERS-PCR
					HLAB27
					RX
					RMN
					)
	(default Ninguno)
					)

)
( defrule REGLA_NOSPAX_TRAUM_1
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnacervical | columnadorsal  ) )
	=>	
	    ( modify  ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion Traumatologo ) ) 
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_TRAUM_1" crlf )
)

( defrule REGLA_NOSPAX_MECAN_1
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar nil ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) ( origen_dolor muchoejercicio | levantopeso | malapostura ))
	=>
	 	( modify  ?diag ( dolorlumbar mecanico ) ) 
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_MECAN_1" crlf )
)

( defrule REGLA_NOSPAX_NOLUMBAR_1
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar nil ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnacervical | columnadorsal | articular | ocular | piel | intestinal ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
	 	( modify  ?diag ( dolorlumbar notiene ) ) 
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_NOLUMBAR_1" crlf )
)

( defrule REGLA_HAYSPAX_REUMA_1
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		( test ( >= ?edad 45 ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico)  ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondiloartritisIndiferenciada ) ( derivacion Reumatologo ) ( estudiossolicitados Ninguno ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_1" crlf )
)		

( defrule REGLA_NOSPAX_CLINI_1
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		( test ( >= ?edad 45 ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico)  ( dolorlumbar mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then
		( modify ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad ProblemaDegenerativo ) ( derivacion MedicoClinico ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_CLINI_1" crlf )
)

( defrule REGLA_NOSPAX_TRAUM_2
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		( test ( < ?edad 45 ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico)  ( dolorlumbar mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then
		( modify ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion Traumatologo ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_TRAUM_2" crlf )
)

( defrule REGLA_HAYSPAX_INFLA_1
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar nil ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) ( origen_dolor desconocido ) ( mejoria conactividad ) ( meses_persistencia ?meses_persistencia ) 
		( lodespiertanoche si ) ( edad_inicio_dolor ?edad_inicio_dolor ) )		
		( test ( >= ?meses_persistencia 3 ) )
		( test ( < ?edad_inicio_dolor 45 ) )
	=>
		( modify  ?diag ( dolorlumbar inflamatorio ) ) 
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_INFLA_1" crlf )
)		

( defrule REGLA_HAYSPAX_INFLA_2
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar nil ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) ( origen_dolor ?origen_dolor ) ( meses_persistencia ?meses_persistencia ) ( lodespiertanoche ?lodespiertanoche ) ( mejoria ?mejoria ) ( edad_inicio_dolor ?edad_inicio_dolor ) )		
		( or ( test (  < ?meses_persistencia 3 ) )
		     ( test ( >= ?edad_inicio_dolor 45 ) ) 
			 ( test ( eq ?lodespiertanoche no ) )
			 ( test ( neq ?mejoria conactividad ) )
			 ( test ( neq ?origen_dolor desconocido ) ) )
	=>
		( modify  ?diag ( dolorlumbar mecanico ) ) 
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_INFLA_2" crlf )
)		

( defrule REGLA_NOSPAX_CLINI_2
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) ( mejoria conactividad ) ( meses_persistencia ?meses_persistencia ) 
		( lodespiertanoche si ) (edad_inicio_dolor ?edad_inicio_dolor) )		
		( test ( >= ?meses_persistencia 3 ) )
		( test ( >= ?edad_inicio_dolor 45 ) )
		( test ( >= ?edad 45 ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then
		( modify ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad ProblemaDegenerativo ) ( derivacion MedicoClinico ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_NOSPAX_CLINI_2" crlf )
)

( defrule REGLA_HAYSPAX_REUMA_2
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona columnalumbar ) )				
		( test ( < ?edad 16 ) )
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ( resultado Sacroilitis ) )	
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) ) 
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo ) 
		( estudiossolicitados Ninguno ) ) ) 
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_2" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_3 
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )				
		( test ( >= ?edad 16 ) )
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ( resultado Sacroilitis ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) ) 
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo ) 
		( estudiossolicitados Ninguno ) ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_3" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_5
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )  )	
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ( resultado Normal ) )
		( antecedente_paciente ( id_paciente ?id_paciente )  ( enfermedad  $?enfermedad ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) ) 
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondiloartritisIndiferenciada ) ( derivacion Reumatologo )
		( estudiossolicitados RMN )	) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_5" crlf )
)


( defrule  REGLA_HAYSPAX_REUMA_6
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )   )	
		( not ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ) )
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( antecedente_paciente ( id_paciente ?id_paciente )  ( enfermedad  $?enfermedad ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then ( bind ?count ( length$ $?enfermedad ) ) 
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondiloartritisIndiferenciada ) ( derivacion Reumatologo )
		( estudiossolicitados RX )	) )
		) 
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_6" crlf )
)	

( defrule  REGLA_HAYSPAX_REUMA_8
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )   )	
		( not ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ) ) 
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then ( bind ?count ( length$ $?enfermedad ) ) 
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion Reumatologo )
		( estudiossolicitados RX )	) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_8" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_10
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )   )	
		( not ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ) ) 
		( test ( < ?edad 16 ) )
		( laboratorio ( id_paciente ?id_paciente ) ( ERS ?ers ) (PCR ?pcr ) )  
		( test ( > ?ers 20 ) )
		( test ( > ?pcr 0.5 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then ( bind ?count ( length$ $?enfermedad ) ) 
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo )
		( estudiossolicitados RX )	) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_10" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_11
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )   )	
		( not ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) ) ) 
		( test (>= ?edad 16 ) )
		( laboratorio ( id_paciente ?id_paciente ) ( ERS ?ers ) (PCR ?pcr ) )  
		( test ( > ?ers 20 ) )
		( test ( > ?pcr 0.5 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then ( bind ?count ( length$ $?enfermedad ) ) 
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo )
		( estudiossolicitados RX )	) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_11" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_12
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona columnalumbar )  )	
		( not ( laboratorio ( id_paciente ?id_paciente ) ) )
		( test ( < ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Sacroilitis ) )  
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then ( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo ) 
		( estudiossolicitados Ninguno ) ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_12" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_13
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )  )	
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( test ( >= ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Sacroilitis ) )  
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo )  
		( estudiossolicitados Ninguno ) ) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_13" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_14
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )  )	
		( test ( < ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Normal ) )  
		( laboratorio ( id_paciente ?id_paciente ) ( ERS ?ers ) (PCR ?pcr ) )  
		( test ( > ?ers 20 ) )
		( test ( > ?pcr 0.5 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo ) ( estudiossolicitados RMN )) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_14" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_15
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar )   )	
		( test ( >= ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Normal ) )  
		( laboratorio ( id_paciente ?id_paciente ) ( ERS ?ers ) (PCR ?pcr ) )  
		( test ( > ?ers 20 ) )
		( test ( > ?pcr 0.5 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
	    ( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo ) ( estudiossolicitados RMN )
		) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_15" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_16
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )	
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( test ( < ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Normal ) )  
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Negativo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo ) ( estudiossolicitados RMN )
		) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_16" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_17
		( paciente ( id_paciente ?id_paciente )( edad ?edad ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona columnalumbar ) )	
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( test ( >= ?edad 16 ) )
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio RX ) (resultado Normal ) )  
		( not ( laboratorio ( id_paciente ?id_paciente ) ) ) 
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Negativo ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad  $?enfermedad ) )
	=>
		( bind ?count0 ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count0 1 ) then 
		( bind ?count ( length$ $?enfermedad ) )
		( if (>= ?count 2 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo ) ( estudiossolicitados RMN )
		) )
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_17" crlf )
)

( defrule  REGLA_HAYSPAX_DERMA_1
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico ) ( zona articular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		( or ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) ) 
		     ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis | ArtritisReumatoide $? ) ) 
	    )
	=>
		( bind ?count ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad ArtritisPsoriasica ) ( derivacion Dermatologo ) ( estudiossolicitados Ninguno) 
		) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_DERMA_1" crlf )
)

( defrule  REGLA_HAYSPAX_DERMA_2
		( paciente ( id_paciente ?id_paciente ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona piel ) ( aspecto Colorado ) (inflamacion Mucha ) )
		( or ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) )
		     ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) ) 
		)
	=>
	    ( bind ?count ( length$ (find-all-facts ((?fact dolor)) (eq ?fact:id_diagnostico ?id_diagnostico ) ) ) )
		( if (= ?count 1 ) then ( modify ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad Psoriasis ) ( derivacion Dermatologo ) ( estudiossolicitados Ninguno))
		)
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_DERMA_2" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_19
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona articular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona columnalumbar ) )  
		( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) )
		     ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis | ArtritisReumatoide $? ) 
			 )		
		
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad ArtritisPsoriasica ) ( derivacion Reumatologo )  ( estudiossolicitados RX )  )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_19" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_20
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona articular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona columnalumbar ) )
		( or ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) )
		     ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Psoriasis $? ) )	 
		)
	     
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad ArtritisPsoriasica ) ( derivacion Reumatologo )  ( estudiossolicitados Ninguno ))
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_20" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_21
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
	
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona articular ) ( aspecto Colorado ) (inflamacion Mucha ) )
	    		
		( or ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? InfeccionUrogenital $?) )
		     ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? InfeccionGastrointestinal $? ) )
	    )
		
		
		( or ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? ArtritisReactiva $? ) )
			 ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? ArtritisReumatoide $? ) )
		)
	     
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad ArtritisReactiva ) ( derivacion Reumatologo ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_21" crlf )
)

( defrule  REGLA_HAYSPAX_REUMA_22
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona intestinal ) ( ritmo_evacuatorio Diarrea | Moco | Pus | Sangre ) )
		
		( and ( not ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? InfeccionUrogenital $?) ) )
		      ( not ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? InfeccionGastrointestinal $? ) ) )
	    )
				
		( or ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? EnfermedadCrohn $? ) )
		     ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? ColitisUlcerosa $? ) )
        )
	=>
		( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EII ) ( derivacion Reumatologo )  ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_22" crlf )
			 
)

( defrule  REGLA_HAYSPAX_GASTRO_1
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ))
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona intestinal ) ( ritmo_evacuatorio Diarrea | Moco | Pus | Sangre ) )
		
		( or ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? EnfermedadCrohn $? ) )
			 ( antecedente_paciente( id_paciente ?id_paciente ) ( enfermedad $? ColitisUlcerosa $? ) )
		)
		
		( or ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? EnfermedadCrohn $? ) )
			 ( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? ColitisUlcerosa $? ) )
		)
	=>
		( modify ?diag ( gradosospecha AltaProbSpax ) ( enfermedad EII ) ( derivacion Gastroenterologo ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_GASTRO_1" crlf )
			 
)

( defrule  REGLA_HAYSPAX_REUMA_23
		( paciente ( id_paciente ?id_paciente )  )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona ocular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )

        ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis $? ) )
		
		( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis | EspondilitisAnquilosante | EspondiloartritisJuvenil $?  ) )
			        
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad Uveitis ) ( derivacion Reumatologo ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_23" crlf )
			 
)

( defrule  REGLA_HAYSPAX_REUMA_24
		( paciente ( id_paciente ?id_paciente ) ( sexo masculino  ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona ocular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  

        ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis $? ) )
		
		( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis | EspondilitisAnquilosante | EspondiloartritisJuvenil $? ) )
			    
		
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondilitisAnquilosante ) ( derivacion Reumatologo ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_24" crlf )	 
)

( defrule  REGLA_HAYSPAX_REUMA_25
		( paciente ( id_paciente ?id_paciente ) ( edad ?edad  ) )
		( test (< ?edad 16 ) )
		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona ocular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		
		( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Positivo ) )  

        ( antecedente_paciente ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis $?  ) )
		
		( antecedente_familiar ( id_paciente ?id_paciente ) ( enfermedad $? Uveitis | EspondilitisAnquilosante | EspondiloartritisJuvenil $? ) )
		
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad EspondiloartritisJuvenil ) ( derivacion Reumatologo ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_25" crlf )
			 
)

( defrule  REGLA_HAYSPAX_REUMA_26
		( paciente ( id_paciente ?id_paciente )  )

		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar inflamatorio ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona ocular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		
		( or ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) (resultado Negativo ) )  
		     ( not ( estudio ( id_paciente ?id_paciente ) ) )
		)

		( and ( not ( antecedente_paciente ( id_paciente ?id_paciente )  ) )
			  ( not ( antecedente_familiar ( id_paciente ?id_paciente )  ) )
		)
		
	=>
		( modify ?diag ( gradosospecha HaySospechaSpax ) ( enfermedad Uveitis ) ( derivacion Reumatologo ) ( estudiossolicitados ERS-PCR ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_REUMA_26" crlf )
			 
)

( defrule  REGLA_HAYSPAX_OCULI_1
		( paciente ( id_paciente ?id_paciente )  )

		?diag <- ( diagnostico( id_paciente ?id_paciente )( id_diagnostico ?id_diagnostico) ( dolorlumbar notiene | mecanico ) ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion MedicoClinico ) )
		
		
		( dolor ( id_diagnostico ?id_diagnostico )  ( zona ocular ) ( aspecto Colorado ) (inflamacion Mucha ) )
		
		( or ( estudio ( id_paciente ?id_paciente ) ( estado Realizado ) ( tipo_estudio GEN ) ( resultado Negativo ) )  
		     ( not ( estudio ( id_paciente ?id_paciente ) ) ) 
		)

		( and ( not ( antecedente_paciente ( id_paciente ?id_paciente )  )  )
			  ( not ( antecedente_familiar ( id_paciente ?id_paciente )  )  )
		)
		
	=>
		( modify ?diag ( gradosospecha NoHaySospechaSpax ) ( enfermedad NoHaySuficienteInformacion ) ( derivacion Oculista ) ( estudiossolicitados Ninguno ) )
		( printout t "PACIENTE: " ?id_paciente "REGLA_HAYSPAX_OCULI_1" crlf )
			 
)