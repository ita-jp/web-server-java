package com.example.web.server.java;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FileManagerTest {

    private static final String DOCUMENT_ROOT = "./src/test/resources";
    private FileManager sut;

    @Before
    public void setup() {
        sut = new FileManager();
    }

    @Test
    public void createBufferedReader_whenExistingFileIsSpecified() {
        // ## Arrange ##

        // ## Act ##
        val optionalBufferedReader = sut.createBufferedReader(DOCUMENT_ROOT, "test.html");

        // ## Assert ##
        assertThat(optionalBufferedReader).isNotEmpty();
    }

    @Test
    public void createBufferedReader_whenNotExistingFileIsSpecified() {
        // ## Arrange ##

        // ## Act ##
        val optionalBufferedReader = sut.createBufferedReader(DOCUMENT_ROOT, "not_existing.html");

        // ## Assert ##
        assertThat(optionalBufferedReader).isEmpty();
    }

    @Test
    public void createBufferedReader_whenExistingDirectoryIsSpecified() {
        // ## Arrange ##

        // ## Act ##
        val optionalBufferedReader = sut.createBufferedReader(DOCUMENT_ROOT, "");

        // ## Assert ##
        assertThat(optionalBufferedReader).isEmpty();
    }

    @Test
    public void createBufferedReader_whenFileExistingNotUnderDocumentRootIsSpecified() {
        // ## Arrange ##

        // ## Act ##
        val optionalBufferedReader = sut.createBufferedReader(DOCUMENT_ROOT, "../../../.gitignore");

        // ## Assert ##
        assertThat(optionalBufferedReader).isEmpty();
    }
}