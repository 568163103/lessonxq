package concurrentprogramming.eleven;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2019-12-30 23:05
 **/
public class BrokerDelegateClassLoader extends MyClassLoader {

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> klass = findLoadedClass(name);
            if (klass == null) {
                if (name.startsWith("java.") || name.startsWith("javax.")) {
                    try {
                        klass = getSystemClassLoader().loadClass(name);
                    } catch (Exception e) {

                    }
                }
            } else {
                klass = this.findClass(name);
                if (klass == null) {
                    if (getParent() != null) {
                        klass = getParent().loadClass(name);
                    } else {
                        klass = getSystemClassLoader().loadClass(name);
                    }
                }
            }
            if (null == klass) {
                throw new ClassNotFoundException("The class" + name + "not found.");
            }
            if (resolve) {
                resolveClass(klass);
            }

            return klass;

        }
    }
}
