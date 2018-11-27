INSERT INTO web_servicio_proceso(CODIGO_SERVICIO, COD_PROCESO, NOMBRE_PROCESO, ORDEN, ES_VISIBLE, CODIGO_TIPO_USU, URL, IMAGEN, APLICA_ADMOR)
  VALUES('77', '87', 'Montos Transaccionales', 'BI', 'S', 'W', '/montos/pagMontos?a=a', NULL, 'S')
GO


INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Nota', '*Los montos transaccionales registrados en este proceso quedarán configurados en el portal unificado ITAÚ para realizar las operaciones diarias', '*Los montos transaccionales registrados en este proceso quedarán configurados en el portal unificado ITAÚ para realizar las operaciones diarias')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Configuracion Montos', 'Configuración montos', 'Amounts Configuration')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Datos Basicos', 'Datos básicos', 'Basic Data')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Empresa', 'Empresa', 'Company')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Monto Diario Nomina y ACH', 'Monto diario pagos ach y nómina', 'Daily ACH/Paysheet Amount')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Monto Diario Portal', 'Monto diario transacciones portal', 'Daily Transactions Amount')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Montos', 'Monto', 'Amount')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Montos AzulNet', 'Montos transaccionales portafolio azulnet', 'Amount AzulNet')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Montos de Operacion Diaria', 'Montos de operación diaria', 'Daily Operation Amounts')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'NIT', 'Nit', 'Nit')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Transaccion', 'Transacción', 'Transaction')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Usuario', 'Usuario', 'User')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Transacciones PSE', 'N° Transacciones diarias', 'Number of daily transactions')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'Monto Diario PSE', 'Monto diario transacciones', 'Daily transactions amount')
GO
INSERT INTO web_multilenguaje(APLICACION, PAGINA, LABEL, ESPANOL, INGLES)
  VALUES('00', 'pagMontos', 'nombre', 'Nombre', 'Name')
GO

INSERT INTO BCR_COD_EVENT(NAMEEVENT, CODEVENT)
  VALUES('portal.montos.actualizacio.montos.tx', '6000003')
GO

INSERT INTO BCR_COD_SEVERITY(CODEVENT, RESPCODE, SEVERITY)
  VALUES('6000003', 'EXITO', 'INFO')
GO

INSERT INTO BCR_COD_SEVERITY(CODEVENT, RESPCODE, SEVERITY)
  VALUES('6000003', 'ERROR', 'ERROR')
GO



