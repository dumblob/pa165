package cz.muni.fi.pa165.books;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.SimpleMessage;
import org.apache.taglibs.standard.functions.Functions;

/**
 * Base actionBean implementing the required methods for setting and getting context.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public abstract class BaseActionBean implements ActionBean {
    private ActionBeanContext context;

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    public static String escapeHTML(String s) {
        return Functions.escapeXml(s);
    }
}
