import java.io.File;

public class Start {
    public static void main(String[] args) {
        // 1. Check if the app is already running with Admin rights
        if (isAdmin()) {
            // Already elevated! Launch the main program window safely
            Cleaner.main(args);
        } else {
            try {
                // 2. Not elevated. Determine the path of the current running .exe file
                String jarPath = Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                String exePath = jarPath.replace(".jar", ".exe");
                File exeFile = new File(exePath);

                // 3. Relaunch the application binary using the Windows 'runas' elevation verb
                if (exeFile.exists()) {
                    Runtime.getRuntime().exec(new String[]{
                        "powershell", "-Command", 
                        "Start-Process '" + exeFile.getAbsolutePath() + "' -Verb RunAs"
                    });
                } else {
                    // Fallback to regular boot if running raw classes during testing
                    Cleaner.main(args);
                }
                System.exit(0); // Close the non-admin background wrapper process immediately
            } catch (Exception e) {
                // If elevation fails, try to load anyway as a fallback
                Cleaner.main(args);
            }
        }
    }

    // Helper process logic that checks if the JVM has active write access to system directories
    private static boolean isAdmin() {
        try {
            Process process = Runtime.getRuntime().exec("net session");
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
