package com.stool.gram.admin.web;

import com.stool.gram.admin.service.ModelService;
import com.stool.gram.common.exception.GlobalException;
import com.stool.gram.common.util.response.CodeMsg;
import com.stool.gram.common.util.response.JsonResult;
import com.stool.gram.pojo.database.GramModel;
import org.apache.commons.io.IOUtils;
import org.n3r.idworker.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@RestController
@RequestMapping("model")
public class GramModelController {

    @Value("${resource.model-root-path}")
    private String modelRootPath;

    @Autowired
    private ModelService modelService;

    @PostMapping("insert")
    public JsonResult<String> insert(
            @RequestPart("gramModel") GramModel gramModel,
            @RequestPart("modelFile") MultipartFile modelFile,
            @RequestPart("labelFile") MultipartFile labelFile) {
        if (modelFile == null || labelFile == null || !validateForInsert(gramModel)) {
            throw new GlobalException(CodeMsg.BIND_ERROR);
        }
        String modelDir = Paths.get(modelRootPath, gramModel.getModelName()).toString();

        String modelFileName = modelFile.getOriginalFilename();
        String labelFileName = labelFile.getOriginalFilename();

        String modelFilePath = Paths.get(modelDir, modelFileName).toString();
        String labelFilePath = Paths.get(modelDir, labelFileName).toString();

        gramModel.setModelDir(modelDir);
        gramModel.setModelFileName(modelFileName);
        gramModel.setLabelFileName(labelFileName);

        gramModel.setId(Id.next());

        modelService.insertGramModel(gramModel);

        uploadFile(modelFilePath, modelFile);
        uploadFile(labelFilePath, labelFile);

        return JsonResult.success("添加成功");
    }

    /**
     * 做切面更好
     * @param gramModel
     * @return
     */
    private boolean validateForInsert(GramModel gramModel) {
        return gramModel != null
                && gramModel.getModelName() != null
                && gramModel.getInputTensorName() != null
                && gramModel.getOutputTensorName() != null
                && gramModel.getNumberLabel() != null
                && gramModel.getTimes() != null;
    }

    @PostMapping("update")
    public JsonResult<String> update(
            @RequestPart("gramModel") GramModel gramModel,
            @RequestPart("modelFile") MultipartFile modelFile,
            @RequestPart("labelFile") MultipartFile labelFile) {

        if (gramModel == null || gramModel.getModelName() == null) {
            throw new GlobalException(CodeMsg.BIND_ERROR);
        }

        String modelDir = Paths.get(modelRootPath, gramModel.getModelName()).toString();
        gramModel.setModelDir(modelDir);

        String modelFileName = null;
        String modelFilePath = null;
        String labelFileName = null;
        String labelFilePath = null;

        if (modelFile != null) {
            modelFileName = modelFile.getOriginalFilename();
            modelFilePath = Paths.get(modelDir, modelFileName).toString();
            gramModel.setModelFileName(modelFileName);
        }

        if (labelFile != null) {
            labelFileName = labelFile.getOriginalFilename();
            labelFilePath = Paths.get(modelDir, labelFileName).toString();
            gramModel.setLabelFileName(labelFileName);
        }

        modelService.updateGramModelByName(gramModel);

        if (modelFilePath != null) {
            uploadFile(modelFilePath, modelFile);
        }
        if (labelFilePath != null) {
            uploadFile(labelFilePath, labelFile);
        }

        return JsonResult.success("更新成功");
    }

    public void uploadFile(String filePath, MultipartFile file) {

        if (file == null) {
            throw new GlobalException(CodeMsg.UPLOAD_ERROR);
        }
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {

            File outFile = new File(filePath);
            if (outFile.getParentFile() != null && !outFile.getParentFile().isDirectory()) {
                // 创建父文件夹
                boolean r = outFile.getParentFile().mkdirs();
            }

            fileOutputStream = new FileOutputStream(outFile);
            inputStream = file.getInputStream();
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.UPLOAD_ERROR);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
