package com.stool.gram.local.filewatch;

import com.stool.gram.local.config.ModelConfig;
import com.stool.gram.local.dlmodel.GramClassifier;
import com.stool.gram.local.dlmodel.GramClassifierMap;

import com.stool.gram.local.service.ModelService;
import com.stool.gram.pojo.database.GramModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class GramModelWatcher {

    private Logger log = LoggerFactory.getLogger(GramModelWatcher.class);

    @Value("${resource.model-root-path}")
    private String modelRootPath;

    @Autowired
    private GramClassifierMap gramClassifierMap;

    @Autowired
    private ModelService modelService;


    /**
     * 监视文件夹的状态
     */
    @Async
    public void watch() {

        WatchService watcher = null;
        try {
            watcher = FileSystems.getDefault().newWatchService();
            Paths.get(modelRootPath).register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            log.info(modelRootPath+"被监视");
            registerChildPath(watcher, Paths.get(modelRootPath));

        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }

        while (true) {
            try {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    String fileName = event.context().toString();
                    WatchEvent.Kind<?> kind = event.kind();
                    Path rootPath = (Path) key.watchable();

                    log.info(rootPath.toString() + "发生变化，变化为：" + fileName);

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        // 将新创建的文件夹注册到监听器中
                        try {
                            if (Paths.get(rootPath.toString(), fileName).toFile().isDirectory()) {
                                Paths.get(rootPath.toString(), fileName).register(watcher,
                                        StandardWatchEventKinds.ENTRY_CREATE,
                                        StandardWatchEventKinds.ENTRY_DELETE,
                                        StandardWatchEventKinds.ENTRY_MODIFY);
                                log.info(Paths.get(rootPath.toString(), fileName).toString()+"被监视");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        // 只在modify时重新加载模型
                        if (Paths.get(rootPath.toString(), fileName).toFile().isDirectory()) {  // 修改的是文件夹（即文件夹添加或删除文件，或重命名）
                            String modelName = fileName;    // 文件名即模型名称
                            if (checkComplete(Paths.get(rootPath.toString(), fileName))) {
                                // 新建模型
                                rebuildGramModel(modelName);
                                log.info("新建模型"+modelName);
                            }
                        } else {    // 修改的是模型文件或类别文件
                            String modelName = rootPath.getFileName().toString();
                            if (checkComplete(rootPath)) {
                                // 重建模型
                                rebuildGramModel(modelName);
                                log.info("重建模型"+modelName);
                            }
                        }
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        // todo
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    throw new RuntimeException("ket reset invalid");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    // 重建/新建模型
    private void rebuildGramModel(String modelName) {
        GramClassifier gramClassifier = gramClassifierMap.get(modelName);
        if (gramClassifier == null) {
            GramModel newGramModel = modelService.getGramModelByName(modelName);
            if (newGramModel == null) {
                throw new RuntimeException("数据不一致");
            }
            // 新建
            GramClassifier newGramClassifier = new GramClassifier(newGramModel);
            gramClassifierMap.put(newGramClassifier);
        } else {
            // 重建
            gramClassifier.buildClassifier();
        }

    }


    /**
     * 目录下的所有文件夹注册到watchService文件监视器中
     * @param watchService
     * @param path
     * @throws IOException
     */
    private void registerChildPath(WatchService watchService, Path path) throws IOException{
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p : stream) {
                p.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
                log.info(p.toString()+"被监视");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断路径下是否有模型文件以及类别文件
     * @param path
     * @return
     */
    private boolean checkComplete(Path path) {

        boolean hasModelFile = false, hasLabelFile = false;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p : stream) {
                if (isModelFile(p.getFileName().toString())) {
                    hasModelFile = true;
                } else if (isLabelFile(p.getFileName().toString())) {
                    hasLabelFile = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hasModelFile && hasLabelFile;
    }

    private boolean isModelFile(String filename) {
        return filename.endsWith(".pb");
    }

    private boolean isLabelFile(String filename) {
        return filename.endsWith(".txt");
    }
}
