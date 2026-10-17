IF OBJECT_ID('detalle_venta', 'U') IS NOT NULL DROP TABLE detalle_venta;
IF OBJECT_ID('ventas', 'U') IS NOT NULL DROP TABLE ventas;
IF OBJECT_ID('apartados', 'U') IS NOT NULL DROP TABLE apartados;
IF OBJECT_ID('mediciones', 'U') IS NOT NULL DROP TABLE mediciones;
IF OBJECT_ID('accesorios', 'U') IS NOT NULL DROP TABLE accesorios;
IF OBJECT_ID('lunas', 'U') IS NOT NULL DROP TABLE lunas;
IF OBJECT_ID('aros', 'U') IS NOT NULL DROP TABLE aros;
IF OBJECT_ID('usuarios', 'U') IS NOT NULL DROP TABLE usuarios;

CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    dni NVARCHAR(20) NOT NULL UNIQUE,
    telefono NVARCHAR(20),
    correo NVARCHAR(120) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    rol NVARCHAR(20) NOT NULL CHECK (rol IN ('CLIENTE','VENDEDOR'))
);

CREATE TABLE mediciones (
    id_medicion INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    ojo NVARCHAR(2) NOT NULL CHECK (ojo IN ('OD','OI')),
    esfera DECIMAL(5,2),
    cilindro DECIMAL(5,2),
    eje INT,
    adicion DECIMAL(5,2),
    fecha DATE NOT NULL,
    CONSTRAINT fk_medicion_cliente FOREIGN KEY (id_cliente) REFERENCES usuarios(id_usuario)
);

CREATE TABLE aros (
    id_aro INT IDENTITY(1,1) PRIMARY KEY,
    marca NVARCHAR(80) NOT NULL,
    material NVARCHAR(50),
    forma NVARCHAR(50),
    color NVARCHAR(50),
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0)
);

CREATE TABLE lunas (
    id_luna INT IDENTITY(1,1) PRIMARY KEY,
    tipo NVARCHAR(50) NOT NULL,
    material NVARCHAR(50),
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0)
);

CREATE TABLE accesorios (
    id_accesorio INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(80) NOT NULL,
    descripcion NVARCHAR(255),
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0)
);

CREATE TABLE ventas (
    id_venta INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_vendedor INT NOT NULL,
    id_medicion INT NULL,
    fecha DATETIME2 NOT NULL DEFAULT GETDATE(),
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_venta_vendedor FOREIGN KEY (id_vendedor) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_venta_medicion FOREIGN KEY (id_medicion) REFERENCES mediciones(id_medicion)
);

CREATE TABLE detalle_venta (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_venta INT NOT NULL,
    id_aro INT NULL,
    id_luna INT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES ventas(id_venta),
    CONSTRAINT fk_detalle_aro FOREIGN KEY (id_aro) REFERENCES aros(id_aro),
    CONSTRAINT fk_detalle_luna FOREIGN KEY (id_luna) REFERENCES lunas(id_luna)
);

CREATE TABLE apartados (
    id_apartado INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_aro INT NOT NULL,
    fecha DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_apartado_cliente FOREIGN KEY (id_cliente) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_apartado_aro FOREIGN KEY (id_aro) REFERENCES aros(id_aro)
);

PRINT 'Tablas creadas correctamente.';
