package bo.edu.usfx.biblioteca.arquitectura;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * =====================================================================
 *  PASO 6 - EL DISENO COMO PRUEBA EJECUTABLE (ArchUnit)
 * =====================================================================
 *  Estas reglas FALLAN sobre el codigo legado. Por eso la clase entera
 *  esta anotada con @Disabled: sin ella la practica arrancaria en rojo.
 *
 *  INSTRUCCION: cuando termines el Paso 5, BORRA la anotacion @Disabled
 *  y ejecuta las pruebas. Captura la pantalla en ROJO, refactoriza hasta
 *  ponerlas en VERDE y captura la pantalla otra vez.
 *  Ese par de capturas es la evidencia E-6 de tu informe.
 *
 *  Estructura de paquetes esperada al final de la refactorizacion:
 *      bo.edu.usfx.biblioteca.dominio           entidades y puertos
 *      bo.edu.usfx.biblioteca.aplicacion        casos de uso
 *      bo.edu.usfx.biblioteca.infraestructura   adaptadores (MySQL, SMTP)
 * =====================================================================
 */
@Disabled("PASO 6: borra esta anotacion cuando termines de refactorizar")
@DisplayName("Reglas de diseno verificadas automaticamente")
class ReglasDisenoTest {

    private static final JavaClasses CLASES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("bo.edu.usfx.biblioteca");

    @Test
    @DisplayName("R1 - el dominio no conoce la infraestructura (DIP)")
    void elDominioNoConoceLaInfraestructura() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..dominio..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..infraestructura..", "..persistencia..", "..ui..");

        regla.allowEmptyShould(true).check(CLASES);
    }

    @Test
    @DisplayName("R2 - nadie fuera de la infraestructura toca JDBC")
    void sinJdbcFueraDeLaInfraestructura() {
        ArchRule regla = noClasses()
                .that().resideOutsideOfPackages("..infraestructura..", "..legado..")
                .should().dependOnClassesThat()
                .resideInAPackage("java.sql..");

        regla.allowEmptyShould(true).check(CLASES);
    }

    @Test
    @DisplayName("R3 - los repositorios del dominio son interfaces (DIP)")
    void losRepositoriosSonInterfaces() {
        ArchRule regla = classes()
                .that().haveSimpleNameStartingWith("Repositorio")
                .and().resideInAPackage("..dominio..")
                .should().beInterfaces();

        regla.allowEmptyShould(true).check(CLASES);
    }

    @Test
    @DisplayName("R4 - ninguna clase usa UnsupportedOperationException (LSP / ISP)")
    void sinOperacionesNoSoportadas() {
        ArchRule regla = noClasses()
                .that().resideOutsideOfPackage("..legado..")
                .should().dependOnClassesThat()
                .areAssignableTo(UnsupportedOperationException.class);

        regla.allowEmptyShould(true).check(CLASES);
    }

    @Test
    @DisplayName("R5 - no existen clases 'Gestor' ni 'Manager' (SRP)")
    void sinClasesCajonDeSastre() {
        ArchRule regla = noClasses()
                .that().resideOutsideOfPackage("..legado..")
                .should().haveSimpleNameStartingWith("Gestor")
                .orShould().haveSimpleNameEndingWith("Manager")
                .orShould().haveSimpleNameEndingWith("Utils");

        regla.allowEmptyShould(true).check(CLASES);
    }

    @Test
    @DisplayName("R6 - el grafo de paquetes no tiene ciclos (ADP)")
    void sinDependenciasCiclicas() {
        slices().matching("bo.edu.usfx.biblioteca.(*)..")
                .should().beFreeOfCycles()
                .check(CLASES);
    }
}
