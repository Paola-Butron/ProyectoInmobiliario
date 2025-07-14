CREATE TABLE Banco (
    idBanco NUMBER(10) NOT NULL,
    nombre VARCHAR2(255),
    direccion VARCHAR2(255),
    distrito VARCHAR2(100),
    ruc VARCHAR2(20),
    CONSTRAINT PK_Banco PRIMARY KEY (idBanco)
);

CREATE TABLE Constructora (
    idConstructora NUMBER(10) NOT NULL,
    nombreConstructora VARCHAR2(255),
    ruc VARCHAR2(20),
    gerenteGeneral VARCHAR2(255),
    direccionFiscal VARCHAR2(255),
    telefono VARCHAR2(20),
    email VARCHAR2(255),
    CONSTRAINT PK_Constructora PRIMARY KEY (idConstructora)
);

CREATE TABLE Notaria (
    idNotaria NUMBER(10) NOT NULL,
    nombre VARCHAR2(255),
    ubicacion VARCHAR2(255),
    numeroRegistro VARCHAR2(50),
    notarioPrincipal VARCHAR2(255),
    CONSTRAINT PK_Notaria PRIMARY KEY (idNotaria)
);

CREATE TABLE Municipalidad (
    idMunicipalidad NUMBER(10) NOT NULL,
    nombre VARCHAR2(255),
    distrito VARCHAR2(100),
    direccion VARCHAR2(255),
    areaEncargada VARCHAR2(255),
    serviciosRelacionados VARCHAR2(1000),
    CONSTRAINT PK_Municipalidad PRIMARY KEY (idMunicipalidad)
);

CREATE TABLE CampañaPublicitaria (
    idCampaña NUMBER(10) NOT NULL,
    nombreCanal VARCHAR2(255),
    tipoCanal VARCHAR2(100),
    descripcion VARCHAR2(4000),
    estado VARCHAR2(50),
    CONSTRAINT PK_CampañaPublicitaria PRIMARY KEY (idCampaña)
);

CREATE TABLE Vendedor (
    idVendedor NUMBER(10) NOT NULL,
    nombreVendedor VARCHAR2(255),
    dni VARCHAR2(8),
    email VARCHAR2(255),
    telefono VARCHAR2(20),
    CONSTRAINT PK_Vendedor PRIMARY KEY (idVendedor)
);

CREATE TABLE AsesorCrediticio (
    idAsesorCrediticio NUMBER(10) NOT NULL,
    nombreCompleto VARCHAR2(255),
    dni VARCHAR2(8),
    telefono VARCHAR2(20),
    correo VARCHAR2(255),
    idBanco NUMBER(10),
    CONSTRAINT PK_AsesorCrediticio PRIMARY KEY (idAsesorCrediticio)
);

CREATE TABLE Tasador (
    idTasador NUMBER(10) NOT NULL,
    nombreCompleto VARCHAR2(255),
    registro VARCHAR2(50),
    telefono VARCHAR2(20),
    correo VARCHAR2(255),
    idBanco NUMBER(10),
    CONSTRAINT PK_Tasador PRIMARY KEY (idTasador)
);

CREATE TABLE Abogado (
    idAbogado NUMBER(10) NOT NULL,
    nombreCompleto VARCHAR2(255),
    colegiatura VARCHAR2(50),
    telefono VARCHAR2(20),
    correo VARCHAR2(255),
    especialidad VARCHAR2(100),
    idNotaria NUMBER(10),
    CONSTRAINT PK_Abogado PRIMARY KEY (idAbogado)
);

CREATE TABLE AsesorMunicipal (
    idAsesorMunicipal NUMBER(10) NOT NULL,
    nombreCompleto VARCHAR2(255),
    dni VARCHAR2(8),
    telefono VARCHAR2(20),
    correo VARCHAR2(255),
    idMunicipalidad NUMBER(10),
    CONSTRAINT PK_AsesorMunicipal PRIMARY KEY (idAsesorMunicipal)
);

CREATE TABLE Proyecto (
    idProyecto NUMBER(10) NOT NULL,
    nombre VARCHAR2(255),
    ubicacion VARCHAR2(255),
    fechaInicio DATE,
    fechaEntrega DATE,
    estado VARCHAR2(50),
    idConstructora NUMBER(10),
    CONSTRAINT PK_Proyecto PRIMARY KEY (idProyecto)
);

CREATE TABLE CanalEntrada (
    idCanalEntrada NUMBER(10) NOT NULL,
    nombreCampaña VARCHAR2(255),
    fechaInicio DATE,
    fechaFin DATE,
    presupuesto NUMBER(12, 2),
    estado VARCHAR2(50),
    descripcion VARCHAR2(4000),
    tipo VARCHAR2(100),
    idCampaña NUMBER(10),
    CONSTRAINT PK_CanalEntrada PRIMARY KEY (idCanalEntrada)
);

