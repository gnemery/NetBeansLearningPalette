
package org.qcc.modules.learningpalette.Customizer;


/**
 *
 * @author Tony
 */
public class CustomizerTextField extends CustomizerControl {

    
    /**
     * Build a customizer text field control.
     */
    public CustomizerTextField(String name, String label, String value) {
        super(name, label, value);
    }

    @Override
    public String getType() {
        return "Text";
    }
    

    
}
