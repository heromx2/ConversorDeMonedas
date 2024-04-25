import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/e2eab898cb358f6c196cdd31/latest/USD";

    public static void main(String[] args) {
        try {
            String jsonResponse = getJsonResponse(API_URL);
            Gson gson = new Gson();
            TiposDeCambio exchangeRates = gson.fromJson(jsonResponse, TiposDeCambio.class);

            if (exchangeRates != null && exchangeRates.getResult().equals("success")) {
                TiposDeConversion rates = exchangeRates.getTiposDeConversion();

                // Conversiones
                double usdToMxnTasa = rates.getMXN();
                double mxnToUsdRate = 1.0 / usdToMxnTasa;
                double usdToArsRate = rates.getARS();
                double arsToUsdRate = 1.0 / usdToArsRate;
                double usdToCopRate = rates.getCOP();
                double copToUsdRate = 1.0 / usdToCopRate;

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
                System.out.print("Escriba su opcion: ");
                var option = Integer.valueOf(teclado.nextLine());

                switch (option) {
                    case 1:
                        //Conversión de dólar a peso mexicano
                        System.out.print("Escriba la cantidad en dolares: ");
                        double cantidadEnUsd = Integer.valueOf(teclado.nextLine());
                        double cantidadEnMxn = cantidadEnUsd * usdToMxnTasa;
                        System.out.println("$" + cantidadEnUsd + " USD equivale a $" + cantidadEnMxn + " MXN");
                        break;
                    case 2:
                        //Conversión de peso mexicano a dólar
                        System.out.print("Escriba la cantidad en pesos mexicanos: ");
                        double cantidadEnMxn2 = Integer.valueOf(teclado.nextLine());
                        double cantidadEnUsd2 = cantidadEnMxn2 * mxnToUsdRate;
                        System.out.println("$" + cantidadEnMxn2 + " MXN equivale a $" + cantidadEnUsd2 + " USD");
                        break;
                    case 3:
                        //Conversión de dólar a peso argentino
                        System.out.print("Escriba la cantidad en dolares: ");
                        double cantidadEnUsd3 = Integer.valueOf(teclado.nextLine());
                        double amountInArs = cantidadEnUsd3 * usdToArsRate;
                        System.out.println("$" + cantidadEnUsd3 + " USD equivale a $" + amountInArs + " ARS");
                        break;
                    case 4:
                        //Conversión de peso argentino a dólar
                        System.out.print("Escriba la cantidad en pesos argentinos: ");
                        double amountInArs2 = Integer.valueOf(teclado.nextLine());
                        double amountInUsd3 = amountInArs2 * arsToUsdRate;
                        System.out.println("$" + amountInArs2 + " ARS equivale a $" + amountInUsd3 + " USD");
                        break;
                    case 5:
                        //Conversión de dólar a peso colombiano
                        System.out.print("Escriba la cantidad en dolares: ");
                        double amountInUsd = Integer.valueOf(teclado.nextLine());
                        double amountInCop = amountInUsd * usdToCopRate;
                        System.out.println("$" + amountInUsd + " USD equivale a $" + amountInCop + " COP");
                        break;
                    case 6:
                        //Conversión de  peso colombiano a dólar
                        System.out.print("Escriba la cantidad en pesos colombianos: ");
                        double amountInCop2 = Integer.valueOf(teclado.nextLine());
                        double amountInUsd4 = amountInCop2 * copToUsdRate;
                        System.out.println("$" + amountInCop2 + " COP equivale a $" + amountInUsd4 + " USD");
                        break;
                    case 7:
                        //tipo = "EXIT";
                        System.out.println("Ha salido del sistema");
                        break;
                }
            } else {
                System.out.println("Error al obtener los datos de la API");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getJsonResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }

    public static class TiposDeCambio {
        private String result;
        private TiposDeConversion conversion_rates;

        public String getResult() {
            return result;
        }

        public TiposDeConversion getTiposDeConversion() {
            return conversion_rates;
        }
    }

    public static class TiposDeConversion {
        private double MXN;
        private double ARS;
        private double COP;

        public double getMXN() {
            return MXN;
        }

        public double getARS() {
            return ARS;
        }

        public double getCOP() {
            return COP;
        }
    }
}
