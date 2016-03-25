import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class VKapi {
    private String client_id = "2971510";
    private String scope = "messages";
    private String redirect_uri = "http://oauth.vk.com/blank.html";
    private String display = "popup";
    private String token = "61c2cc4f1a2a5b87ae928f2948e23831f6f78510c3bcb9027bd9e38ca66ace7fcd5e2b5431657fdb3c4fe&expires_in=86400&user_id=15360888";
    private String response_type = "token";
    private String access_token;
    private String email = "";//тут должен быть прописан email
    private String pass = "";//тут должен быть прописан пароль

    public void setConnection() throws IOException, URISyntaxException {
        HttpClient httpClient = HttpClients.createDefault();
// Делаем первый запрос

        String httpsURL = "https://oauth.vk.com/authorize?" +
                "client_id=5293925&" +
                "scope=messages&" +
                "display=popup&" +
                "response_type=token";

        HttpPost post = new HttpPost(httpsURL);
        HttpResponse response;
        response = httpClient.execute(post);
        post.abort();
//Получаем редирект
        String HeaderLocation = response.getFirstHeader("location").getValue();
        URI RedirectUri = new URI(HeaderLocation);
//Для запроса авторизации необходимо два параметра полученных в первом запросе
//ip_h и to_h
        String ip_h = RedirectUri.getQuery().split("&")[2].split("=")[1];
        String to_h = RedirectUri.getQuery().split("&")[4].split("=")[1];
// Делаем запрос авторизации
        String httpsPost = ("https://login.vk.com/?act=login&soft=1" +
                "&q=1" +
                "&ip_h=" + ip_h +
                "&from_host=oauth.vk.com" +
                "&to=" + to_h +
                "&expire=0" +
                "&email=" + email +
                "&pass=" + pass);
        post = new HttpPost(httpsPost);
        System.out.println(httpsURL);
        System.out.println(httpsPost);
        response = httpClient.execute(post);
        post.abort();
// Получили редирект на подтверждение требований приложения
        HeaderLocation = response.getFirstHeader("location").getValue();
        post = new HttpPost(HeaderLocation);
// Проходим по нему
        response = httpClient.execute(post);
        post.abort();
// Теперь последний редирект на получение токена
        HeaderLocation = response.getFirstHeader("location").getValue();
// Проходим по нему
        post = new HttpPost(HeaderLocation);
        response = httpClient.execute(post);
        post.abort();
// Теперь в след редиректе необходимый токен
        HeaderLocation = response.getFirstHeader("location").getValue();
// Просто спарсим его сплитами
        access_token = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];
        System.out.println(access_token);
    }

    public String getNewMessage() throws ClientProtocolException, IOException, NoSuchAlgorithmException, URISyntaxException {
        //формируем строку запроса
        String url = "https://api.vk.com/method/" +
                "messages.get" +
                "?out=0" +
                "&access_token=" + token;
        String line = "";
        try {
            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            line = reader.readLine();
            reader.close();

        } catch (MalformedURLException e) {
            System.out.println("first exep");
        } catch (IOException e) {
            System.out.println("IO excep");
        }
        return line;
    }
}