
package org.qcc.modules.learningpalette.Customizer;


/**
 *
 * @author Tony
 */
public class CustomizerExpressionField extends CustomizerControl {

    
    /**
     * Build a customizer text field control.
     */
    public CustomizerExpressionField(String name, String label, String value) {
        super(name, label, value);
    }

    @Override
    public String getType() {
        return "Expression";
    }
    

    
}
