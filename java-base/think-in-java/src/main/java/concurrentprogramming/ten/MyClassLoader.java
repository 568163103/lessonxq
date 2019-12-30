package concurrentprogramming.ten;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @program: java-base
 * @description: 自定义类加载器
 * @author: xq
 * @create: 2019-12-30 19:14
 **/
public class MyClassLoader extends ClassLoader {
    public static final Path DEFAULT_CLASS_DIR = Paths.get("G:", "classloader1");
    private final Path classDir;

    public MyClassLoader() {
        super();
        this.classDir = DEFAULT_CLASS_DIR;
    }

    public MyClassLoader(String classDir) {
        super();
        this.classDir = Paths.get(classDir);
    }


    public MyClassLoader(String classDir, ClassLoader parent) {
        super(parent);
        this.classDir = Paths.get(classDir);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = readClassBytes(name);
        if (null==classBytes || classBytes.length ==0){
            throw  new ClassNotFoundException("Can not load the class"+ name);
        }
        return this.defineClass(name,classBytes,0,classBytes.length);
    }

    private byte[] readClassBytes(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        Path classFullPath = classDir.resolve(Paths.get(classPath + ".class"));
        if (!classFullPath.toFile().exists()) {
            throw new ClassNotFoundException("The class" + name + "not found");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Files.copy(classFullPath, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("The class" + name + "occur error", e);
        }
    }

    @Override
    public String toString() {
        return "MyClassLoader";
    }
}
