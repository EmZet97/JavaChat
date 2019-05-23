package Messages;

import java.util.List;

public interface MessageManagerInterface {
    //zwraca id ostatniej wiadomosci
    public int GetLastMassageId();

    //zwraca string ze wszystkimi wiadomosciami
    public List<Message> GetMessages();

    //wysyla wiadomosci, zwraca powodzenie wyslania
    public boolean SendMessage(String message);

    //sprawdz czy nastapily zmiany w bazie, jezeli tak to aktualizuje liste wiadomosci i ustawia nowa wartosc ostatniej wiadomosci
    public void CheckChanges();


    //dodac zmienne: Integer ID_ostatniej_wiadomosci, Liste<> obiektow Message, Integer roomID i roomID
    //dodac konstruktor w MessageManager z parametrami: Integer roomID. Integer userID


}
