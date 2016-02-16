import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class vk {
    String token = "https://oauth.vk.com/blank.html#access_token=" +
            "61c2cc4f1a2a5b87ae928f2948e23831f6f78510c3bcb9027bd9e38ca66ace7fcd5e2b5431657fdb3c4fe" +
            "&expires_in=86400&user_id=15360888";
    String url = "https://api.vk.com/method/" +
            "messages.get" +
            "?out=0" +
            "&access_token=" + token;
    public static void main(String[] args)
            throws Exception {
        String httpsURL = "https://oauth.vk.com/authorize?" +
                "client_id=5293925&" +
                "scope=messages&" +
                "display=popup&" +
                "response_type=token";
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }

        in.close();


    }

    public String getMessages(String url) {

        String line = "";
        try

        {
            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            line = reader.readLine();
            reader.close();

        } catch (IOException e) {
            // ...
        }

        return line;
    }

}