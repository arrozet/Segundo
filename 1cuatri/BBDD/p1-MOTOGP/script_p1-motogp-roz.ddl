-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2023-10-07 19:14:19 CEST
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE acto_piloto (
    acto_publico_fecha DATE NOT NULL,
    piloto_nombre      VARCHAR2(200) NOT NULL
);

ALTER TABLE acto_piloto ADD CONSTRAINT acto_piloto_pk PRIMARY KEY ( acto_publico_fecha,
                                                                    piloto_nombre );

CREATE TABLE acto_publico (
    fecha            DATE NOT NULL,
    direccion        VARCHAR2(500),
    descripcion      VARCHAR2(200),
    persona_contacto VARCHAR2(200),
    numero_contacto  INTEGER
);

ALTER TABLE acto_publico ADD CONSTRAINT acto_publico_pk PRIMARY KEY ( fecha );

CREATE TABLE carrera (
    tipo_neumatico_delantero VARCHAR2(500),
    tipo_neumatico_trasero   VARCHAR2(500),
    caida                    INTEGER,
    tiempo                   NUMBER,
    puntuacion               INTEGER,
    piloto_nombre            VARCHAR2(200) NOT NULL,
    circuito_nombre          VARCHAR2(500) NOT NULL
);

ALTER TABLE carrera ADD CONSTRAINT carrera_pk PRIMARY KEY ( piloto_nombre,
                                                            circuito_nombre );

CREATE TABLE circuito (
    nombre                    VARCHAR2(500) NOT NULL,
    fecha                     DATE NOT NULL,
    pais                      VARCHAR2(500),
    ciudad                    VARCHAR2(500),
    ano_inauguracion          INTEGER,
    anchura                   NUMBER,
    posicion_parrilla         INTEGER,
    longitud_total            NUMBER,
    distancia_recta_mas_larga NUMBER,
    numero_curvas_izq         INTEGER,
    numero_curvas_der         INTEGER,
    mapa                      BLOB,
    numero_vueltas            INTEGER,
    tipo_carrera              VARCHAR2(500)
);

ALTER TABLE circuito ADD CONSTRAINT circuito_pk PRIMARY KEY ( nombre );

ALTER TABLE circuito ADD CONSTRAINT circuito_fecha_un UNIQUE ( fecha );

CREATE TABLE empleado (
    codigo           INTEGER NOT NULL,
    pasaporte        INTEGER,
    nacionalidad     VARCHAR2(200),
    nombre           VARCHAR2(200),
    fecha_nacimiento DATE,
    equipo_nombre    VARCHAR2(500),
    equipo_nombre2   VARCHAR2(500)
);

CREATE UNIQUE INDEX empleado__idx ON
    empleado (
        equipo_nombre
    ASC );

ALTER TABLE empleado ADD CONSTRAINT empleado_pk PRIMARY KEY ( codigo );

ALTER TABLE empleado ADD CONSTRAINT empleado_pasaporte_un UNIQUE ( pasaporte );

ALTER TABLE empleado ADD CONSTRAINT empleado_nacionalidad_un UNIQUE ( nacionalidad );

CREATE TABLE equipo (
    nombre           VARCHAR2(500) NOT NULL,
    modelo_moto      VARCHAR2(500) NOT NULL,
    direccion_postal VARCHAR2(500) NOT NULL,
    direccion_web    VARCHAR2(500) NOT NULL,
    objetivos        VARCHAR2(500) NOT NULL,
    foto_oficial     BLOB NOT NULL,
    logo             BLOB NOT NULL,
    empleado_codigo  INTEGER
);

CREATE UNIQUE INDEX equipo__idx ON
    equipo (
        empleado_codigo
    ASC );

ALTER TABLE equipo ADD CONSTRAINT equipo_pk PRIMARY KEY ( nombre );

CREATE TABLE oficial (
    nombre            VARCHAR2(500) NOT NULL,
    presupuesto       NUMBER,
    ano_creacion      INTEGER,
    direccion_fabrica VARCHAR2(500)
);

ALTER TABLE oficial ADD CONSTRAINT oficial_pk PRIMARY KEY ( nombre );

CREATE TABLE piloto (
    nombre        VARCHAR2(200) NOT NULL,
    dorsal        INTEGER NOT NULL,
    pais          VARCHAR2(200) NOT NULL,
    ciudad        VARCHAR2(200) NOT NULL,
    peso          NUMBER NOT NULL,
    altura        NUMBER NOT NULL,
    video         BLOB NOT NULL,
    equipo_nombre VARCHAR2(500) NOT NULL
);

