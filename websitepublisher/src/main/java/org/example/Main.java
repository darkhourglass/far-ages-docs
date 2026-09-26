package org.example;

import org.apache.commons.io.FileUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.asciidoctor.jruby.AsciiDocDirectoryWalker;
import org.asciidoctor.jruby.DirectoryWalker;
import org.jruby.ext.jruby.JRubyUtilLibrary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static java.lang.System.out;
import static org.apache.commons.io.file.PathUtils.isEmpty;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {


        try {
        } finally {
            // NOOP
        }
            File base = new File("../far-ages-docs");


            File outputDir = new File("../output");
            boolean outputCreated = outputDir.mkdir();
            out.println("ouputcreated: " + outputCreated);

            FileUtils.copyDirectory(base, outputDir);


            DirectoryWalker directoryWalker = new AsciiDocDirectoryWalker(outputDir.getAbsolutePath());
            List<File> asciidocFiles = directoryWalker.scan();

            Options acciiDoctoroptions = Options.builder()
                    .toFile(true)
                    .safe(SafeMode.UNSAFE)
                    .build();

        try {
            try (Asciidoctor asciidoctor = Asciidoctor.Factory.create()) {

                for (File asciidocFile : asciidocFiles) {
                    out.println(asciidocFile.getAbsolutePath() + "\n---");
                    out.println(asciidocFile.getPath());
                    asciidoctor.convertFile(asciidocFile, acciiDoctoroptions);
                }


                Iterator<File> fileIterator = FileUtils.iterateFiles(outputDir, new String[]{"html"}, true);
                while (fileIterator.hasNext()) {
                    File file = fileIterator.next();
                    analyseAndCorrectLinks(file);

                }



            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }


        } finally {

        }
}

    private static final String HREF = "href";

private static void analyseAndCorrectLinks(final File htmlFile) throws Exception {
    Document doc = Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name(), "https://example.com/");




    Element content = doc.getElementById("content");

    Elements links1 = (Elements) content.select("a");


    for (Element link : links1) {
        String linkHref = link.attr(HREF);
        String linkText = link.text();
        String corrected = generateNewLink(linkHref);
        link.attr(HREF, corrected);
    }

    String newHTML = doc.html();
    FileUtils.write(htmlFile, newHTML, StandardCharsets.UTF_8);

}

    private static String generateNewLink(String linkHref) {
       if (linkHref == null|| linkHref.isEmpty()) {
           return "invalid";
       } else {
           return linkHref.replaceAll("adoc", "html");
       }
    }
}