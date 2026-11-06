create database Sistema_Gimnasio;
use Sistema_Gimnasio;

-- Tabla Administrador
CREATE TABLE Administrador (
     DNI INT PRIMARY KEY,
     Nombre VARCHAR(50) NOT NULL,
     Apellido VARCHAR(50) NOT NULL,
     Contraseña VARCHAR(50) NOT NULL
);

-- Tabla Entrenador
CREATE TABLE Entrenador (
     DNI INT PRIMARY KEY,
     Nombre VARCHAR(50) NOT NULL,
     Apellido VARCHAR(50) NOT NULL,
     Contraseña VARCHAR(50) NOT NULL
);

alter table Entrenador
drop Clases;


-- Tabla Cliente (Antes de Rutina)
CREATE TABLE Cliente (
     DNI INT PRIMARY KEY,
     Nombre VARCHAR(50) NOT NULL,
     Apellido VARCHAR(50) NOT NULL,
     Contraseña VARCHAR(50) NOT NULL,
     Clases INT NOT NULL,
     Id_Entrenador INT, 
     FOREIGN KEY (Id_Entrenador) REFERENCES Entrenador(DNI)
);

ALTER TABLE Cliente MODIFY Id_Entrenador INT NULL;

-- Tabla Rutina
CREATE TABLE Rutina (
     Id_Rutina INT AUTO_INCREMENT PRIMARY KEY,
     Descripcion VARCHAR(255) NOT NULL,
     Estado BOOLEAN NOT NULL,
     Fecha DATE NOT NULL,
     Id_Cliente INT,
     FOREIGN KEY (Id_Cliente) REFERENCES Cliente(DNI)
);

ALTER TABLE rutina MODIFY Id_Cliente INT NULL;

alter table rutina
drop column Id_Cliente;

ALTER TABLE Rutina
ADD COLUMN nombre VARCHAR(50) AFTER Id_Rutina;

-- Tabla Ejercicio
CREATE TABLE Ejercicio (
     Id_Ejercicio INT AUTO_INCREMENT PRIMARY KEY,
     Nombre VARCHAR(50) NOT NULL,
     Descripcion VARCHAR(255) NOT NULL,
     Repeticiones INT NOT NULL,
     Peso INT NOT NULL,
     Estado BOOLEAN,
     Grupo_muscular VARCHAR(50)
);

-- Tabla historial_rutinas para registrar el historial de rutinas de cada cliente
CREATE TABLE historial_rutinas (
     Id_Cliente INT,
     Id_Rutina INT,
     PRIMARY KEY(Id_Cliente, Id_Rutina),
     FOREIGN KEY (Id_Cliente) REFERENCES Cliente(DNI),
     FOREIGN KEY (Id_Rutina) REFERENCES Rutina(Id_Rutina)
);

alter table historial_rutinas
ADD COLUMN fecha VARCHAR(50) AFTER Id_Rutina;

-- Tabla intermedia rutina_ejercicio para asociar ejercicios con rutinas
CREATE TABLE rutina_ejercicio (
     Id_Rutina INT NOT NULL,
     Id_Ejercicio INT NOT NULL,
     PRIMARY KEY(Id_Rutina, Id_Ejercicio),
     FOREIGN KEY (Id_Rutina) REFERENCES Rutina(Id_Rutina),
     FOREIGN KEY (Id_Ejercicio) REFERENCES Ejercicio(Id_Ejercicio)
);

CREATE TABLE cliente_rutina (
    dni_cliente INT NOT NULL,
    id_rutina INT NOT NULL,
    fecha_asignacion VARCHAR(50),

    PRIMARY KEY (dni_cliente, id_rutina),
    FOREIGN KEY (dni_cliente) REFERENCES cliente(DNI),
    FOREIGN KEY (id_rutina) REFERENCES rutina(Id_Rutina)
);

ALTER TABLE cliente MODIFY COLUMN Contraseña VARCHAR(64);
ALTER TABLE entrenador MODIFY COLUMN Contraseña VARCHAR(64);
ALTER TABLE Administrador MODIFY COLUMN Contraseña VARCHAR(64);

-- INDICE:
-- Creación de un índice compuesto
create index idx_ejercicio_grupo_estado on Ejercicio (Grupo_muscular, Estado);

