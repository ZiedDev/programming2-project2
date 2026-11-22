package UI;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.util.Map;
import java.awt.Component;
import java.util.HashMap;
public class MultipleInputField extends JPanel{
    private final Map<String,String> fields = new HashMap<>();
    public MultipleInputField(String[] fieldNames){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String name : fieldNames){
            JPanel inputField = ComponentFactory.createInputField(name,"",20,value->{
                fields.put(name,value);
            });
            add(inputField);
        }
    }
    
    public String getValue(String fieldName){
        String answer = fields.get(fieldName);
        return answer != null ? answer : "";
    }
}
