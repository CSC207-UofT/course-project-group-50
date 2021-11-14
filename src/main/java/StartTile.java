import java.io.Serializable;

public class StartTile extends SpecialTile implements Serializable {
    public void interact(Token token){
        token.getPlayer().addCash(200);
    }
}
