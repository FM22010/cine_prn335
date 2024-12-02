CREATE TABLE IF NOT EXISTS public.asiento
(
    id_asiento bigserial NOT NULL,
    id_sala integer,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    CONSTRAINT pk_asiento PRIMARY KEY (id_asiento)
    );

CREATE TABLE IF NOT EXISTS public.asiento_caracteristica
(
    id_asiento_caracteristica bigserial NOT NULL,
    id_asiento bigint,
    id_tipo_asiento integer,
    valor text COLLATE pg_catalog."default",
    CONSTRAINT pk_asiento_caracteristica PRIMARY KEY (id_asiento_caracteristica)
    );

CREATE TABLE IF NOT EXISTS public.factura
(
    id_factura bigserial NOT NULL,
    cliente character varying(255) COLLATE pg_catalog."default",
    dui character varying(155) COLLATE pg_catalog."default",
    fecha timestamp with time zone,
                        comentarios text COLLATE pg_catalog."default",
                        CONSTRAINT pk_factura PRIMARY KEY (id_factura)
    );

CREATE TABLE IF NOT EXISTS public.factura_detalle_producto
(
    id_factura_detalle_producto bigserial NOT NULL,
    id_factura bigint,
    id_producto bigint,
    monto numeric(10, 2),
    CONSTRAINT pk_factura_detalle_producto PRIMARY KEY (id_factura_detalle_producto)
    );

CREATE TABLE IF NOT EXISTS public.factura_detalle_sala
(
    id_factura_detalle_sala bigserial NOT NULL,
    id_factura bigint,
    id_reserva_detalle bigint,
    monto numeric(10, 2),
    CONSTRAINT pk_factura_detalle_sala PRIMARY KEY (id_factura_detalle_sala)
    );

CREATE TABLE IF NOT EXISTS public.pago
(
    id_pago bigserial NOT NULL,
    id_factura bigint,
    id_tipo_pago integer,
    fecha timestamp with time zone,
    CONSTRAINT pk_pago PRIMARY KEY (id_pago)
    );

CREATE TABLE IF NOT EXISTS public.pago_detalle
(
    id_pago_detalle bigserial NOT NULL,
    id_pago bigint,
    monto numeric(10, 2),
    concepto text COLLATE pg_catalog."default",
    CONSTRAINT pk_pago_detalle PRIMARY KEY (id_pago_detalle)
    );

CREATE TABLE IF NOT EXISTS public.pelicula
(
    id_pelicula bigserial NOT NULL,
    nombre character varying(255) COLLATE pg_catalog."default",
    sinopsis text COLLATE pg_catalog."default",
    CONSTRAINT pk_pelicula PRIMARY KEY (id_pelicula)
    );

CREATE TABLE IF NOT EXISTS public.pelicula_caracteristica
(
    id_pelicula_caracteristica bigserial NOT NULL,
    id_tipo_pelicula integer,
    id_pelicula bigint,
    valor text COLLATE pg_catalog."default",
    CONSTRAINT pk_pelicula_caracteristica PRIMARY KEY (id_pelicula_caracteristica)
    );

CREATE TABLE IF NOT EXISTS public.producto
(
    id_producto bigserial NOT NULL,
    id_tipo_producto integer,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    descripcion text COLLATE pg_catalog."default",
    CONSTRAINT pk_producto PRIMARY KEY (id_producto)
    );

CREATE TABLE IF NOT EXISTS public.programacion
(
    id_programacion bigserial NOT NULL,
    id_sala integer,
    id_pelicula bigint,
    desde timestamp with time zone,
    hasta timestamp with time zone,
    comentarios text COLLATE pg_catalog."default",
    CONSTRAINT pk_programacion PRIMARY KEY (id_programacion)
    );

CREATE TABLE IF NOT EXISTS public.reserva
(
    id_reserva bigserial NOT NULL,
    id_programacion bigint,
    id_tipo_reserva integer,
    fecha_reserva timestamp with time zone,
    estado character varying(155) COLLATE pg_catalog."default" DEFAULT 'ACTIVO'::character varying,
    observaciones text COLLATE pg_catalog."default",
    CONSTRAINT pk_reserva PRIMARY KEY (id_reserva)
    );

CREATE TABLE IF NOT EXISTS public.reserva_detalle
(
    id_reserva_detalle bigserial NOT NULL,
    id_reserva bigint,
    id_asiento bigint,
    estado character varying(155) COLLATE pg_catalog."default" DEFAULT 'ACTIVO'::character varying,
    CONSTRAINT pk_reserva_detalle PRIMARY KEY (id_reserva_detalle)
    );

CREATE TABLE IF NOT EXISTS public.sala
(
    id_sala bigserial NOT NULL,
    id_sucursal integer,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    observaciones text COLLATE pg_catalog."default",
    CONSTRAINT pk_sala PRIMARY KEY (id_sala)
    );

CREATE TABLE IF NOT EXISTS public.sala_caracteristica
(
    id_sala_caracteristica bigserial NOT NULL,
    id_tipo_sala integer,
    id_sala integer,
    valor text COLLATE pg_catalog."default",
    CONSTRAINT pk_sala_caracteristica PRIMARY KEY (id_sala_caracteristica)
    );

CREATE TABLE IF NOT EXISTS public.sucursal
(
    id_sucursal bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    longitud double precision,
    latitud double precision,
    comentarios text COLLATE pg_catalog."default",
    activo boolean,
    CONSTRAINT pk_sucursal PRIMARY KEY (id_sucursal)
    );

CREATE TABLE IF NOT EXISTS public.tipo_asiento
(
    id_tipo_asiento bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    comentarios text COLLATE pg_catalog."default",
    expresion_regular text COLLATE pg_catalog."default",
    CONSTRAINT pk_tipo_asiento PRIMARY KEY (id_tipo_asiento)
    );

