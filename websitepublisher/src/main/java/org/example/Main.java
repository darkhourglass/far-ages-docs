package org.example;

import org.apache.commons.io.FileUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.jruby.AsciiDocDirectoryWalker;
import org.asciidoctor.jruby.DirectoryWalker;

import java.io.File;
import java.lang.System;
import java.util.List;


import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {
            Asciidoctor asciidoctor = Asciidoctor.Factory.create();
            File base = new File("../far-ages-docs");



            out.println("copy base to output");

            File outputDir = new File("output");
            boolean outputCreated = outputDir.mkdir();
            out.println("ouputcreated: " + outputCreated);

            FileUtils.copyDirectory(base, outputDir);

            System:out.println("copy base to output DONE");


            DirectoryWalker directoryWalker = new AsciiDocDirectoryWalker(outputDir.getName());
            List<File> asciidocFiles = directoryWalker.scan();

            for(File asciidocFile : asciidocFiles){
                out.println(asciidocFile.getAbsolutePath()+"\n---");
                out.println(asciidocFile.getPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}