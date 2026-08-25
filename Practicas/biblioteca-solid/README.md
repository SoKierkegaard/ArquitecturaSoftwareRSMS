# Practica Guiada — Principios SOLID
## Sistema de Biblioteca USFX · COM-350 Arquitectura de Software · Tema 3

Proyecto Maven de partida para la practica guiada y los ejercicios 1 y 2.

---

### 1. Requisitos

| Herramienta | Version minima |
|---|---|
| JDK | 17 (recomendado 21 LTS) |
| Apache NetBeans | 21 o superior |
| Apache Maven | 3.8 (el que trae NetBeans sirve) |
| Git | cualquiera |

La primera compilacion descarga JUnit 5, Mockito, AssertJ y ArchUnit desde
Maven Central: necesitas conexion a internet una sola vez.

### 2. Abrir en NetBeans

1. `File > Open Project...` y elige la carpeta `biblioteca-solid`.
2. Clic derecho sobre el proyecto > `Build with Dependencies`.
3. Clic derecho > `Test` (o Alt+F6) para ejecutar la suite.
4. Clic derecho > `Run` (F6) para ver la demostracion de `Main`.

### 3. Que hay dentro

```
src/main/java/bo/edu/usfx/biblioteca/
    Main.java                     demostracion ejecutable
    legado/                       PRACTICA GUIADA  (pasos 1 a 6)
        GestorBiblioteca.java       clase Dios: SRP, OCP y DIP
        MaterialBiblioteca.java     jerarquia que rompe LSP
        OperacionesBiblioteca.java  interfaz gorda: ISP
    ejercicio1/
        SistemaSanciones.java       EJERCICIO 1: SRP + OCP + DIP
    ejercicio2/
        Reserva.java                EJERCICIO 2: LSP + ISP
        ServicioReservas.java

src/test/java/bo/edu/usfx/biblioteca/
    legado/PruebasCaracterizacionTest.java   red de seguridad (debe seguir verde)
    legado/ContratoLspTest.java
    legado/ContratoIspTest.java
    ejercicio1/SistemaSancionesTest.java
    ejercicio2/ReservasCaracterizacionTest.java
    arquitectura/ReglasDisenoTest.java       ArchUnit — @Disabled hasta el Paso 6
```

### 4. Reglas de la practica

1. **Un commit por paso**, con el mensaje en el formato indicado en la guia.
2. Despues de cada commit, `mvn test` debe pasar en **verde**.
3. No borres ni relajes las pruebas de caracterizacion: son el contrato.
4. Toda evidencia (capturas, `git log`, reportes) va al informe final.

### 5. Comandos utiles

```bash
mvn clean test                 # ejecuta toda la suite
mvn -Dtest=PruebasCaracterizacionTest test
mvn surefire-report:report     # genera target/site/surefire-report.html
git log --oneline --graph      # evidencia de los commits por paso
```
