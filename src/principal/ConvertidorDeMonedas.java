package principal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.ResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConvertidorDeMonedas {
    public static void main(String[] args) throws IOException, InterruptedException {
        //
        String direccion = "https://v6.exchangerate-api.com/v6/e2eab898cb358f6c196cdd31/latest/USD";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        //System.out.println(json);

        Gson gson = new GsonBuilder().create(); // en lugar de usar el Gson gson = new Gson();

        ResponseDTO responseDTO = gson.fromJson(json, ResponseDTO.class);

        Scanner teclado = new Scanner(System.in);

        System.out.println("""
                *********************************
                Sea bienbenido/a al Conversor
                1) Dólar --> Peso mexicano
                2) Peso mexican --> Dólar
                3) Dólar --> Peso argentino
                4) Peso argentino --> Dólar
                5) Dólar --> Peso colombiano
                6) Peso colombiano --> Dólar
                7) Salir
                *********************************
                """);
        double usd = responseDTO.conversion_rates().USD();
        double usdToMxn = responseDTO.conversion_rates().MXN();
        double usdToCop = responseDTO.conversion_rates().COP();
        double usdToArs = responseDTO.conversion_rates().ARS();
        double usdToBrl = responseDTO.conversion_rates().BRL();

        System.out.print("Escriba su opcion: ");
        var option = Integer.valueOf(teclado.nextLine());

        switch (option) {
            case 1:
                //Conversión de dólar a peso mexicano
                System.out.print("Escriba la cantidad en dolares: ");
                double cantidadEnUsd1 = Double.valueOf(teclado.nextLine());
                double cantidadEnMxn1 = cantidadEnUsd1 * (usdToMxn/usd);
                System.out.println("$" + cantidadEnUsd1 + " USD equivale a $" + cantidadEnMxn1 + " MXN");
                break;
            case 2:
                //Conversión de peso mexicano a dólar
                System.out.print("Escriba la cantidad en pesos mexicanos: ");
                double cantidadEnMxn2 = Double.valueOf(teclado.nextLine());
                double cantidadEnUsd2 = cantidadEnMxn2 * (usd/usdToMxn);
                System.out.println("$" + cantidadEnMxn2 + " MXN equivale a $" + cantidadEnUsd2 + " USD");
                break;
            case 3:
                //Conversión de dólar a peso argentino
                System.out.print("Escriba la cantidad en dolares: ");
                double cantidadEnUsd3 = Double.valueOf(teclado.nextLine());
                double cantidadEnArs3 = cantidadEnUsd3 * (usdToArs/usd);
                System.out.println("$" + cantidadEnUsd3 + " USD equivale a $" + cantidadEnArs3 + " ARS");
                break;
            case 4:
                //Conversión de peso argentino a dólar
                System.out.print("Escriba la cantidad en pesos argentinos: ");
                double cantidadEnArs4 = Double.valueOf(teclado.nextLine());
                double cantidadEnUsd4 = cantidadEnArs4 * (usd/usdToArs);
                System.out.println("$" + cantidadEnArs4 + " ARS equivale a $" + cantidadEnUsd4 + " USD");
                break;
            case 5:
                //Conversión de dólar a peso colombiano
                System.out.print("Escriba la cantidad en dolares: ");
                double cantidadEnUsd5 = Double.valueOf(teclado.nextLine());
                double amountInCop5 = cantidadEnUsd5 * (usdToCop/usd);
                System.out.println("$" + cantidadEnUsd5 + " USD equivale a $" + amountInCop5 + " COP");
                break;
            case 6:
                //Conversión de  peso colombiano a dólar
                System.out.print("Escriba la cantidad en pesos colombianos: ");
                double amountInCop6 = Double.valueOf(teclado.nextLine());
                double amountInUsd6 = amountInCop6 * (usd/usdToCop);
                System.out.println("$" + amountInCop6 + " COP equivale a $" + amountInUsd6 + " USD");
                break;
            case 7:
                System.out.println("Ha salido");
                break;
            default:
                System.out.println("Incorrecto");
        }
    }
}