CREATE TABLE Lead (
    idLead NUMBER(10) NOT NULL,
    nombreCompleto VARCHAR2(255),
    email VARCHAR2(255),
    telefono VARCHAR2(20),
    estadoLead VARCHAR2(50),
    idCanalEntrada NUMBER(10),
    CONSTRAINT PK_Lead PRIMARY KEY (idLead)
);

CREATE TABLE Clientes (
    idCliente NUMBER(10) NOT NULL,
    inmuebleInteresado VARCHAR2(255),
    interesProyecto VARCHAR2(255),
    estadoDeAprobacion VARCHAR2(50),
    montoAprobado NUMBER(12, 2),
    plazoMeses NUMBER(3),
    idLead NUMBER(10) UNIQUE, -- Un Lead se convierte en un solo Cliente
    CONSTRAINT PK_Clientes PRIMARY KEY (idCliente)
);

CREATE TABLE Inmueble (
    idInmueble NUMBER(10) NOT NULL,
    area NUMBER(8, 2),
    precio NUMBER(12, 2),
    estado VARCHAR2(50),
    tipo VARCHAR2(100), -- E.g., 'Departamento', 'Duplex'
    idProyecto NUMBER(10),
    CONSTRAINT PK_Inmueble PRIMARY KEY (idInmueble)
);

CREATE TABLE Documento (
    idDocumento NUMBER(10) NOT NULL,
    nombreDocumento VARCHAR2(255),
    estado VARCHAR2(50),
    fechaFirma DATE,
    idAbogado NUMBER(10),
    idAsesorMunicipal NUMBER(10),
    idTasador NUMBER(10),
    idCliente NUMBER(10),
    CONSTRAINT PK_Documento PRIMARY KEY (idDocumento)
);

CREATE TABLE Departamento (
    idInmueble NUMBER(10) NOT NULL,
    numeroPiso NUMBER(3),
    dormitorios NUMBER(2),
    baños NUMBER(2),
    balcon VARCHAR2(2), -- 'SI'/'NO'
    CONSTRAINT PK_Departamento PRIMARY KEY (idInmueble)
);

CREATE TABLE Duplex (
    idInmueble NUMBER(10) NOT NULL,
    niveles NUMBER(2),
    dormitorios NUMBER(2),
    baños NUMBER(2),
    terraza VARCHAR2(2), -- 'SI'/'NO'
    accesoIndependiente VARCHAR2(2), -- 'SI'/'NO'
    CONSTRAINT PK_Duplex PRIMARY KEY (idInmueble)
);

CREATE TABLE Estacionamiento (
    idInmueble NUMBER(10) NOT NULL,
    numero VARCHAR2(10),
    subterraneo VARCHAR2(2), -- 'SI'/'NO'
    tipoCobertura VARCHAR2(50),
    areaTechada NUMBER(5, 2),
    CONSTRAINT PK_Estacionamiento PRIMARY KEY (idInmueble)
);

CREATE TABLE Minuta (
    idDocumento NUMBER(10) NOT NULL,
    notariaAsociada VARCHAR2(255),
    montoTotal NUMBER(12, 2),
    formaDePago VARCHAR2(100),
    constanciaDeAcabados VARCHAR2(2), -- 'SI'/'NO'
    CONSTRAINT PK_Minuta PRIMARY KEY (idDocumento)
);

CREATE TABLE Separacion (
    idDocumento NUMBER(10) NOT NULL,
    montoSeparacion NUMBER(12, 2),
    fechaPago DATE,
    formaDePago VARCHAR2(100),
    constanciaDeAcabados VARCHAR2(2), -- 'SI'/'NO'
    CONSTRAINT PK_Separacion PRIMARY KEY (idDocumento)
);


-- Tablas de Transacciones y de Enlace (Muchos a Muchos)
CREATE TABLE Venta (
    idVenta NUMBER(10) NOT NULL,
    fechaDeVenta DATE,
    montoTotal NUMBER(12, 2),
    estadoVenta VARCHAR2(50),
    idCliente NUMBER(10),
    CONSTRAINT PK_Venta PRIMARY KEY (idVenta)
);

CREATE TABLE Financiamiento (
    idFinanciamiento NUMBER(10) NOT NULL,
    entidadFinanciera VARCHAR2(255),
    cuotaMensual NUMBER(10, 2),
    fechaAprobacion DATE,
    idAsesorCrediticio NUMBER(10),
    idCliente NUMBER(10),
    CONSTRAINT PK_Financiamiento PRIMARY KEY (idFinanciamiento)
);

