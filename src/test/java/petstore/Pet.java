// 1- Pacote
package petstore;

//2- Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3- Classe
public class Pet {
    // 3.1 - Atríbutos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entídade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority=1) //Identificar o método da função como um teste o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        //  Dado - Quando - Então
        // Given - When - Then

        given() // Dado
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
                .body(jsonBody)
                .when() // Quando
                .post(uri)
                .then() // Então
                .log().all()
                .statusCode(200)
                .body("name", is("Boby"))
                .body("status", is("available"))
                .body("category.name", is("XPATHY1"))
                .body("tags.name", contains("data"))
        ;
    }
    @Test(priority=2)
public void consultarPet(){
        String petId = "1974110524";
String token =
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Boby"))
                .body("category.name", is("XPATHY1"))
                .body("status", is("available"))
                .extract()
                .path("category.name")
        ;
        System.out.println("O token é " + token);

        ;

   }
}