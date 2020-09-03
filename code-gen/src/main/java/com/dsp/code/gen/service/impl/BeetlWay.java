package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BeetlWay implements Way {

    private String targetPath;

    @Override
    public void flush(Gen gen, String content) {
        String target = "";
        if (gen instanceof JavaEntityGen) {
            target = getRootPath() + File.separator+ "build" + File.separator + "entity"+ File.separator +gen.getFileName();
        }
        flush(target,content);
    }

    protected void flush(String path, String content) {
        FileWriter fw;
        try {
            File file = new File(path);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fw = new FileWriter(new File(path));
            fw.write(content);
            fw.close();
            System.out.println(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
        if(targetPath!=null) {
            return targetPath;
        }else {
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

    public BeetlWay setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    public static void main(String[] args) {
        System.out.println(detectRootPath());
    }
}
