-- Corregir las secuencias de las tablas

-- Para la tabla 'asiento'
SELECT setval('asiento_id_asiento_seq', (SELECT MAX(id_asiento) FROM public.asiento));

-- Para la tabla 'asiento_caracteristica'
SELECT setval('asiento_caracteristica_id_asiento_caracteristica_seq', (SELECT MAX(id_asiento_caracteristica) FROM public.asiento_caracteristica));

-- Para la tabla 'factura'
SELECT setval('factura_id_factura_seq', (SELECT MAX(id_factura) FROM public.factura));

-- Para la tabla 'factura_detalle_producto'
SELECT setval('factura_detalle_producto_id_factura_detalle_producto_seq', (SELECT MAX(id_factura_detalle_producto) FROM public.factura_detalle_producto));

-- Para la tabla 'factura_detalle_sala'
SELECT setval('factura_detalle_sala_id_factura_detalle_sala_seq', (SELECT MAX(id_factura_detalle_sala) FROM public.factura_detalle_sala));

-- Para la tabla 'pago'
SELECT setval('pago_id_pago_seq', (SELECT MAX(id_pago) FROM public.pago));

-- Para la tabla 'pago_detalle'
SELECT setval('pago_detalle_id_pago_detalle_seq', (SELECT MAX(id_pago_detalle) FROM public.pago_detalle));

-- Para la tabla 'pelicula'
SELECT setval('pelicula_id_pelicula_seq', (SELECT MAX(id_pelicula) FROM public.pelicula));

-- Para la tabla 'pelicula_caracteristica'
SELECT setval('pelicula_caracteristica_id_pelicula_caracteristica_seq', (SELECT MAX(id_pelicula_caracteristica) FROM public.pelicula_caracteristica));

-- Para la tabla 'producto'
SELECT setval('producto_id_producto_seq', (SELECT MAX(id_producto) FROM public.producto));

-- Para la tabla 'programacion'
SELECT setval('programacion_id_programacion_seq', (SELECT MAX(id_programacion) FROM public.programacion));

-- Para la tabla 'reserva'
SELECT setval('reserva_id_reserva_seq', (SELECT MAX(id_reserva) FROM public.reserva));

-- Para la tabla 'reserva_detalle'
SELECT setval('reserva_detalle_id_reserva_detalle_seq', (SELECT MAX(id_reserva_detalle) FROM public.reserva_detalle));

-- Para la tabla 'sala'
SELECT setval('sala_id_sala_seq', (SELECT MAX(id_sala) FROM public.sala));

-- Para la tabla 'sala_caracteristica'
SELECT setval('sala_caracteristica_id_sala_caracteristica_seq', (SELECT MAX(id_sala_caracteristica) FROM public.sala_caracteristica));

-- Para la tabla 'sucursal'
SELECT setval('sucursal_id_sucursal_seq', (SELECT MAX(id_sucursal) FROM public.sucursal));

-- Para la tabla 'tipo_asiento'
SELECT setval('tipo_asiento_id_tipo_asiento_seq', (SELECT MAX(id_tipo_asiento) FROM public.tipo_asiento));

-- Para la tabla 'tipo_pago'
SELECT setval('tipo_pago_id_tipo_pago_seq', (SELECT MAX(id_tipo_pago) FROM public.tipo_pago));

-- Para la tabla 'tipo_pelicula'
SELECT setval('tipo_pelicula_id_tipo_pelicula_seq', (SELECT MAX(id_tipo_pelicula) FROM public.tipo_pelicula));

-- Para la tabla 'tipo_producto'
SELECT setval('tipo_producto_id_tipo_producto_seq', (SELECT MAX(id_tipo_producto) FROM public.tipo_producto));

-- Para la tabla 'tipo_reserva'
SELECT setval('tipo_reserva_id_tipo_reserva_seq', (SELECT MAX(id_tipo_reserva) FROM public.tipo_reserva));

-- Para la tabla 'tipo_sala'
SELECT setval('tipo_sala_id_tipo_sala_seq', (SELECT MAX(id_tipo_sala) FROM public.tipo_sala));
