
package org.qcc.modules.learningpalette.Customizer;


/**
 *
 * @author Tony
 */
public class CustomizerVariableField extends CustomizerControl {

    
    /**
     * Build a customizer text field control.
     */
    public CustomizerVariableField(String name, String label, String value) {
        super(name, label, value);
    }

    @Override
    public String getType() {
        return "Variable";
    }
    

    
}
