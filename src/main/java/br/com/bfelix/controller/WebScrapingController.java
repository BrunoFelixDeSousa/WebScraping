package main.java.br.com.bfelix.controller;

import main.java.br.com.bfelix.model.Produto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebScrapingController {

    public void rasparDados() {

        try {

            // define o caminho do driver
            System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");

            EdgeOptions options = new EdgeOptions();

            //para corrigir possiveis erros na execução
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            //evitar detecao dos sites
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", null);

            options.addArguments("window-size=1600,800");

            //para ajudar a não ser identificado como bot
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

            WebDriver driver = new EdgeDriver(options);

            driver.get("https://www.amazon.com.br");

            WebElement inputPesquisa = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));

            inputPesquisa.sendKeys("Smatphone");

            inputPesquisa.submit();

            waitForIt(5000);

            List<WebElement> descricaoProdutos = driver.findElements(By.xpath("//span[@class=\"a-size-base-plus a-color-base a-text-normal\"]"));

            List<WebElement> valoresProdutos = driver.findElements(By.xpath("//div[@class=\"a-row a-size-base a-color-base\"]"));

            ArrayList<Produto> produtos = new ArrayList<>();
            for (int i = 0; i < descricaoProdutos.size(); i++) {
                produtos.add(new Produto(descricaoProdutos.get(i).getText(), valoresProdutos.get(i).getText()));
            }

            for (Produto produto : produtos) {
                System.out.println(produto.toString());
            }

            waitForIt(5000);

            driver.quit();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForIt(long tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
