<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>

	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
	<title>CLUB DEPORTIVO ATLÉTICO ALBAIDA</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
		});
	</script>
	
	<style type="text/css">
		#accordion .card-header {
			color: #fff;
		    background-color: #4582EC;
		    border-color: #4582EC;
		}
		
		#accordion .card-body {
		    background-color: #cce4ff4d;;
		}
		
		#accordion .card-header button {
			color: #fff;
		}
		
		#collapseTwo div.logo {
			    background-image: url(../../resources/img/logo.png);
			    background-position: bottom center;
			    background-repeat: no-repeat;
			    background-size: contain;
			    margin-bottom: 1em;
			    padding-top: 0x;
			    height: 9em;
		}
		
		#collapseTwo div.cargo {
			font-weight: bold;
		}
		
		#collapseFour, #collapseOne {
			font-size: 0.9rem;
		}
		
		#collapseFour p, #collapseOne p {
			margin-top: 1em;
    		text-align: justify;
    		margin-left: 4em;
    		margin-right: 6em;
		}
		
		#collapseFour ul, #collapseOne ul {
			margin-top: 1em;
    		text-align: justify;
    		margin-left: 4em;
    		margin-right: 6em;
   		    font-family: Georgia, Cambria, "Times New Roman", Times, serif;
		}
		
		#collapseSix p {
			margin-top: 1em;
    		text-align: justify;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div id="accordion">
					<div class="card">
						<div class="card-header" id="headingOne">
							<h5 class="mb-0">
								<button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
									Introducción
								</button>
							</h5>
						</div>

						<div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
							<div class="card-body">
								<h2 style="margin-left: 2em; margin-right: 2em;">INTRODUCCIÓN</h2>
								<p>
									El <b>CLUB DEPORTIVO ATLÉTICO ALBAIDA</b> nace en base a una necesidad existente en las distintas actividades deportivas que se realizaban 
									principalmente en el Polideportivo Municipal Príncipe Felipe de Albaida del Aljarafe. Antes de ser constituido el Club, la organización 
									deportiva de las actividades desarrolladas en el Polideportivo era gestionada directamente por el propio Ayuntamiento del Municipio 
									en forma de Escuelas Deportivas. 
								</p>
								<p>
									Con el crecimiento del número de actividades, equipos y de personas implicadas en estas actividades, se precisa la necesidad de componer 
									un Club Multideportivo que englobe y gestione todas las actividades desarrolladas.
								</p>
								<p>
									El <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> se crea en el año 2019 por los propios monitores de las distintas Escuelas Deportivas principalmente las escuelas de Voleibol, 
									Tenis, Baloncesto, Atletismo y Psicomotricidad.
								</p>
								<p>Actualmente el Club dispone de las siguientes escuelas:</p>
								<ul>
									<li>
										Voleibol
									</li>
									<li>
										Tenis
									</li>
									<li>
										Baloncesto
									</li>
									<li>
										Atletismo
									</li>
									<li>
										Psicomotricidad
									</li>
									<li>
										Padel
									</li>
								</ul>
								<p>
									Adicionalmente el <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> organiza distintas actividades lúdico-deportivas como pueden ser: Campamento de Verano, Senderismos, torneos de los 
									distintos deportes que practica, gymkhana deportiva, etc.
								</p>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingTwo">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
									Junta Directiva
								</button>
							</h5>
						</div>
						<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
							<div class="card-body">
								<div class="row">
									<c:forEach items="${directivas}" var="directiva">
										<div class="col-md-4">
											<div class="row">
												<div class="col-md-12 text-center logo">
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 text-center cargo">
													${directiva.cargo}
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 text-center nombre">
													${directiva.nombre}
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingThree">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
									Reglamento y Régimen Interno
								</button>
							</h5>
						</div>
						<div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
							<div class="card-body">
								Reglamento y Régimen Interno
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingFour">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
									Política y Privacidad
								</button>
							</h5>
						</div>
						<div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#accordion">
							<div class="card-body">
								<h2 style="margin-left: 2em; margin-right: 2em;">POLITICA DE PRIVACIDAD DEL FORMULARIO DE INSCRIPCIÓN EN LAS ACTIVIDADES DEPORTIVAS Y CULTURALES DEL CLUB DEPORTIVO ATLETICO ALBAIDA</h2>
								<p>
									De conformidad con lo dispuesto en la Ley Orgánica 15/1999 de 13 de diciembre, sobre Protección de Datos de Carácter Personal (en adelante, “LOPD”), 
									en su normativa de desarrollo, así como en el nuevo Reglamento General de Protección de datos (UE) 2016/679 de 27 de abril de 2016 (en adelante RGPD), 
									así como con las disposiciones de la Ley 34/2002 de 11 de julio, de Servicios de la Sociedad de la Información y de Comercio Electrónico, le informamos 
									que los datos de carácter personal que los usuarios suministren mediante nuestros formularios de registro durante su navegación a través de nuestros Sitios Web, 
									serán incorporados a un fichero de datos personales cuyo responsable es <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b>, con domicilio en Avenida de la Juventud S/N, 
									CP 41809 de Albaida del Aljarafe (Sevilla). Los datos incorporados al fichero se tratarán confidencialmente y con el nivel de protección que exige la LOPD, y su normativa de desarrollo 
									para el tipo de datos solicitados en los formularios.
								</p>
								<p>
									Todos los campos que aparecen en los formularios del Sitio Web serán de obligada cumplimentación, de tal modo que la omisión de alguno de ellos podrá comportar la 
									imposibilidad de que podamos atender su solicitud, salvo que en el propio formulario haya datos de cumplimentación voluntaria.
								</p>
								<p>
									Al aceptar la presente Política de Privacidad, acepta su contenido y al marcar las opciones que requieren de su consentimiento expreso, 
									nos lo otorga con las siguientes finalidades:
								</p>
								<ul>
									<li>
										Gestionar la inscripción en las actividades deportivas, culturales y de ocio que organiza y en las que participa el 
										CLUB DEPORTIVO ATLETICO ALBAIDA, la correcta identificación de los usuarios registrados que solicitan la inscripción y 
										participación en las actividades deportivas, culturales y de ocio de nuestra entidad.
									</li>
									<li>
										Remitirles, por cualquier medio, (incluyendo el envío de comunicaciones comerciales por correo electrónico o cualquier 
										otro medio de comunicación electrónica equivalente como SMS, MMS, mensaje WhatsApp, mensaje directo por redes sociales, etc.) 
										información de las actividades del Club, así como información, promociones y ofertas comerciales única y exclusivamente 
										de los patrocinadores de la entidad.
									</li>
									<li>
										La realización de estudios estadísticos de los usuarios registrados que permitan diseñar mejoras en los servicios prestados, sin elaboración de perfiles,
									</li>
									<li>
										Llevar a cabo tareas básicas de administración y poder comunicar incidencias, actos, eventos, resultados, o novedades a los usuarios registrados.
									</li>
								</ul>
								<p>
									Dichos datos serán conservados durante los plazos legalmente previstos.
								</p>
								<p>
									Como titular de los datos personales y la información que nos facilita, declara que los mismos son exactos y veraces y se compromete a comunicarnos cualquier modificación 
									a fin de que la información contenida en nuestro fichero esté en todo momento actualizada y no contenga errores. En cualquier caso, nos reservamos el derecho a excluir a 
									todo usuario que haya facilitado datos falsos, sin prejuicio de las demás acciones que procedan en Derecho.
								</p>
								<p>
									Mediante la aceptación de la presente Política de Privacidad, conoce y acepta que <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> podrá poner los datos personales a disposición 
									de los patrocinadores de la entidad deportiva, que estarán localizadas dentro del territorio nacional para las anteriores finalidades.
								</p>
								<p>
									En cualquier momento, y de conformidad con los procedimientos legalmente previstos, podrá ejercer sus derechos de acceso, limitación del tratamiento, rectificación, supresión 
									u oposición mediante petición por escrito firmada, con la indicación “Protección de Datos”, junto con copia de su DNI o documento equivalente que lo identifique dirigida a: 
									<b>CLUB DEPORTIVO ATLETICO ALBAIDA</b>, con domicilio en Avda. de la Juventud S/N, CP 41809 de Albaida del Aljarafe (Sevilla), o bien mediante correo electrónico dirigido a 
									atleticoalbaida@gmail.com . La cancelación de los datos puede hacerse también a través de la opción de contacto en el sitio web.
								</p>
								<p>
									<b>1.	Acceso a otros sitios web.</b>
 								</p>
								<p>
									El <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> declina cualquier responsabilidad respecto a la información a la que los usuarios accedan desde los sitios web de terceros que no estén 
									gestionados directamente por nuestro diseñador web. La función de los links que aparecen en los Sitios Web es exclusivamente la de informar al usuario sobre la existencia 
									de otras fuentes de información sobre la materia en Internet, donde podrá ampliar los datos ofrecidos en los sitios web de terceros. <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> no será 
									en ningún caso responsable de la información facilitada ni del resultado obtenido a través de dichos enlaces a sitios web de terceros. 
								</p>
								<p>
									<b>2.	Menores de edad.</b>
								</p>
								<p>
									Los formularios de datos para la inscripción están pensados para mayores de 14 años. Al cumplimentar el formulario de registro garantiza que es mayor de 14 años y será enteramente 
									responsable de esta declaración. Podremos, en cualquier momento, requerirle para que verifique su edad mediante la aportación del correspondiente documento identificativo. Bajo 
									sospecha de que un usuario es menor de edad, y de que ha falseado los datos que se requieren para su acceso, se podrá denegar al referido usuario el acceso a los servicios ofrecidos.
								</p>
								<p>
									No obstante, lo anterior, debido a su contenido, el sitio web puede resultar atractivo para dichos menores. En este sentido, <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b>> anima a los 
									padres o tutores a que supervisen las actividades electrónicas de sus hijos/as, por ejemplo, mediante el uso de herramientas de control paterno que ofrecen diversos servicios electrónicos 
									y fabricantes de software, y que ayudan a proporcionar un entorno electrónico seguro para los menores de edad. 
								</p>
								<p>
									<b>3.	Datos especialmente protegidos</b>
								</p>
								<p>
									Le informamos que algunas de las categorías de sus datos objeto de tratamiento son datos especialmente protegidos de acuerdo con el artículo 9 del Reglamento Europeo de Protección 
									de Datos, relativos a la salud.
								</p>
								<p>
									<b>4.	Cambios en nuestra Política de Privacidad.</b>
								</p>
								<p>
									Si en el futuro se produjeran cambios en nuestras prácticas y/o Política de Privacidad que pudieran afectar a sus datos personales le comunicaremos los cambios pertinentes a través 
									de nuestro Sitio Web o de alguna otra manera, a nuestra elección. Dichos cambios tendrán validez desde el momento en el que sean anunciados.
								</p>
								<p>
									En ningún caso dichas modificaciones afectarán a la elección que hubiera formulado en lo que respecta al modo en que <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> puede tratar sus datos. 
									Si en cualquier momento <b>CLUB DEPORTIVO ATLETICO ALBAIDA</b> desea utilizar sus datos de forma distinta a la establecida en el momento de la recogida, se lo notificará por correo 
									electrónico, solicitando su consentimiento.
								</p>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingTwo">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseFive" aria-expanded="false" aria-controls="collapseTwo">
									Patrocinadores
								</button>
							</h5>
						</div>
						<div id="collapseFive" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
							<div class="card-body">
								<div class="row">
									<c:forEach items="${patrocinadores}" var="patrocinador">
										<div class="col-md-4">
											<div class="row">
												<div class="col-md-12 text-center">
													<img src='data:${patrocinador.mime};base64,${patrocinador.imagen}' alt="Logo" class="foto" size="112" height="128"/>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 text-center cargo">
													${patrocinador.descripcion}
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 text-center">
													<c:if test="${patrocinador.email != null && patrocinador.email != ''}">
														<a href="mailto:${patrocinador.email}"><i class="fas fa-solid fa-envelope"></i></a>
													</c:if>
													<c:if test="${patrocinador.facebook != null && patrocinador.facebook != ''}">
														<a href="${patrocinador.facebook}" target="_blank" ><i class="fa fa-brands fa-facebook"></i></a>
													</c:if>
													<c:if test="${patrocinador.instagram != null && patrocinador.instagram != ''}">
														<a href="${patrocinador.instagram}" target="_blank" ><i class="fa fa-brands fa-instagram"></i></a>
													</c:if>
													<c:if test="${patrocinador.twitter != null && patrocinador.twitter != ''}">
														<a href="${patrocinador.twitter}" target="_blank" ><i class="fa fa-brands fa-twitter"></i></a>
													</c:if>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingFive">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseSix" aria-expanded="false" aria-controls="collapseFive">
									Instalaciones
								</button>
							</h5>
						</div>
						<div id="collapseSix" class="collapse" aria-labelledby="headingFive" data-parent="#accordion">
							<div class="card-body">
								Instalaciones
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header" id="headingSix">
							<h5 class="mb-0">
								<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseSeven" aria-expanded="false" aria-controls="collapseSix">
									Contacto
								</button>
							</h5>
						</div>
						<div id="collapseSeven" class="collapse" aria-labelledby="headingSix" data-parent="#accordion">
							<div class="card-body">
								<div class="row">
									<div class="col-md-7">
										<div class="mapouter">
											<div class="gmap_canvas">
												<iframe width="600" height="400" id="gmap_canvas" 
													src="https://maps.google.com/maps?q=Polideportivo%20y%20piscina%20municipal%20Albaida%20del%20aljarafe&t=k&z=17&ie=UTF8&iwloc=&output=embed" 
													frameborder="0" scrolling="no" marginheight="0" marginwidth="0">
												</iframe>
												<a href="https://123movies-i.net"></a>
												<br>
												<style>.mapouter{position:relative;text-align:right;height:400px;width:600px;}</style>
												<a href="https://www.embedgooglemap.net">
													embedgooglemap.net
												</a><style>.gmap_canvas {overflow:hidden;background:none!important;height:400px;width:600px;}</style>
											</div>
										</div>
									</div>
									<div class="col-md-5">
										<p><b>DIRECCIÓN</b></p>
										<p>
											Avenida de la Juventud, S/N<br/>
											41809 - Albaida del Aljarafe (Sevilla)
										</p>
										<p><b>EMAIL</b></p>
										<p>
											atleticoalbaida@gmail.com
										</p>
										<p><b>TELÉFONO</b></p>
										<p>
											644 178 769 - 635 454 524
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>