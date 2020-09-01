package demo.eurakServer.eventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 服务下线的事件监听
 * </p>
 *
 * @author ll Create on 20/8/31 14:51
 * @version 1.0
 */
@Component
public class DownEventListener {

    private final static Logger logger = LoggerFactory.getLogger(DownEventListener.class);

    @EventListener
    public void handler(String message) {
        logger.error(message);
    }

    @EventListener
    public void handler(String message, String type) {
        logger.error(message);
        logger.error(type);
    }
}