CREATE TABLE Contacto (
    idLead NUMBER(10) NOT NULL,
    idVendedor NUMBER(10) NOT NULL,
    tipoContacto VARCHAR2(100),
    fechaContacto DATE,
    CONSTRAINT PK_Contacto PRIMARY KEY (idLead, idVendedor)
);

CREATE TABLE Vendedor_Ofrece_Inmueble (
    idVendedor NUMBER(10) NOT NULL,
    idInmueble NUMBER(10) NOT NULL,
    CONSTRAINT PK_Vendedor_Ofrece_Inmueble PRIMARY KEY (idVendedor, idInmueble)
);

ALTER TABLE AsesorCrediticio ADD CONSTRAINT FK_Asesor_Banco FOREIGN KEY (idBanco) REFERENCES Banco(idBanco);
ALTER TABLE Tasador ADD CONSTRAINT FK_Tasador_Banco FOREIGN KEY (idBanco) REFERENCES Banco(idBanco);
ALTER TABLE Abogado ADD CONSTRAINT FK_Abogado_Notaria FOREIGN KEY (idNotaria) REFERENCES Notaria(idNotaria);
ALTER TABLE AsesorMunicipal ADD CONSTRAINT FK_AsesorMuni_Muni FOREIGN KEY (idMunicipalidad) REFERENCES Municipalidad(idMunicipalidad);

ALTER TABLE Proyecto ADD CONSTRAINT FK_Proyecto_Constructora FOREIGN KEY (idConstructora) REFERENCES Constructora(idConstructora);
ALTER TABLE CanalEntrada ADD CONSTRAINT FK_Canal_Campaña FOREIGN KEY (idCampaña) REFERENCES CampañaPublicitaria(idCampaña);
ALTER TABLE Lead ADD CONSTRAINT FK_Lead_Canal FOREIGN KEY (idCanalEntrada) REFERENCES CanalEntrada(idCanalEntrada);
ALTER TABLE Clientes ADD CONSTRAINT FK_Cliente_Lead FOREIGN KEY (idLead) REFERENCES Lead(idLead);
ALTER TABLE Inmueble ADD CONSTRAINT FK_Inmueble_Proyecto FOREIGN KEY (idProyecto) REFERENCES Proyecto(idProyecto);

ALTER TABLE Documento ADD CONSTRAINT FK_Doc_Abogado FOREIGN KEY (idAbogado) REFERENCES Abogado(idAbogado);
ALTER TABLE Documento ADD CONSTRAINT FK_Doc_AsesorMuni FOREIGN KEY (idAsesorMunicipal) REFERENCES AsesorMunicipal(idAsesorMunicipal);
ALTER TABLE Documento ADD CONSTRAINT FK_Doc_Tasador FOREIGN KEY (idTasador) REFERENCES Tasador(idTasador);
ALTER TABLE Documento ADD CONSTRAINT FK_Doc_Cliente FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente);

ALTER TABLE Departamento ADD CONSTRAINT FK_Depto_Inmueble FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);
ALTER TABLE Duplex ADD CONSTRAINT FK_Duplex_Inmueble FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);
ALTER TABLE Estacionamiento ADD CONSTRAINT FK_Estac_Inmueble FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);
ALTER TABLE Minuta ADD CONSTRAINT FK_Minuta_Doc FOREIGN KEY (idDocumento) REFERENCES Documento(idDocumento);
ALTER TABLE Separacion ADD CONSTRAINT FK_Separacion_Doc FOREIGN KEY (idDocumento) REFERENCES Documento(idDocumento);

ALTER TABLE Venta ADD CONSTRAINT FK_Venta_Cliente FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente);
ALTER TABLE Financiamiento ADD CONSTRAINT FK_Financ_Asesor FOREIGN KEY (idAsesorCrediticio) REFERENCES AsesorCrediticio(idAsesorCrediticio);
ALTER TABLE Financiamiento ADD CONSTRAINT FK_Financ_Cliente FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente);
ALTER TABLE Contacto ADD CONSTRAINT FK_Contacto_Lead FOREIGN KEY (idLead) REFERENCES Lead(idLead);
ALTER TABLE Contacto ADD CONSTRAINT FK_Contacto_Vendedor FOREIGN KEY (idVendedor) REFERENCES Vendedor(idVendedor);
ALTER TABLE Vendedor_Ofrece_Inmueble ADD CONSTRAINT FK_Ofrece_Vendedor FOREIGN KEY (idVendedor) REFERENCES Vendedor(idVendedor);
ALTER TABLE Vendedor_Ofrece_Inmueble ADD CONSTRAINT FK_Ofrece_Inmueble FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);
