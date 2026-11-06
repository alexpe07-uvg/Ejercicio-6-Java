package Dashboard_Model;
public class FrasesWidget extends Widget{ // solamente va a contar con un texto y un autor, no va a tener interacciones
    private String phrase;
    private String author;

    public FrasesWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String phrase, String author) {
        super(name, ID, memCapacity, isState, height, width);
        this.phrase = phrase;
        this.author = author;
    }

    //getters y setters específicos
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override public String render() {
        return " | " + getName() + " | Frase: \"" + phrase + "\" - " + author;
    }
    
}
