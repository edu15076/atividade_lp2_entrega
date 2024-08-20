package br.cefetmg.gestaoEntregas.viewdesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class SceneManager {
    private static final String arquivoPath = "/br/cefetmg/gestaoEntregas/viewdesktop/scenes.txt";
    private HashMap<String, String> cenas;

    public SceneManager() {
        cenas = new HashMap<>();

        try {
            URL url = getClass().getResource(arquivoPath);
            assert url != null;
            File scenesLista = new File(url.toURI());
            Scanner scanner = new Scanner(scenesLista);

            System.out.println("Cenas configuradas:");
            while (scanner.hasNext()) {
                String key = scanner.next();
                String path = scanner.next();

                cenas.put(key, path);

                System.out.printf("%s: %s\n", key, path);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException("Arquivo de configuração '" + arquivoPath + "' não existe");
        }
    }

    public Scene getScene(String sceneName) throws IOException {
        String pathScene = cenas.get(sceneName);
        assert pathScene != null : "Cena não registrada";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathScene));

        return new Scene(fxmlLoader.load());
    }

    public void showScene(Stage stage, String sceneName, String sceneTitle) throws IOException {
        Scene scene = getScene(sceneName);

        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();
    }
}
