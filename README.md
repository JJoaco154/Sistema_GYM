# 🏋️ Sistema GYM

Sistema de gestión para un gimnasio desarrollado en **Java** como proyecto educativo y práctico.

El proyecto fue realizado con el objetivo de aplicar conocimientos de **Programación Orientada a Objetos, manejo de bases de datos, conexión con MySQL y organización de un sistema mediante diferentes clases y responsabilidades**.

Aunque se trata de un proyecto educativo, el sistema es **funcional** y cuenta con las principales funcionalidades necesarias para administrar la información de un gimnasio.

---

## 📌 Sobre el proyecto

**Sistema GYM** es una aplicación de escritorio para gestionar diferentes aspectos de un gimnasio.

El sistema permite trabajar con usuarios, clientes, entrenadores, ejercicios y rutinas, además de contar con un sistema de inicio de sesión y conexión con una base de datos MySQL.

El proyecto fue desarrollado como parte de mi formación y como práctica para aplicar los conocimientos adquiridos durante la carrera.

La idea principal fue intentar desarrollar un sistema completo, manteniendo el código lo más claro y sencillo posible.

---

## ⚙️ Tecnologías utilizadas

* ☕ **Java**
* 🗄️ **MySQL**
* 🔌 **JDBC** para la conexión con la base de datos
* 🖥️ Aplicación de escritorio
* 🧱 Programación Orientada a Objetos (POO)
* 📦 Patrón DAO para el acceso a datos

---

## ✨ Funcionalidades

El sistema cuenta con diferentes funcionalidades relacionadas con la gestión de un gimnasio.

### 🔐 Inicio de sesión

El sistema cuenta con un login para controlar el acceso a la aplicación.

Dependiendo del tipo de usuario, se pueden manejar diferentes responsabilidades dentro del sistema.

### 👤 Gestión de usuarios

Permite trabajar con los usuarios del sistema y sus diferentes roles.

Se contemplan distintos tipos de usuarios, como:

* Administradores
* Entrenadores
* Clientes

### 🧑‍💼 Clientes

El sistema permite administrar la información relacionada con los clientes del gimnasio.

Cada cliente puede tener información relacionada con:

* Datos personales
* Clases
* Historial
* Rutina actual

### 🏋️ Entrenadores

Los entrenadores forman parte de la estructura del sistema y pueden trabajar con las rutinas y ejercicios correspondientes a los clientes.

### 💪 Ejercicios

El sistema permite manejar los ejercicios disponibles para crear las diferentes rutinas de entrenamiento.

### 📋 Rutinas

Las rutinas permiten organizar diferentes ejercicios para los clientes.

Cada rutina puede estar formada por diferentes ejercicios y asociarse con un cliente.

### 🗄️ Base de datos

El proyecto utiliza una base de datos **MySQL** llamada:

```text
Sistema_Gimnasio
```

La aplicación se conecta a la base de datos mediante Java y utiliza clases encargadas de manejar la conexión y el acceso a los datos.

Para organizar el acceso a la información se utiliza el concepto de **DAO (Data Access Object)**.

---

## 🧱 Estructura del proyecto

El proyecto está organizado utilizando clases que representan las diferentes entidades y funcionalidades del sistema.

Algunas de las clases principales son:

```text
Usuario
├── Cliente
├── Entrenador
└── Administrador

Ejercicio
Rutina
Realizable
Sistema
Login
```

También se cuenta con una parte de persistencia encargada de la conexión con la base de datos.

Por ejemplo:

```text
persistencia
└── conexion_BdD
```

Y clases DAO para realizar las operaciones relacionadas con los datos.

---

## 🧠 Conceptos aplicados

Durante el desarrollo del proyecto se aplicaron diferentes conceptos de programación:

* Programación Orientada a Objetos
* Clases y objetos
* Encapsulamiento
* Herencia
* Métodos y atributos
* Relaciones entre objetos
* Manejo de colecciones
* Separación de responsabilidades
* Conexión Java - MySQL
* Operaciones con bases de datos
* Patrón DAO
* Organización del código en diferentes clases

---

## 🗃️ Base de datos

La aplicación utiliza **MySQL** para almacenar la información de forma persistente.

La conexión con la base de datos se realiza desde Java mediante JDBC.

El proyecto separa la lógica de la aplicación de las operaciones relacionadas con la base de datos, utilizando clases específicas para la persistencia y los DAO.

---

## 🎯 Objetivo del proyecto

El objetivo principal de este proyecto fue poner en práctica los conocimientos de programación y bases de datos mediante el desarrollo de un sistema relativamente completo.

Más que intentar crear un sistema comercial, la idea fue desarrollar una aplicación que permitiera practicar y demostrar los conceptos aprendidos durante la carrera.

Por este motivo, algunas partes del proyecto pueden ser mejoradas o ampliadas en el futuro.

---

## 🚧 Estado del proyecto

🟢 **Proyecto funcional**

El sistema fue desarrollado con fines educativos, pero cuenta con funcionalidades implementadas y una estructura funcional para la gestión de un gimnasio.

No se considera un producto comercial terminado y todavía puede recibir mejoras, nuevas funcionalidades y modificaciones en su diseño.

---

## 📚 Proyecto educativo

Este proyecto fue realizado principalmente con fines **educativos y de aprendizaje**.

Su desarrollo me permitió practicar y comprender mejor cómo se estructura una aplicación de escritorio, cómo se relacionan diferentes clases entre sí y cómo conectar una aplicación Java con una base de datos MySQL.

También sirvió como práctica para aplicar conceptos de **POO, persistencia de datos y organización de proyectos** en un caso más cercano a una aplicación real.

---

## 🔮 Posibles mejoras

Algunas mejoras que podrían agregarse al proyecto en futuras versiones son:

* Mejorar la interfaz gráfica.
* Agregar más funcionalidades para administradores.
* Mejorar el sistema de permisos según el tipo de usuario.
* Agregar más información a las rutinas y ejercicios.
* Mejorar la validación de datos.
* Incorporar nuevas consultas y funcionalidades para la base de datos.
* Mejorar la organización y documentación del código.

---

## 👨‍💻 Autor

**Joaco**

Proyecto realizado como parte de mi formación en programación y desarrollo de software.

---

> 📌 **Nota:** Este proyecto es educativo y fue desarrollado para aplicar conocimientos de programación y bases de datos, pero cuenta con una implementación funcional de sus principales componentes.

