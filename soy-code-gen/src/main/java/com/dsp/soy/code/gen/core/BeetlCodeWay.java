package com.dsp.soy.code.gen.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BeetlCodeWay implements CodeWay {

    private String targetPath;

    @Override
    public void flush(CodeGen gen, String content) {
        String target = "";
        System.out.println("rootPath: " + getRootPath());
        target = getRootPath() + File.separator + "build" + File.separator + "gen" + File.separator + gen.getFileName();
        write(target, content, "UTF-8");
    }

    protected static void write(String path, String content, String encoding) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // file.delete();
        // file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSrcPath() {
        return getRootPath() + File.separator + "src/main/java";
    }

    public String getResourcePath() {
        return getRootPath() + File.separator + "src/main/resources";
    }

    public String getRootPath() {
        if (targetPath != null) {
            return targetPath;
        } else {
            return detectRootPath();
        }
    }

    public static String detectRootPath() {
        String userDir = System.getProperty("user.dir");
        if (userDir == null) {
            throw new NullPointerException("用户目录未找到");
        }
        return userDir;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public BeetlCodeWay setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

}
