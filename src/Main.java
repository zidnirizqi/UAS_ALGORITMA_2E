
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

    public class Main {
        private static final String URL_STRING = "https://dummyjson.com/products";
        private static final String X_CONS_ID_HEADER = "X-Cons_ID";
        private static final String USER_KEY_HEADER = "user_key";
        private static final String X_CONS_ID_VALUE = "1234567";
        private static final String USER_KEY_VALUE = "faY738sH";

        public static void main(String[] args) {
            try {
                // Mengambil data JSON dari server
                String jsonData = fetchJSONData();

                // Mendapatkan array produk dari JSON
                JSONArray productsArray = new JSONArray(jsonData);

                // Mendapatkan nilai 'rating' dari setiap produk
                double[] ratings = extractRatings(productsArray);

                // Mengurutkan nilai 'rating' menggunakan Selection Sort
                selectionSort(ratings);

                // Menampilkan data yang diurutkan berdasarkan 'rating'
                for (double rating : ratings) {
                    displayProductByRating(productsArray, rating);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String fetchJSONData() throws IOException {
            URL url = new URL(URL_STRING);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(X_CONS_ID_HEADER, X_CONS_ID_VALUE);
            connection.setRequestProperty(USER_KEY_HEADER, USER_KEY_VALUE);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        }

        private static double[] extractRatings(JSONArray productsArray) {
            double[] ratings = new double[productsArray.length()];
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject product = productsArray.getJSONObject(i);
                ratings[i] = product.getDouble("rating");
            }
            return ratings;
        }

        private static void selectionSort(double[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < n; j++) {
                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }
                }
                double temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }

        private static void displayProductByRating(JSONArray productsArray, double rating) {
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject product = productsArray.getJSONObject(i);
                if (product.getDouble("rating") == rating) {
                    System.out.println("Product Name: " + product.getString("name"));
                    System.out.println("Rating: " + product.getDouble("rating"));
                    System.out.println("Price: " + product.getDouble("price"));
                    System.out.println("-------------------------");
                    break;
                }
            }
        }
    }