-- SP: 
-- insertar profe
delimiter //
create procedure sp_insertar_entrenador(
     in p_dni int,
     in p_nombre varchar(50),
     in p_apellido varchar(50),
     in p_contraseña varchar(64)
)
begin
       declare exit handler for sqlexception
       begin
            rollback;
            signal sqlstate '45000' set message_text = 'error: el entrenador no se ha podido registrar. transaccion terminada';
       end;
       
       start transaction;
       
       if exists(select 1 from entrenador where dni = p_dni) then
          signal sqlstate '45000' set message_text = 'el dni ya pertenece a un entrenador registrado';
          
       elseif p_nombre is null or p_nombre = '' or p_apellido is null or p_apellido = '' then
          signal sqlstate '45000' set message_text = 'el nombre y el apellido son obligatorios';
          
       elseif p_contraseña is null or length(p_contraseña) < 4 then
          signal sqlstate '45000' set message_text = 'la contraseña es invalida o demasiado corta';
       end if;
       
       insert into entrenador(dni, nombre, apellido, contraseña)
       values(p_dni, p_nombre, p_apellido, p_contraseña);
       
       commit;          
end //
delimiter ;

-- insertar cliente
delimiter //
create procedure sp_insertar_cliente(
     in p_dni int,
     in p_nombre varchar(50),
     in p_apellido varchar(50),
     in p_contraseña varchar(64),
     in p_clases int,
     in p_id_entrenador int    
)
begin
       declare exit handler for sqlexception
       begin
            rollback;
            signal sqlstate '45000' set message_text = 'error: el cliente no se ha podido registrar. transaccion terminada';
       end;
       
       start transaction;
       
       -- validaciones de negocio
       if exists(select 1 from cliente where dni = p_dni) then
          signal sqlstate '45000' set message_text = 'el dni ya pertenece a un cliente registrado';
          
       elseif p_nombre is null or p_nombre = '' or p_apellido is null or p_apellido = '' then
          signal sqlstate '45000' set message_text = 'el nombre y el apellido no pueden ser nulos o estar vacios';
          
       elseif p_contraseña is null or length(p_contraseña) < 4 then
          signal sqlstate '45000' set message_text = 'la contraseña es demasiado corta o nula';
          
       elseif p_clases < 0 then
          signal sqlstate '45000' set message_text = 'la cantidad de clases no puede ser un numero negativo';
          
       elseif p_id_entrenador is not null and not exists(select 1 from entrenador where dni = p_id_entrenador) then
          signal sqlstate '45000' set message_text = 'el entrenador especificado no existe';
       end if;
 
       insert into cliente(dni, nombre, apellido, contraseña, clases, id_entrenador)
       values(p_dni, p_nombre, p_apellido, p_contraseña, p_clases, p_id_entrenador);
       
       commit;          
end //
delimiter ;

-- insertar ejercicio
delimiter //
create procedure sp_insertar_ejercicio(
     in p_nombre varchar(50),
     in p_descripcion varchar(255),
     in p_repeticiones int,
     in p_peso int,
     in p_grupo_muscular varchar(50)
)
begin
       declare exit handler for sqlexception
       begin
            rollback;
            signal sqlstate '45000' set message_text = 'error: el ejercicio no se ha podido crear. transaccion terminada';
       end;
       
       start transaction;
       
       if p_nombre is null or p_nombre = '' then
          signal sqlstate '45000' set message_text = 'el nombre del ejercicio no puede estar vacio';
          
       elseif exists(select 1 from ejercicio where nombre = p_nombre and grupo_muscular = p_grupo_muscular) then
          signal sqlstate '45000' set message_text = 'ya existe un ejercicio con ese nombre para el mismo grupo muscular';
          
       elseif p_repeticiones <= 0 or p_peso < 0 then
          signal sqlstate '45000' set message_text = 'las repeticiones deben ser mayores a 0 y el peso no puede ser negativo';
       end if;
       
       insert into ejercicio(nombre, descripcion, repeticiones, peso, estado, grupo_muscular)
       values(p_nombre, p_descripcion, p_repeticiones, p_peso, true, p_grupo_muscular);
       
       commit;          
end //
delimiter ;


