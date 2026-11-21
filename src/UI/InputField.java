package UI;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;
public class InputField extends JTextField {
    private String lastValue;
    private final Consumer<String> onCommit;
    public InputField(String initialText,int columns,Consumer<String> onCommit){
        super(initialText,columns);
        this.lastValue = initialText;
        this.onCommit = onCommit;

        this.addActionListener(e->commit());

        this.addFocusListener(new FocusAdapter() {
            @Override 
            public void focusLost(FocusEvent e){
                commit();
            }
        });
    }

    private void commit(){
        String current = getText().trim();
        if(!current.equals(lastValue)){
            lastValue = current;
            onCommit.accept(current);
        }
    }

}
