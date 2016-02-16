import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by KuuuuuCya on 02/14/16.
 */
public class Main extends Thread {
    public static void main(String[] args) throws IOException, URISyntaxException, AWTException, InterruptedException, NoSuchAlgorithmException {
        Serial serial = new Serial();
        byte sliderdata;

        serial.initialize();
        System.out.println("Started");
        //Создадим раскрывающееся меню
        PopupMenu popup = new PopupMenu();
        //Создадим элемент меню
        MenuItem exitItem = new MenuItem("Выход");
        //Добавим для него обработчик
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Добавим пункт в меню
        popup.add(exitItem);
        SystemTray systemTray = SystemTray.getSystemTray();
        //получим картинку
        Image image = Toolkit.getDefaultToolkit().getImage("vk_icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "ВК", popup);
        trayIcon.setImageAutoSize(true);
        //добавим иконку в трей
        systemTray.add(trayIcon);
        trayIcon.displayMessage("ВК", "Соединяемся с сервером", TrayIcon.MessageType.INFO);
        //Создадим экземпляр класса ВКапи
        VKapi vkAPI = new VKapi();
        //Получим токен
        //vkAPI.setConnection();
        trayIcon.displayMessage("ВК", "Соединение установлено", TrayIcon.MessageType.INFO);
        //Бескоечный цикл
        String oldMessage = vkAPI.getNewMessage();
        String newMessage;

        int i = 0;
        for (; ; ) {
            // Запросы на сервер можно подавать раз в 3 секунды
            Thread.sleep(3000); // ждем три секунды
            //Здесь отработка
            newMessage = vkAPI.getNewMessage();
            if (!newMessage.equals(oldMessage)) {
                oldMessage = newMessage;
                trayIcon.displayMessage("ВК", "Получено новое сообщение", TrayIcon.MessageType.INFO);
                sliderdata = 50;
                System.out.println("SliderData (byte)=" + sliderdata);
                serial.sendSingleByte(sliderdata);
                System.out.println(newMessage);
                sleep(2000);
                sliderdata = 51;
                System.out.println("SliderData (byte)=" + sliderdata);
                serial.sendSingleByte(sliderdata);
            }
        }
    }
}