-- asignar_rtina
DELIMITER //
create procedure sp_asignar_rutina(
     in p_nombre_rutina varchar(50),
     in p_descripcion varchar(200),
     in p_dni_cliente int,
     in p_fecha_asignacion date
)
begin
       declare exit handler for sqlexception
       begin
            rollback;
            signal sqlstate '45000' set message_text = 'Error: la rutina no se a podido asignar. Transaccion terminada';
       end;
       
       start transaction;
       
       if not exists(select 1 from clientes where dni = p_dni_cliente) then
          signal sqlstate '45000'
          set message_text = 'El cliente no existe';
	   elseif p_nombre_rutina is null then
          signal sqlstate '45000'
          set message_text = 'La rutina no puede ser nula';
	   elseif p_fecha_asignacion is null or p_fecha_asignacion < curdate() then
		  signal sqlstate '45000'
          set message_text = 'La fecha es incorrecta';  
	   end if;
       
       insert into Rutina(nombre, Descripcion, Estado, Fecha)
       values(p_nombre_rutina, p_descripcion, TRUE, CURDATE());
       
       set @ultimo_id = last_insert_id();
       
       insert into cliete_rutina(dni_cliente, id_rutina, fecha_asignacion)
       values(p_dni_cliente, @ultimo_id, p_fecha_asignacion);
       
       commit;          
end //
DELIMITER ;

-- UDF: calcular categoria cliente/socio
DELIMITER //
create function calcular_nivel_cliente(p_dni int)
returns varchar(20)
deterministic
begin
      declare v_calses int;
      declare v_nivel varchar(20);
      
      select calses into v_clases
      from clientes 
      where dni = p_dni;
      
      if v_clases < 10 then
         set v_nivel = 'Principiante';
	  elseif v_clase between 10 and 30 then
         set v_nivel = 'Intermedio';
	  elseif v_clase > 30 then
         set v_nivel = 'Avanzado';
	  end if;
      
      return v_nivel;
end //
DELIMITER ;

-- VISTAS
create view Vista_Ranking_Clientes_Por_Entrenador as(
    with cant_alumnos_x_profe as(
        select p.DNI as dni_entrenador, p.nombre as profe, c.nombre as cliente, sum(c.clases) as cant_clases
        from cliente as c
        join entrenador as p on c.Id_Entrenador = p.DNI
        group by p.DNI, p.nombre, c.nombre
    ),
    orden_clientes as(
       select dni_entrenador, profe, cliente, cant_clases,
              dense_rank() over(partition by dni_entrenador order by cant_clases desc) as rancking
	   from cant_alumnos_x_profe
    )
    select dni_entrenador, profe, cliente, cant_clases, rancking
    from orden_clientes
);

create view Vista_Historial_Correlativo_Rutinas as(
    with rutina_cliente as(
       select c.nombre as cliente, r.nombre as rutina, hr.fecha
       from historial_rutinas as hr
       join cliente as c on c.DNI = hr.id_cliente
       join rutina as r on r.id_rutina = hr.id_rutina
    ),
    orden_fechas_rutinas as(
       select cliente, rutina, fecha,
			  lag(fecha, 1, null) over(partition by cliente order by fecha) as rutina_anterior,
              lead(fecha, 1, null) over(partition by cliente order by fecha) as rutina_siguiente
		from rutina_cliente
    )
    select cliente, rutina, fecha, rutina_anterior, rutina_siguiente
    from orden_fechas_rutinas
);

create view Vista_Analisis_Carga_Rutinas as(
    with cte_carga_ejercicios as(
       select e.id_ejercicio, e.nombre as ejercicio, e.peso, r.id_rutina, r.nombre as rutina
       from rutina_ejercicio as re
       join ejercicio as e on e.id_ejercicio = re.id_ejercicio
       join rutina as r on r.id_rutina = re.id_rutina
    ),
    suma_pesos_rutinas as(
        select rutina, ejercicio, peso,
               sum(peso) over(partition by rutina) as suma_peso_acumulada
		from cte_carga_ejercicios
    )
    select rutina, ejercicio, peso, suma_peso_acumulada
    from suma_pesos_rutinas
);

-- TRIGGER
DELIMITER //
create trigger Trigger_Registrar_Historial_Rutina
after insert on cliente_rutina
for each row
begin
    insert into historial_rutinas(Id_Cliente, Id_Rutina, fecha)
    values(new.dni_cliente, new.id_rutina, CURDATE());
end //
DELIMITER ;


DELIMITER //
create trigger Trigger_Validar_Clases_Disponibles
before insert on cliente_rutina
for each row
begin
    declare clases_restantes int;

    select Clases into clases_restantes 
    from Cliente 
    where DNI = new.dni_cliente;

    if clases_restantes <= 0 then
        signal sqlstate '45000' 
        set message_text = 'Error: El cliente no tiene clases disponibles para asignar una nueva rutina.';
    end if;
end //
DELIMITER ;
