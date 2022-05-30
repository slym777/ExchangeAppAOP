package aspects;

import org.jdesktop.swingx.painter.effects.AbstractAreaEffect;

import javax.management.remote.MBeanServerForwarder;
import java.util.logging.Logger;

public aspect LoggingAspect {
    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    after() returning(String res): call(* Ops.validate*()) {
        logger.info(res);
    }

    before(): execution(* GUI.Login.getRegister()) {
        logger.info("Registering a new user. Complete form to register ...");
    }

    before(): execution(* GUI.MainView.getLogin()) {
        logger.info("Authentication process started ...");
    }

    after(): call(* GUI.MainView.getLogout()) {
        logger.info("User logged out ...");
    }

}
