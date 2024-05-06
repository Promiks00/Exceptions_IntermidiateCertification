import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");

        try {
            String input = scanner.nextLine();
            String[] data = input.split("\\s+");

            if (data.length != 6) {
                System.err.println("Ошибка: Неверное количество данных.");
                return;
            }

            String surname = data[0];
            String name = data[1];
            String patronymic = data[2];
            String dobString = data[3];
            long phoneNumber = Long.parseLong(data[4]);
            char gender = data[5].charAt(0);

            // Проверка формата даты рождения
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dob = dateFormat.parse(dobString);

            // Проверка, что дата рождения не позднее текущей даты
            Date currentDate = new Date();
            if (dob.after(currentDate)) {
                throw new IllegalArgumentException("Ошибка: Дата рождения не может быть позднее текущей даты.");
            }

            // Проверка формата пола
            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Ошибка: Неверный формат пола.");
            }

            // Создание строки для записи в файл
            String record = surname + " " + name + " " + patronymic + " " + dobString + " " + phoneNumber + " " + gender;

            // Запись в файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true))) {
                writer.write(record);
                writer.newLine();
                System.out.println("Данные успешно записаны в файл.");
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл:");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Неверный формат номера телефона.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println("Ошибка: Неверный формат даты рождения.");
        }
    }
}