CREATE TABLE IF NOT EXISTS public.tipo_pago
(
    id_tipo_pago bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    CONSTRAINT pk_tipo_pago PRIMARY KEY (id_tipo_pago)
    );

CREATE TABLE IF NOT EXISTS public.tipo_pelicula
(
    id_tipo_pelicula bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    comentarios text COLLATE pg_catalog."default",
    expresion_regular text COLLATE pg_catalog."default",
    CONSTRAINT pk_tipo_pelicula PRIMARY KEY (id_tipo_pelicula)
    );

CREATE TABLE IF NOT EXISTS public.tipo_producto
(
    id_tipo_producto bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    comentarios text COLLATE pg_catalog."default",
    CONSTRAINT pk_tipo_producto PRIMARY KEY (id_tipo_producto)
    );

CREATE TABLE IF NOT EXISTS public.tipo_reserva
(
    id_tipo_reserva bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    comentarios text COLLATE pg_catalog."default",
    CONSTRAINT pk_tipo_reserva PRIMARY KEY (id_tipo_reserva)
    );

CREATE TABLE IF NOT EXISTS public.tipo_sala
(
    id_tipo_sala bigserial NOT NULL,
    nombre character varying(155) COLLATE pg_catalog."default",
    activo boolean,
    comentarios text COLLATE pg_catalog."default",
    expresion_regular text COLLATE pg_catalog."default",
    CONSTRAINT pk_tipo_sala PRIMARY KEY (id_tipo_sala)
    );

-- Foreign Key Constraints
ALTER TABLE IF EXISTS public.asiento
    ADD CONSTRAINT fk_asiento_sala FOREIGN KEY (id_sala)
    REFERENCES public.sala (id_sala) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.asiento_caracteristica
    ADD CONSTRAINT fk_asiento_caracteristica_asiento FOREIGN KEY (id_asiento)
    REFERENCES public.asiento (id_asiento) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.asiento_caracteristica
    ADD CONSTRAINT fk_asiento_caracteristica_tipo_asiento FOREIGN KEY (id_tipo_asiento)
    REFERENCES public.tipo_asiento (id_tipo_asiento) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.factura_detalle_producto
    ADD CONSTRAINT fk_factura_detalle_producto_factura FOREIGN KEY (id_factura)
    REFERENCES public.factura (id_factura) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.factura_detalle_producto
    ADD CONSTRAINT fk_factura_detalle_producto_producto FOREIGN KEY (id_producto)
    REFERENCES public.producto (id_producto) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.factura_detalle_sala
    ADD CONSTRAINT fk_factura_detalle_sala_factura FOREIGN KEY (id_factura)
    REFERENCES public.factura (id_factura) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.factura_detalle_sala
    ADD CONSTRAINT fk_factura_detalle_sala_reserva_detalle FOREIGN KEY (id_reserva_detalle)
    REFERENCES public.reserva_detalle (id_reserva_detalle) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.pago
    ADD CONSTRAINT fk_pago_factura FOREIGN KEY (id_factura)
    REFERENCES public.factura (id_factura) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.pago
    ADD CONSTRAINT fk_pago_tipo_pago FOREIGN KEY (id_tipo_pago)
    REFERENCES public.tipo_pago (id_tipo_pago) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.pago_detalle
    ADD CONSTRAINT fk_pago_detalle_pago FOREIGN KEY (id_pago)
    REFERENCES public.pago (id_pago) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.pelicula_caracteristica
    ADD CONSTRAINT fk_pelicula_caracteristica_pelicula FOREIGN KEY (id_pelicula)
    REFERENCES public.pelicula (id_pelicula) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.pelicula_caracteristica
    ADD CONSTRAINT fk_pelicula_caracteristica_tipo_pelicula FOREIGN KEY (id_tipo_pelicula)
    REFERENCES public.tipo_pelicula (id_tipo_pelicula) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.producto
    ADD CONSTRAINT fk_producto_tipo_producto FOREIGN KEY (id_tipo_producto)
    REFERENCES public.tipo_producto (id_tipo_producto) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.programacion
    ADD CONSTRAINT fk_programacion_pelicula FOREIGN KEY (id_pelicula)
    REFERENCES public.pelicula (id_pelicula) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.programacion
    ADD CONSTRAINT fk_programacion_sala FOREIGN KEY (id_sala)
    REFERENCES public.sala (id_sala) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.reserva
    ADD CONSTRAINT fk_reserva_programacion FOREIGN KEY (id_programacion)
    REFERENCES public.programacion (id_programacion) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.reserva
    ADD CONSTRAINT fk_reserva_tipo_reserva FOREIGN KEY (id_tipo_reserva)
    REFERENCES public.tipo_reserva (id_tipo_reserva) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.reserva_detalle
    ADD CONSTRAINT fk_reserva_detalle_asiento FOREIGN KEY (id_asiento)
    REFERENCES public.asiento (id_asiento) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.reserva_detalle
    ADD CONSTRAINT fk_reserva_detalle_reserva FOREIGN KEY (id_reserva)
    REFERENCES public.reserva (id_reserva) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.sala
    ADD CONSTRAINT fk_sala_sucursal FOREIGN KEY (id_sucursal)
    REFERENCES public.sucursal (id_sucursal) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.sala_caracteristica
    ADD CONSTRAINT fk_sala_caracteristica_sala FOREIGN KEY (id_sala)
    REFERENCES public.sala (id_sala) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;

ALTER TABLE IF EXISTS public.sala_caracteristica
    ADD CONSTRAINT fk_sala_caracteristica_tipo_sala FOREIGN KEY (id_tipo_sala)
    REFERENCES public.tipo_sala (id_tipo_sala) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE RESTRICT;
