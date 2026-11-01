import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.*;
import java.util.stream.Stream;

public class Cleaner extends Application {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--silent")) {
            runSortingEngine();
        } else {
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Downloads Folder Cleaner Bot");

        Label titleLabel = new Label("Downloads Cleaner Automation");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label statusLabel = new Label("System Status: Ready");
        statusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px;");

        TextField hourField = new TextField("18");
        hourField.setPrefWidth(50);
        TextField minuteField = new TextField("00");
        minuteField.setPrefWidth(50);
        Label colonLabel = new Label(":");
        colonLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        HBox timeContainer = new HBox(10, new Label("Schedule Time:"), hourField, colonLabel, minuteField);
        timeContainer.setAlignment(Pos.CENTER);

        Button installBtn = new Button("Enable Automation");
        Button removeBtn = new Button("Remove Schedule");

        installBtn.setOnAction(e -> {
            String hh = sanitizeNum(hourField.getText(), 23);
            String mm = sanitizeNum(minuteField.getText(), 59);
            hourField.setText(hh);
            minuteField.setText(mm);
            
            if (registerWindowsTask(hh + ":" + mm)) {
                statusLabel.setText("System Status: Scheduled daily for " + hh + ":" + mm);
            } else {
                statusLabel.setText("System Status: Error! Run app as Administrator.");
            }
        });

        removeBtn.setOnAction(e -> {
            if (removeWindowsTask()) {
                statusLabel.setText("System Status: Automation cleared.");
            } else {
                statusLabel.setText("System Status: No tasks found.");
            }
        });

        VBox rootLayout = new VBox(20, titleLabel, timeContainer, installBtn, removeBtn, statusLabel);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-padding: 40;");

        Scene mainScene = new Scene(rootLayout, 450, 320);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private boolean registerWindowsTask(String time) {
        try {
            String currentExePath = new File(Cleaner.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            String taskCmd = String.format("schtasks /create /tn \"DownloadsFolderCleaner\" /tr \"\\\"%s\\\" --silent\" /sc daily /st %s /f", currentExePath, time);
            Process proc = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", taskCmd});
            proc.waitFor();
            return proc.exitValue() == 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean removeWindowsTask() {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "schtasks /delete /tn \"DownloadsFolderCleaner\" /f"});
            proc.waitFor();
            return proc.exitValue() == 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private String sanitizeNum(String val, int max) {
        val = val.replaceAll("[^0-9]", "");
        if (val.isEmpty()) return "00";
        int num = Math.min(Integer.parseInt(val), max);
        return String.format("%02d", num);
    }

    private static void runSortingEngine() {
        String homeDir = System.getProperty("user.home");
        Path downloadsPath = Paths.get(homeDir, "Downloads");
        if (!Files.exists(downloadsPath)) return;
        try (Stream<Path> fileStream = Files.list(downloadsPath)) {
            fileStream.filter(Files::isRegularFile).forEach(file -> {
                try {
                    String fileName = file.getFileName().toString();
                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0) {
                        String ext = fileName.substring(dotIndex + 1).toLowerCase();
                        Path targetSubFolder = downloadsPath.resolve(ext + "_files");
                        if (!Files.exists(targetSubFolder)) { Files.createDirectories(targetSubFolder); }
                        Files.move(file, targetSubFolder.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception ignored) {}
            });
        } catch (Exception ignored) {}
    }
}
