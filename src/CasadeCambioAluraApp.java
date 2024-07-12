import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;



public class CasadeCambioAluraApp {
    private static final String API_KEY = "94f6357cdda3f6f66070af6a";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";


    public static void main(String[] args) {

        int opcion = 0;


        System.out.println("----------------------");
        System.out.println("**********************");
        System.out.println("\nBienvenidos a tu casa de Cambio Alura\n");

        String menu = """
                1. Dolares --> Soles.
                2. Soles --> Dolares.
                3. Dolares --> Pesos Argentinos.
                4. Pesos Argentinos --> Dolares.
                5. Dolares --> Pesos Colombianos.
                6. Pesos Colombianos --> Dolares.
                7. Dolares --> Real Brasilero.
                8. Real Brasilero --> Dolares
                9. SALIR.""";

        System.out.println("----------------------");
        System.out.println("**********************");

        Scanner teclado = new Scanner(System.in);
        while (opcion != 9) {

            System.out.println(menu);
            System.out.println("\nElija una opción de cambio:");
            System.out.println("----------------------");

            opcion = teclado.nextInt();

            if (opcion == 9) {
                System.out.println("Gracias por su preferencia");
                break;
            }

            System.out.println("Ingrese el valor a convertir: ");
            double valor = teclado.nextDouble();

            try {
                switch (opcion) {
                    case 1:
                        convertirMoneda("USD", "PEN", valor);
                        break;
                    case 2:
                        convertirMoneda("PEN", "USD", valor);
                        break;
                    case 3:
                        convertirMoneda("USD", "ARS", valor);
                        break;
                    case 4:
                        convertirMoneda("ARS", "USD", valor);
                        break;
                    case 5:
                        convertirMoneda("USD", "BOB", valor);
                        break;
                    case 6:
                        convertirMoneda("BOB", "USD", valor);
                        break;
                    case 7:
                        convertirMoneda("USD", "BRL", valor);
                        break;
                    case 8:
                        convertirMoneda("BRL", "USD", valor);
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error al realizar la conversion: " + e.getMessage());
            }
        }
        teclado.close();
    }

    public static void convertirMoneda(String from, String to, double valor) throws IOException, InterruptedException {
        String url = API_URL + from;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MonedaCambio monedaUtil = new MonedaCambio();
        double tasa = monedaUtil.obtenerTasaConversion(response.body(), to);

        double resultado = valor * tasa;


        System.out.println("El valor" + " " + valor + " " + "(" + from + ")" + " " + "tiene una tasa de cambio de " + " " + resultado + " " + "(" + to + ")\n");

    }

}


