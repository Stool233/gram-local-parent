package com.stool.gram.local.listener;

import com.stool.gram.local.dlmodel.GramClassifier;
import com.stool.gram.local.dlmodel.GramClassifierMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationClosedListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private GramClassifierMap gramModelMap;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 清除系统资源
        gramModelMap.clear();
    }
}
