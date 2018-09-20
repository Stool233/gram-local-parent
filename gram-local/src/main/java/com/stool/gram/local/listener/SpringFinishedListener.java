package com.stool.gram.local.listener;

import com.stool.gram.local.filewatch.GramModelWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringFinishedListener implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(SpringFinishedListener.class);

    @Autowired
    private GramModelWatcher gramModelWatcher;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("文件监控器开启");
        gramModelWatcher.watch();
    }
}
