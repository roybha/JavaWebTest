package org.example;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Memory  {
    private final UserDAO userInterface;
    public UserDAO getUserInterface() {
        return userInterface;
    }
    private final ChoiceDAO choiceInterface;
    public ChoiceDAO getChoiceInterface() {
        return choiceInterface;
    }
    private final MessageDAO messageInterface;
    public MessageDAO getMessageInterface() {
        return messageInterface;
    }
     ArrayList<Choice> users;
     Connection conn;
    public Memory(String url, String user, String password)  {
        try {
            conn=DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
           throw new RuntimeException("Error while connecting",e);
        }
        users = new ArrayList<>();
        userInterface = new userImplement();
        choiceInterface = new choiceImplement();
        messageInterface = new messageImplement();

     }
     private class userImplement implements UserDAO {
         @Override
         public List<User> getAll() {
             List<User> users = new ArrayList<>();  // Список для збереження всіх користувачів
             String query = "SELECT id, name, age, file, gender, password, login FROM users";  // Запит до бази даних

             try (PreparedStatement statement = conn.prepareStatement(query)) {
                 ResultSet rs = statement.executeQuery();  // Виконання запиту

                 while (rs.next()) {  // Перебір результатів
                     int id = rs.getInt("id");
                     String name = rs.getString("name");
                     int age = rs.getInt("age");
                     String file = rs.getString("file");
                     String gender = rs.getString("gender");
                     String password = rs.getString("password");
                     String login = rs.getString("login");

                     // Створення нового користувача та додавання його до списку
                     User user = new User(id, name, age, file, password, login,gender);
                     users.add(user);
                 }
             } catch (SQLException e) {
                 throw new RuntimeException("Error retrieving users", e);  // Обробка помилок
             }

             return users;  // Повернення списку користувачів
         }
         @Override
         public void save(User user) {
             String query = "INSERT INTO users(id, name, age, file, gender, password, login) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                     "ON CONFLICT(id) DO UPDATE SET name = EXCLUDED.name, " +
                     "age = EXCLUDED.age, file = EXCLUDED.file, gender = EXCLUDED.gender, " +
                     "password = EXCLUDED.password, login = EXCLUDED.login";

             try (PreparedStatement statement = conn.prepareStatement(query)) {
                 statement.setInt(1, getMaxID());
                 statement.setString(2, user.getName());
                 statement.setInt(3, user.getAge());
                 statement.setString(4, user.getFile());
                 statement.setString(5, user.getGender());
                 statement.setString(6, user.getPassword());
                 statement.setString(7, user.getLogin());

                 statement.executeUpdate();
             } catch (SQLException e) {
                 throw new RuntimeException("Error saving user", e);
             }
         }
         public Optional<User> getByID(int searchId) {
             try {
                 String check = """
                select * from users where id = ?
                """;
                 PreparedStatement statement = conn.prepareStatement(check);
                 statement.setInt(1, searchId);
                 ResultSet rs = statement.executeQuery();
                 while (rs.next()) {
                     int id = rs.getInt("id");
                     String name = rs.getString("name");
                     int age = rs.getInt("age");
                     String file = rs.getString("file");
                     String password = rs.getString("password");
                     String login = rs.getString("login");
                     String gender = rs.getString("gender");
                     return Optional.of(new User(id, name, age, file, password, login, gender));
                 }
                 rs.close();

             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
             return Optional.empty();
         }
         public List<User> getUsersByGender(String gender) {
             List<User> users = new ArrayList<>();
             String query = "SELECT id, name, file, age,gender FROM users WHERE gender = ?";

             try (PreparedStatement stmt = conn.prepareStatement(query)) {
                 stmt.setString(1, gender);
                 ResultSet rs = stmt.executeQuery();

                 while (rs.next()) {
                     users.add(new User(rs.getInt("id"),rs.getString("name"),rs.getInt("age"),rs.getString("file"),null,null,rs.getString("gender")));
                 }
             } catch (SQLException e) {
                 throw new RuntimeException("Error fetching users from the database.", e);
             }
             return users;
         }
         public Integer getMaxID(){
             String query = " SELECT MAX(id) FROM users";
             try (PreparedStatement statement = conn.prepareStatement(query)){
                 ResultSet result = statement.executeQuery();
                 if (result.next()) {
                     int maxID = result.getInt(1);
                     // Якщо таблиця пуста, result.getInt(1) поверне 0
                     return maxID > 0 ? maxID + 1 : 1;
                 } else {
                     // На випадок, якщо результатів взагалі немає
                     return 1;
                 }
             }catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
     }
     private class choiceImplement implements ChoiceDAO {
         @Override
         public void save(Choice choice) {
             try {
                 // Спочатку перевіряємо, чи існує запис з такими name, age, file
                 String checkSql = """
            SELECT COUNT(*)
            FROM user_responses
            WHERE  responder_id = ? AND receiver_id = ?
            """;
                 PreparedStatement checkStatement = conn.prepareStatement(checkSql);
                 checkStatement.setInt(1,choice.getResponder_user().getId());
                 checkStatement.setInt(2,choice.getReceiver_user().getId());


                 ResultSet rs = checkStatement.executeQuery();
                 rs.next();
                 int count = rs.getInt(1);
                 rs.close();

                 if (count > 0) {
                     // Якщо запис існує, оновлюємо поле answer
                     String updateSql = """
                UPDATE user_responses
                SET response_text = ?
                WHERE receiver_id = ? AND responder_id = ?
                """;
                     PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                     updateStatement.setString(1,choice.getAnswer());
                     updateStatement.setInt(2,choice.getReceiver_user().getId());
                     updateStatement.setInt(3,choice.getResponder_user().getId());
                     updateStatement.executeUpdate();
                 } else {
                     // Якщо запису немає, додаємо новий
                     String insertSql = """
                INSERT INTO user_responses (responder_id, receiver_id, response_text)
                VALUES (?, ?, ?)
                """;
                     PreparedStatement insertStatement = conn.prepareStatement(insertSql);
                     insertStatement.setInt(1, choice.getResponder_user().getId());
                     insertStatement.setInt(2, choice.getReceiver_user().getId());
                     insertStatement.setString(3, choice.getAnswer());
                     insertStatement.executeUpdate();
                 }

                 // Оновлюємо локальний список
                 users.removeIf(c ->
                         c.getResponder_user().getName().equals(choice.getResponder_user().getName()) &&
                                 c.getResponder_user().getAge() == choice.getResponder_user().getAge() &&
                                 c.getResponder_user().getFile().equals(choice.getResponder_user().getFile())
                 );
                 users.add(choice);

             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         @Override
         public List<Choice> getAll() {
             try {
                 users.clear();
                 PreparedStatement statement = conn.prepareStatement("select * from user_responses");
                 ResultSet rs = statement.executeQuery();
                 while (rs.next()) {
                     int responder_id = rs.getInt("responder_id");
                     int receiver_id = rs.getInt("receiver_id");
                     String response_text = rs.getString("response_text");

                     // Отримуємо користувача за responder_id
                     Optional<User> responderOpt = userInterface.getByID(responder_id);
                     User responder = responderOpt.orElseThrow(() -> new RuntimeException("Responder not found for id: " + responder_id));

                     // Отримуємо користувача за receiver_id
                     Optional<User> receiverOpt = userInterface.getByID(receiver_id);
                     User receiver = receiverOpt.orElseThrow(() -> new RuntimeException("Receiver not found for id: " + receiver_id));

                     // Додаємо новий об'єкт Choice
                     users.add(new Choice(response_text, responder, receiver));
                 }
                 return Collections.unmodifiableList(users);
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             } catch (RuntimeException e)
             {
                 return Collections.emptyList();
             }
         }
     }
     private class messageImplement implements MessageDAO {
         @Override
         public void save(Message message)  {
             String sql = "INSERT INTO Messages (from_user_id, to_user_id, text, sent_at) VALUES (?, ?, ?, ?)";

             try (PreparedStatement statement = conn.prepareStatement(sql)) {
                 statement.setInt(1, message.getFrom()); // from_user_id
                 statement.setInt(2, message.getTo());   // to_user_id
                 statement.setString(3, message.getText()); // text
                 statement.setObject(4, message.getTime()); // time (LocalDateTime)

                 statement.executeUpdate();
             }catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         @Override
         public List<Message> getAll(Integer fromWho, Integer toWho) {
             try {
                 String querry = """
                    SELECT *
                    FROM messages
                    WHERE (from_user_id = ? AND to_user_id = ?)
                       OR (from_user_id = ? AND to_user_id = ?)
                    ORDER BY sent_at ASC;
                    """;
                 PreparedStatement statement = conn.prepareStatement(querry);
                 statement.setInt(1, fromWho);
                 statement.setInt(2, toWho);
                 statement.setInt(3, toWho);
                 statement.setInt(4, fromWho);
                 ResultSet rs = statement.executeQuery();
                 List<Message> messages = new ArrayList<>();
                 while (rs.next()) {
                     Integer id = rs.getInt("id");
                     String text = rs.getString("text");
                     Integer from = rs.getInt("from_user_id");
                     Integer to = rs.getInt("to_user_id");
                     LocalDateTime time = rs.getTimestamp("sent_at").toLocalDateTime();
                     messages.add(new Message(id,text,time,from,to));
                 }
                 return messages;
             }catch(SQLException e)
             {
                 throw new RuntimeException(e);
             }
         }
         public List<Message> getAll(){
             return Collections.emptyList();
         }
     }


    public void clear(){
        String query = """
                    UPDATE users
                    SET answer = NULL
                    WHERE answer IS NOT NULL ;
                    """;
        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
