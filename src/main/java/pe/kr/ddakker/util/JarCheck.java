package pe.kr.ddakker.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.lang.System.err;

/**
 * https://www.mindprod.com/products1.html#JARCHECK
 * 참고 해서 만듬
 */
public class JarCheck {
    private static final Map<Integer, String> convertMachineToHuman = new HashMap<>();
    private static double START_VERSION;
    private static double END_VERSION;

    static {
        convertMachineToHuman.put(44, "1.0");
        convertMachineToHuman.put(45, "1.1");
        convertMachineToHuman.put(46, "1.2");
        convertMachineToHuman.put(47, "1.3");
        convertMachineToHuman.put(48, "1.4");
        convertMachineToHuman.put(49, "1.5");
        convertMachineToHuman.put(50, "1.6");
        convertMachineToHuman.put(51, "1.7");
        convertMachineToHuman.put(52, "1.8");
        convertMachineToHuman.put(53, "1.9");
        convertMachineToHuman.put(54, "10");
        convertMachineToHuman.put(55, "11");
        convertMachineToHuman.put(56, "12");
        convertMachineToHuman.put(57, "13");
        convertMachineToHuman.put(58, "14");
        convertMachineToHuman.put(59, "15");
        convertMachineToHuman.put(60, "16");
        convertMachineToHuman.put(61, "17");
        convertMachineToHuman.put(62, "18");
    }
    private static final int chunkLength = 8;

    public static void check(InputStream fis, String filename) throws IOException {
        ZipInputStream zipFile = new ZipInputStream(fis);

        while(true){
            ZipEntry entry = zipFile.getNextEntry();
            if (entry == null) {
                break;
            }
            String name = entry.getName();
            if (entry.getName().endsWith(".jar")) {
                check(zipFile, name);
            }

            if (!entry.getName().endsWith(".class")) {
                continue;
            }

            byte[] chunk = new byte[chunkLength];
            int bytesRead = zipFile.read(chunk, 0, chunkLength);

            int major = ((chunk[chunkLength - 2] & 0xff) << 8) + ( chunk[chunkLength - 1] & 0xff);
            double javaVersion = Double.parseDouble(convertMachineToHuman.get(major));
            if (javaVersion >= START_VERSION && javaVersion <= END_VERSION) {
                System.out.println("major: " + major + " : " + javaVersion + " - " + filename + ":" + name);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            err.println("usage: java jar jarCheck.jar jarFileName.jar 1.1 18");
            System.exit(1);
        }
        START_VERSION = Double.parseDouble(args[1]);
        END_VERSION = Double.parseDouble(args[2]);

        FileInputStream fis = null;
        try {
            File file = new File(args[0]);
            fis = new FileInputStream(file);
            check(fis, file.getName());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