ALTER TABLE piloto ADD CONSTRAINT piloto_pk PRIMARY KEY ( nombre );

ALTER TABLE piloto ADD CONSTRAINT piloto_dorsal_un UNIQUE ( dorsal );

CREATE TABLE piloto_piloto (
    piloto_nombre  VARCHAR2(200) NOT NULL,
    piloto_nombre1 VARCHAR2(200) NOT NULL
);

ALTER TABLE piloto_piloto ADD CONSTRAINT piloto_piloto_pk PRIMARY KEY ( piloto_nombre,
                                                                        piloto_nombre1 );

CREATE TABLE tiempos (
    vuelta        INTEGER NOT NULL,
    tiempo        NUMBER,
    piloto_nombre VARCHAR2(200) NOT NULL,
    tramo_codigo  INTEGER NOT NULL
);

ALTER TABLE tiempos
    ADD CONSTRAINT tiempos_pk PRIMARY KEY ( vuelta,
                                            piloto_nombre,
                                            tramo_codigo );

CREATE TABLE tramo (
    codigo            INTEGER NOT NULL,
    diferencia_altura NUMBER NOT NULL,
    tipo_asfalto      VARCHAR2(500) NOT NULL,
    velocidad_media   NUMBER NOT NULL,
    circuito_nombre   VARCHAR2(500) NOT NULL
);

ALTER TABLE tramo ADD CONSTRAINT tramo_pk PRIMARY KEY ( codigo );

ALTER TABLE acto_piloto
    ADD CONSTRAINT acto_piloto_acto_publico_fk FOREIGN KEY ( acto_publico_fecha )
        REFERENCES acto_publico ( fecha );

ALTER TABLE acto_piloto
    ADD CONSTRAINT acto_piloto_piloto_fk FOREIGN KEY ( piloto_nombre )
        REFERENCES piloto ( nombre );

ALTER TABLE carrera
    ADD CONSTRAINT carrera_circuito_fk FOREIGN KEY ( circuito_nombre )
        REFERENCES circuito ( nombre );

ALTER TABLE carrera
    ADD CONSTRAINT carrera_piloto_fk FOREIGN KEY ( piloto_nombre )
        REFERENCES piloto ( nombre );

ALTER TABLE empleado
    ADD CONSTRAINT empleado_equipo_fk FOREIGN KEY ( equipo_nombre )
        REFERENCES equipo ( nombre );

ALTER TABLE empleado
    ADD CONSTRAINT empleado_equipo_fkv2 FOREIGN KEY ( equipo_nombre2 )
        REFERENCES equipo ( nombre );

ALTER TABLE equipo
    ADD CONSTRAINT equipo_empleado_fk FOREIGN KEY ( empleado_codigo )
        REFERENCES empleado ( codigo );

ALTER TABLE oficial
    ADD CONSTRAINT oficial_equipo_fk FOREIGN KEY ( nombre )
        REFERENCES equipo ( nombre );

ALTER TABLE piloto
    ADD CONSTRAINT piloto_equipo_fk FOREIGN KEY ( equipo_nombre )
        REFERENCES equipo ( nombre );

ALTER TABLE piloto_piloto
    ADD CONSTRAINT piloto_piloto_piloto_fk FOREIGN KEY ( piloto_nombre )
        REFERENCES piloto ( nombre );

ALTER TABLE piloto_piloto
    ADD CONSTRAINT piloto_piloto_piloto_fkv1 FOREIGN KEY ( piloto_nombre1 )
        REFERENCES piloto ( nombre );

ALTER TABLE tiempos
    ADD CONSTRAINT tiempos_piloto_fk FOREIGN KEY ( piloto_nombre )
        REFERENCES piloto ( nombre );

ALTER TABLE tiempos
    ADD CONSTRAINT tiempos_tramo_fk FOREIGN KEY ( tramo_codigo )
        REFERENCES tramo ( codigo );

ALTER TABLE tramo
    ADD CONSTRAINT tramo_circuito_fk FOREIGN KEY ( circuito_nombre )
        REFERENCES circuito ( nombre );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            11
-- CREATE INDEX                             2
-- ALTER TABLE                             29
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
