INSERT INTO procesos(cod_proceso, nombre_proceso)
  VALUES('87', 'Montos Transaccionales')
GO

INSERT INTO servicios(codigo_servicio, descripcion, codigo_tipo_usu, estado, usuario_ins, aplicativo_ins, proceso_ins, fec_ins, usuario_mod, aplicativo_mod, proceso_mod, fec_mod, orden)
  VALUES('77', 'Montos Transaccionales', 'W', 'A', 'dialogo', '06', '01', '20020129 00:00:00.0', 'dialogo', '06', '01', '20020129 00:00:00.0', 'G')
GO


INSERT INTO seg_perfiles_servicio(cod_servicio, cod_perfil, nombre_perfil)
  VALUES('77', '03', 'Montos Transaccionales')
GO


INSERT INTO seg_procesos_perfil(cod_servicio, cod_perfil, cod_proceso)
  VALUES('77', '03', '87')
GO


INSERT INTO servicio_proceso(codigo_servicio, cod_proceso, nombre_proceso, orden, es_visible, codigo_tipo_usu, url, imagen, estado, usuario_ins, aplicativo_ins, proceso_ins, fec_ins, usuario_mod, aplicativo_mod, proceso_mod, fec_mod, aplica_admor)
  VALUES('77', '87', 'Montos Transaccionales', 'BI', 'S', 'W', '/montos/pagMontos', NULL, 'A', 'CAN', '06', '07', '20091204 21:47:15.220', 'CAN', '06', '07', '20091204 21:47:15.220', 'S')
GO

grant update on parametros_empresa to GS_seguridad
go

